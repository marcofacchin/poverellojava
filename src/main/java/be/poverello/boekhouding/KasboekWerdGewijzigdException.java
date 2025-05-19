package be.poverello.boekhouding;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class KasboekWerdGewijzigdException extends RuntimeException {
    public KasboekWerdGewijzigdException() {
        super("Een andere gebruiker wijzigde het kasboek.");
    }
}
