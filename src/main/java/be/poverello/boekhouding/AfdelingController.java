package be.poverello.boekhouding;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{taal}")
    Stream<AfdelingBeknopt> findAfdelingen(@PathVariable Taal taal) {
        //return afdelingService.findAfdelingen(be.poverello.boekhouding.Taal.NL).stream().map(afdeling -> new AfdelingBeknopt(afdeling));
        return afdelingService.findAfdelingen(taal).stream().map(afdeling -> new AfdelingBeknopt(afdeling));
    }
}
