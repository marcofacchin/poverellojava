package be.vdab.poverello.boekhouding;

import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    Verrichting findById(@PathVariable long id) {
        return verrichtingService.findById(id)
                .orElseThrow(VerrichtingNietGevondenException::new);
    }

    @GetMapping
    List<Verrichting> findAll() {
        return verrichtingService.findAll();
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
