package be.vdab.poverello.boekhouding;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("verrichtingen")
public class VerrichtingController {
    private final VerrichtingService verrichtingService;

    public VerrichtingController(VerrichtingService verrichtingService) {
        this.verrichtingService = verrichtingService;
    }

    @GetMapping("aantal")
    long findAantal() {
        return verrichtingService.findAantal();
    }

    @GetMapping
    List<Verrichting> findAll() {
        return verrichtingService.findAll();
    }
}
