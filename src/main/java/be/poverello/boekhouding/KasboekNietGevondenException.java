package be.poverello.boekhouding;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KasboekNietGevondenException extends RuntimeException {
    public KasboekNietGevondenException() {
        super("Kasboek werd niet gevonden.");
    }
}
