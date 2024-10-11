package be.vdab.poverello.boekhouding;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class KasboekService {
    private final KasboekRepository kasboekRepository;
    private final OmschrijvingService omschrijvingService;

    public KasboekService(KasboekRepository kasboekRepository, OmschrijvingService omschrijvingService) {
        this.kasboekRepository = kasboekRepository;
        this.omschrijvingService = omschrijvingService;
    }

    long findAantal() {
        return kasboekRepository.count();
    }

    Optional<Kasboek> findKasboekByIdMetDetails(long id) {
        return kasboekRepository.findKasboekByIdMetDetails(id);
    }

    Optional<Kasboek> findKasboekByAfdelingIdJaarMaandMetDetails(long afdelingId, int jaar, int maand) {
        Afdeling afdeling = kasboekRepository.findAfdelingByAfdelingId(afdelingId)
                .orElseThrow(() -> new KasboekNietGevondenException());
        return kasboekRepository.findKasboekByAfdelingJaarMaandMetDetails(afdelingId, jaar, maand);
    }

    @Transactional
    long create(NieuwKasboek nieuwKasboek) {
        try {
            var kasboek = new Kasboek(nieuwKasboek.afdelingId(), nieuwKasboek.jaar(), nieuwKasboek.maand());
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // kan misschien betere opvang doen van exceptions in onderstaande method (geen exc throwen maar db aanpassen...)
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional
    void voegVerrichtingToe(long kasboekId, NieuweVerrichting nieuweVerrichting) {
        Omschrijving omschrijving = new Omschrijving();
        if (nieuweVerrichting.omschrijvingId() > 0) {
            omschrijving = omschrijvingService.findById(nieuweVerrichting.omschrijvingId())
                    .orElseThrow(() -> new OmschrijvingNietGevondenException());
        } else {
            omschrijving = new Omschrijving(nieuweVerrichting.afdelingId(), nieuweVerrichting.omschrijving());
            try {
                omschrijvingService.save(omschrijving);
            } catch (DataIntegrityViolationException ex) {
                throw new OmschrijvingBestaatAlException();
            }
        }
        var verrichting = new Verrichting(nieuweVerrichting.volgnummer(), nieuweVerrichting.dag(), nieuweVerrichting.bedrag(), omschrijving, nieuweVerrichting.kasticket(), nieuweVerrichting.verrichtingsType());
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
}
