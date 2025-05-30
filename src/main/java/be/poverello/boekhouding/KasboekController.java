package be.poverello.boekhouding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("kasboeken")
public class KasboekController {
    private final KasboekService kasboekService;
    private final OmschrijvingService omschrijvingService;

    public KasboekController(KasboekService kasboekService, OmschrijvingService omschrijvingService) {
        this.kasboekService = kasboekService;
        this.omschrijvingService = omschrijvingService;
    }

    private record VerrichtingItem(
            int volgnummer,
            int dag,
            BigDecimal bedrag,
            String omschrijving,
            Boolean kasticket,
            VerrichtingsType verrichtingsType
    ) {
        public VerrichtingItem(Verrichting verrichting) {
            this(
                    verrichting.getVolgnummer(),
                    verrichting.getDag(),
                    verrichting.getBedrag(),
                    verrichting.getOmschrijving(),
                    verrichting.getKasticket(),
                    verrichting.getVerrichtingsType()
            );
        }
    }
    private record KasboekBeknopt(
            long id,
            long afdelingId,
            int jaar,
            int maand,
            BigDecimal beginSaldo,
            Stream<VerrichtingItem> verrichtingen,
            CashMetGewichten cash,
            CashInEuro cashInEuro,
            BerekendeWaarden berekendeWaarden
    ) {
        public KasboekBeknopt(Kasboek kasboek) {
            this(
                    kasboek.getId(),
                    kasboek.getAfdelingId(),
                    kasboek.getJaar(),
                    kasboek.getMaand(),
                    kasboek.getBeginSaldo(),
                    kasboek.getVerrichtingen()
                            .stream()
                            .map(verrichting -> new VerrichtingItem(verrichting)),
                    kasboek.getCash(),
                    kasboek.getCashInEuro(),
                    kasboek.getBerekendeWaarden()
            );
        }
    }

    @GetMapping("aantal")
    long findAantal() {
        return kasboekService.findAantal();
    }

    @GetMapping("{afdelingId}/jaren")
    List<Integer> findJaren(@PathVariable long afdelingId) {
        return kasboekService.findJarenVanKasboekenAfdelingId(afdelingId);
    }

    @GetMapping("{afdelingId}/{jaar}/maanden")
    List<Integer> findMaanden(@PathVariable long afdelingId, @PathVariable int jaar) {
        return kasboekService.findMaandenVanKasboekenAfdelingIdEnJaar(afdelingId, jaar);
    }

    @GetMapping("{id}")
    KasboekBeknopt findKasboekByIdMetVerrichtingen(@PathVariable long id) {
        return kasboekService.findKasboekByIdMetDetails(id)
                .map(kasboek -> new KasboekBeknopt(kasboek))
                .orElseThrow(() -> new KasboekNietGevondenException());
    }

    @GetMapping("{afdelingId}/{jaar}/{maand}")
    KasboekBeknopt findKasboekByIdMetVerrichtingen(
            @PathVariable long afdelingId,
            @PathVariable int jaar,
            @PathVariable int maand) {
        return kasboekService.findKasboekByAfdelingIdJaarMaandMetDetails(afdelingId, jaar, maand)
                .map(kasboek -> new KasboekBeknopt(kasboek))
                .orElseThrow(() -> new KasboekNietGevondenException());
    }

    @GetMapping("{afdelingId}/{jaar}/{maand}/verrichtingen")
    Stream<VerrichtingItem> findKasboekByIdMetAlleenVerrichtingen(
            @PathVariable long afdelingId,
            @PathVariable int jaar,
            @PathVariable int maand) {
        return kasboekService.findKasboekByAfdelingIdJaarMaandMetDetails(afdelingId, jaar, maand)
                .map(kasboek -> kasboek.getVerrichtingen().stream().map(verrichting -> new VerrichtingItem(verrichting)))
                .orElseThrow(() -> new KasboekNietGevondenException());
    }

