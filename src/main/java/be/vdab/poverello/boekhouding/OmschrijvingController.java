package be.vdab.poverello.boekhouding;

import jakarta.validation.constraints.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("omschrijvingen")
public class OmschrijvingController {
    private final OmschrijvingService omschrijvingService;

    public OmschrijvingController(OmschrijvingService omschrijvingService) {
        this.omschrijvingService = omschrijvingService;
    }

    private record OmschrijvingBeknopt(long id, String inhoud) {
        OmschrijvingBeknopt(Omschrijving omschrijving) {
            this(omschrijving.getId(), omschrijving.getInhoud());
        }
    }

    @GetMapping("{afdelingId}")
    Stream<OmschrijvingBeknopt> findAllByAfdelingId(@PathVariable long afdelingId) {
        return omschrijvingService.findAllByAfdelingIdOrderByInhoud(afdelingId)
                .stream().map(omschrijving -> new OmschrijvingBeknopt(omschrijving));
    }

    @DeleteMapping("{id}")
    void deleteById(@PathVariable long id) {
        try {
            omschrijvingService.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new OmschrijvingNietGevondenException();
        }
    }

    @PostMapping("{afdelingId}/exists")
    Boolean existsByAfdelingId(@PathVariable long afdelingId,
                               @RequestBody @NotNull String inhoud) {
        return omschrijvingService.existsByAfdelingIdAndInhoud(afdelingId, inhoud);
    }


}
