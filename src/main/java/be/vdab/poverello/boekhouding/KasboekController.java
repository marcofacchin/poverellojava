package be.vdab.poverello.boekhouding;

import jakarta.validation.Valid;
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
}