    @PostMapping long create(@RequestBody @Valid NieuwKasboek nieuwKasboek) {
        if (nieuwKasboek.afdelingId() == 1) {
            throw new KasboekBestaatAlExcpetion();
        }
        return kasboekService.create(nieuwKasboek);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        try {
            kasboekService.delete(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }

    //het wijzigen van de afdelingId wordt heel ingewikkeld omdat de omschrijvingen nu opgeslaan worden per afdeling
    //er dienen dus neiuwe omschrijvingen aangemaakt te worden in de omschrijvingen-DBtabel indien deze nog niet bestaan in de nieuwe afdeling
    //er zou ook moeten gekeken worden of een omschrijving uit de oude afdeling reeds bestaat in de nieuwe...
    //INDIEN DE AFDELING VAN EEN KASBOEK GEWIJZIGD MOET WORDEN IS DE BESTE OPLOSSING DUS OM EEN VOLLEDIG NIEUW KASBOEK AAN TE MAKEN
    //EN DE VERRICHTINGEN OPNIEUW IN TE GEVEN IN DE NIEUWE AFDELING
    /*    @PatchMapping("{kasboekId}/afdelingId")
    void wijzigAfdelingId(@PathVariable long kasboekId,
                          @RequestBody @NotNull @Positive long nieuwAfdelingId) {
        try {
            //indien er reeds verrichtingen zijn moet het afdelingId van de omschrijvingen ook aangepast worden!:
            var kasboek = kasboekService.findKasboekByIdMetDetails(kasboekId)
                    .orElseThrow(() -> new KasboekNietGevondenException());
            var verrichtingen = kasboek.getVerrichtingen();
            if (!verrichtingen.isEmpty()) {
                verrichtingen.stream().forEach(verrichting -> {
                    var omschrijving = verrichting.getOmschrijving();
                    if (omschrijving.getAfdelingId() > 1) {
                        omschrijving.setAfdelingId(nieuwAfdelingId);
                        omschrijvingService.save(omschrijving);
                    }
                });
            }
            kasboekService.wijzigAfdelingId(kasboekId, nieuwAfdelingId);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }*/

    @PatchMapping("{kasboekId}/jaar")
    void wijzigJaar(@PathVariable long kasboekId,
                          @RequestBody @NotNull @Positive int nieuwJaar) {
        try {
            kasboekService.wijzigJaar(kasboekId, nieuwJaar);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/maand")
    void wijzigMaand(@PathVariable long kasboekId,
                    @RequestBody @NotNull @Positive int nieuweMaand) {
        try {
            kasboekService.wijzigMaand(kasboekId, nieuweMaand);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/beginSaldo")
    void wijzigBeginSaldo(@PathVariable long kasboekId,
                     @RequestBody @NotNull BigDecimal saldo) {
        try {
            kasboekService.wijzigBeginSaldo(kasboekId, saldo);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/cash")
    void wijzigCash(@PathVariable long kasboekId,
                     @RequestBody @Valid CashMetGewichten cashMetGewichten) {
        try {
            kasboekService.wijzigCash(kasboekId, cashMetGewichten);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/totaalGewichtMunten2E")
    void wijzigCash2E(@PathVariable long kasboekId,
                    @RequestBody @NotNull BigDecimal totaalGewichtMunten2E) {
        try {
            kasboekService.wijzigTotaalGewichtMunten2E(kasboekId, totaalGewichtMunten2E);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/totaalGewichtMunten1E")
    void wijzigCash1E(@PathVariable long kasboekId,
                    @RequestBody @NotNull BigDecimal totaalGewichtMunten1E) {
        try {
            kasboekService.wijzigTotaalGewichtMunten1E(kasboekId, totaalGewichtMunten1E);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/totaalGewichtMunten50cE")
    void wijzigCash50cE(@PathVariable long kasboekId,
                    @RequestBody @NotNull BigDecimal totaalGewichtMunten50cE) {
        try {
            kasboekService.wijzigTotaalGewichtMunten2E(kasboekId, totaalGewichtMunten50cE);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/totaalGewichtMunten20cE")
    void wijzigCash20cE(@PathVariable long kasboekId,
                    @RequestBody @NotNull BigDecimal totaalGewichtMunten20cE) {
        try {
            kasboekService.wijzigTotaalGewichtMunten2E(kasboekId, totaalGewichtMunten20cE);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/totaalGewichtMunten10cE")
    void wijzigCash10cE(@PathVariable long kasboekId,
                    @RequestBody @NotNull BigDecimal totaalGewichtMunten10cE) {
        try {
            kasboekService.wijzigTotaalGewichtMunten10cE(kasboekId, totaalGewichtMunten10cE);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PatchMapping("{kasboekId}/totaalGewichtMuntenBruinE")
    void wijzigCashBruinE(@PathVariable long kasboekId,
                    @RequestBody @NotNull BigDecimal totaalGewichtMuntenBruinE) {
        try {
            kasboekService.wijzigTotaalGewichtMuntenBruinE(kasboekId, totaalGewichtMuntenBruinE);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

    @PostMapping("{kasboekId}/verrichtingen")
    void voegVerrichtingToe(@PathVariable long kasboekId,
                            @RequestBody @Valid  NieuweVerrichting nieuweVerrichting) {
        kasboekService.voegVerrichtingToe(kasboekId, nieuweVerrichting);
    }

    @DeleteMapping("{kasboekId}/verrichtingen/{volgnummer}")
    void verwijderVerrichting(@PathVariable long kasboekId, @PathVariable int volgnummer) {
        kasboekService.verwijderVerrichting(kasboekId, volgnummer);
    }

    // aanpassen : geef OUDE volgnummer mee als pathvariabele
    @PatchMapping("{kasboekId}/verrichtingen/{volgnummer}")
    void wijzigVerrichting(@PathVariable long kasboekId, @PathVariable int volgnummer,
                           @RequestBody @Valid  NieuweVerrichting nieuweVerrichting) {
        try {
            kasboekService.wijzigVerrichting(kasboekId, volgnummer, nieuweVerrichting);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }


}
