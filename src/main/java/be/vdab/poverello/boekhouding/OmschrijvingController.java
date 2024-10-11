package be.vdab.poverello.boekhouding;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
        return omschrijvingService.findAllByAfdelingId(afdelingId)
                .stream().map(omschrijving -> new OmschrijvingBeknopt(omschrijving));
    }


}
