package be.vdab.poverello.boekhouding;

import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("verrichtingen")
public class VerrichtingController {
    private final VerrichtingService verrichtingService;

    private record VerrichtingBeknopt(long id, Date datum, BigDecimal bedrag) {
        VerrichtingBeknopt(Verrichting verrichting) {
            this(verrichting.getId(), verrichting.getDatum(), verrichting.getBedrag());
        }
    }
    private record OmschrijvingBeknopt(long id, String inhoud) {
        OmschrijvingBeknopt(Omschrijving omschrijving) {
            this(omschrijving.getId(), omschrijving.getInhoud());
        }
    }
    private record VerrichtingMetOmschrijvingEnType(long id, int afdelingId, int volgnummer, Date datum, BigDecimal bedrag, long omschrijvingId, String omschrijving, long typeId, String type, Boolean kasticket) {
        VerrichtingMetOmschrijvingEnType(Verrichting verrichting) {
            this(verrichting.getId(), verrichting.getAfdelingId(), verrichting.getVolgnummer(), verrichting.getDatum(), verrichting.getBedrag(), verrichting.getOmschrijving().getId(), verrichting.getOmschrijving().getInhoud(), verrichting.getType().getId(), verrichting.getType().getInhoud(), verrichting.getKasticket());
        }
    }
    public VerrichtingController(VerrichtingService verrichtingService) {
        this.verrichtingService = verrichtingService;
    }

    @GetMapping("aantal")
    long findAantal() {
        return verrichtingService.findAantal();
    }

    @GetMapping("{id}")
    VerrichtingBeknopt findById(@PathVariable long id) {
        return verrichtingService.findById(id)
                .map(verrichting -> new VerrichtingBeknopt(verrichting))
                .orElseThrow(VerrichtingNietGevondenException::new);
    }

    @GetMapping
    Stream<VerrichtingMetOmschrijvingEnType> findAll() {
        return verrichtingService.findAllMetOmschrijvingMetType()
                .stream()
                .map(verrichting -> new VerrichtingMetOmschrijvingEnType(verrichting));
    }

    @GetMapping(params = "afdelingId")
    Stream<VerrichtingMetOmschrijvingEnType> findByAfdelingId(int afdelingId) {
        return verrichtingService.findByAfdelingId(afdelingId)
                .stream()
                .map(verrichting -> new VerrichtingMetOmschrijvingEnType(verrichting));
    }

    @GetMapping("{id}/omschrijving")
    OmschrijvingBeknopt findOmschrijvingVan(@PathVariable long id) {
        return verrichtingService.findById(id)
                .map(verrichting -> new OmschrijvingBeknopt(verrichting.getOmschrijving()))
                .orElseThrow(VerrichtingNietGevondenException::new);
    }

    @PostMapping
    long create(@RequestBody @Valid NieuweVerrichting nieuweVerrichting) {
        return verrichtingService.create(nieuweVerrichting);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        try {
            verrichtingService.delete(id);
        } catch (EmptyResultDataAccessException ex) {
        }
    }

}
