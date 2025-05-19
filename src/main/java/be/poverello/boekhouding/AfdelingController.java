package be.poverello.boekhouding;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("afdelingen")
public class AfdelingController {
    private final AfdelingService afdelingService;

    public AfdelingController(AfdelingService afdelingService) {
        this.afdelingService = afdelingService;
    }

    private record AfdelingBeknopt(long id, String naam) {
        AfdelingBeknopt(Afdeling afdeling) {
            this(afdeling.getId(), afdeling.getNaam());
        }
    }

    @GetMapping()
    Stream<AfdelingBeknopt> findAfdelingen() {
        return afdelingService.findAfdelingen(Taal.NL).stream().map(afdeling -> new AfdelingBeknopt(afdeling));
    }
}
