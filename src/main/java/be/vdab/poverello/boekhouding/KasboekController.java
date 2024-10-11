package be.vdab.poverello.boekhouding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

@RestController
@RequestMapping("kasboeken")
public class KasboekController {
    private final KasboekService kasboekService;
    private record VerrichtingItem(
            int volgnummer,
            int dag,
            BigDecimal bedrag,
            long omschrijvingId,
            String omschrijving,
            Boolean kasticket,
            VerrichtingsType verrichtingsType
    ) {
        public VerrichtingItem(Verrichting verrichting) {
            this(
                    verrichting.getVolgnummer(),
                    verrichting.getDag(),
                    verrichting.getBedrag(),
                    verrichting.getOmschrijving().getId(),
                    verrichting.getOmschrijving().getInhoud(),
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
            Stream<VerrichtingItem> verrichtingen
    ) {
        public KasboekBeknopt(Kasboek kasboek) {
            this(
                    kasboek.getId(),
                    kasboek.getAfdelingId(),
                    kasboek.getJaar(),
                    kasboek.getMaand(),
                    kasboek.getVerrichtingen()
                            .stream()
                            .map(verrichting -> new VerrichtingItem(verrichting))
            );
        }
    }

    public KasboekController(KasboekService kasboekService) {
        this.kasboekService = kasboekService;
    }

    @GetMapping("aantal")
    long findAantal() {
        return kasboekService.findAantal();
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

    @PostMapping long create(@RequestBody @Valid NieuwKasboek nieuwKasboek) {
        return kasboekService.create(nieuwKasboek);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        try {
            kasboekService.delete(id);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }

    @PatchMapping("{kasboekId}/afdelingId")
    void wijzigAfdelingId(@PathVariable long kasboekId,
                          @RequestBody @NotNull @Positive long nieuwAfdelingId) {
        try {
            kasboekService.wijzigAfdelingId(kasboekId, nieuwAfdelingId);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new KasboekWerdGewijzigdException();
        }
    }

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

/*    @PostMapping("{kasboekId}/verrichtingen")
    void voegVerrichtingToe(@PathVariable long kasboekId,
                            @RequestBody @Valid  NieuweVerrichting nieuweVerrichting) {
        kasboekService.voegVerrichtingToe(kasboekId, nieuweVerrichting);
    }*/

    @DeleteMapping("{kasboekId}/verrichtingen/{volgnummer}")
    void verwijderVerrichting(@PathVariable long kasboekId, @PathVariable int volgnummer) {
        kasboekService.verwijderVerrichting(kasboekId, volgnummer);
    }
}
