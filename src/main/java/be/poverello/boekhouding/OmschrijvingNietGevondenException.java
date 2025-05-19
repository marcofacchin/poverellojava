package be.poverello.boekhouding;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OmschrijvingNietGevondenException extends RuntimeException {
    public OmschrijvingNietGevondenException() {
        super("Deze omschrijving werd niet gevonden.");
    }
}
