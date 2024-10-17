package be.vdab.poverello.boekhouding;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class KasboekService {
    private final KasboekRepository kasboekRepository;
    private final OmschrijvingService omschrijvingService;
    private final AfdelingRepository afdelingRepository;

    public KasboekService(KasboekRepository kasboekRepository, OmschrijvingService omschrijvingService,  AfdelingRepository afdelingRepository) {
        this.kasboekRepository = kasboekRepository;
        this.omschrijvingService = omschrijvingService;
        this.afdelingRepository = afdelingRepository;
    }

    private void maakNieuweOmschrijvingIndienNogNietBestaand(NieuweVerrichting nieuweVerrichting) {
        if (!omschrijvingService.existsByAfdelingIdAndInhoud(nieuweVerrichting.afdelingId(), nieuweVerrichting.omschrijving())) {
            Omschrijving omschrijving = new Omschrijving(nieuweVerrichting.afdelingId(), nieuweVerrichting.omschrijving());
            try {
                omschrijvingService.save(omschrijving);
            } catch (DataIntegrityViolationException ex) {
                throw new OmschrijvingBestaatAlException();
            }
        }
    }

    long findAantal() {
        return kasboekRepository.count();
    }

    List<Integer> findJarenVanKasboekenAfdelingId(long afdelingId) {
        return kasboekRepository.findJarenVanKasboekenAfdelingId(afdelingId);
    }

    List<Integer> findMaandenVanKasboekenAfdelingIdEnJaar(long afdelingId, long jaar) {
        return kasboekRepository.findMaandenVanKasboekenAfdelingIdEnJaar(afdelingId, jaar);
    }

    Optional<Kasboek> findKasboekByIdMetDetails(long id) {
        return kasboekRepository.findKasboekByIdMetDetails(id);
    }

    Optional<Kasboek> findKasboekByAfdelingIdJaarMaandMetDetails(long afdelingId, int jaar, int maand) {
        Afdeling afdeling = afdelingRepository.findAfdelingByAfdelingId(afdelingId)
                .orElseThrow(() -> new KasboekNietGevondenException());
        return kasboekRepository.findKasboekByAfdelingJaarMaandMetDetails(afdelingId, jaar, maand);
    }

    @Transactional
    long create(NieuwKasboek nieuwKasboek) {
        try {
            var kasboek = new Kasboek(nieuwKasboek.afdelingId(), nieuwKasboek.jaar(), nieuwKasboek.maand(), nieuwKasboek.beginSaldo());
            var verrichting = new Verrichting(0, 1, kasboek.getBeginSaldo(), "SALDO", false, VerrichtingsType.N);
            kasboek.voegVerrichtingToe(verrichting);
            kasboekRepository.save(kasboek);
            return kasboek.getId();
        } catch (DataIntegrityViolationException ex) {
            throw new KasboekBestaatAlExcpetion();
        }
    }

    @Transactional
    void delete(long id) {
        kasboekRepository.deleteById(id);
    }

    @Transactional
    void wijzigAfdelingId(long kasboekId, long nieuwAfdelingId) {
        kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException())
                .setAfdelingId(nieuwAfdelingId);
    }

    @Transactional
    void wijzigJaar(long kasboekId, int nieuwJaar) {
        kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException())
                .setJaar(nieuwJaar);
    }

    @Transactional
    void wijzigMaand(long kasboekId, int nieuweMaand) {
        kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException())
                .setMaand(nieuweMaand);
    }

    @Transactional
    void wijzigBeginSaldo(long kasboekId, BigDecimal saldo) {
        kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException())
                .setBeginSaldo(saldo);
    }

    @Transactional
    void wijzigCash(long kasboekId, CashMetGewichten cashMetGewichten) {
        kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException())
                .setCash(cashMetGewichten);
    }

    private void checkVolgnummerVerrichting(long kasboekId, int volgnummer) {
        var kasboek = kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException());
        var verrichtingen = kasboek.getVerrichtingen();
        if (verrichtingen.stream().anyMatch(element -> element.getVolgnummer() == volgnummer)) {
            throw new VerrichtingBestaatAlException();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // kan misschien betere opvang doen van exceptions in onderstaande method (geen exc throwen maar db aanpassen...)
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    void voegVerrichtingToe(long kasboekId, NieuweVerrichting nieuweVerrichting) {
        checkVolgnummerVerrichting(kasboekId, nieuweVerrichting.volgnummer());
        maakNieuweOmschrijvingIndienNogNietBestaand(nieuweVerrichting);
        var verrichting = new Verrichting(nieuweVerrichting.volgnummer(), nieuweVerrichting.dag(), nieuweVerrichting.bedrag(), nieuweVerrichting.omschrijving(), nieuweVerrichting.kasticket(), nieuweVerrichting.verrichtingsType());
        kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException())
                .voegVerrichtingToe(verrichting);
    }

    @Transactional
    void verwijderVerrichting(long kasboekId, int volgnummer) {
        var kasboek = kasboekRepository.findById(kasboekId)
                        .orElseThrow(() -> new KasboekNietGevondenException());
        var verrichtingen = kasboek.getVerrichtingen();
        var verrichting = verrichtingen.stream().filter(element -> element.getVolgnummer() == volgnummer)
                        .findFirst()
                                .orElse(null);
        kasboek.verwijderVerrichting(verrichting);
    }

    @Transactional
    void wijzigVerrichting(long kasboekId, NieuweVerrichting nieuweVerrichting) {
        maakNieuweOmschrijvingIndienNogNietBestaand(nieuweVerrichting);
        kasboekRepository.findById(kasboekId)
                .orElseThrow(() -> new KasboekNietGevondenException())
                .wijzigVerrichting(nieuweVerrichting);
    }


}
