package be.vdab.poverello.boekhouding;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VerrichtingBestaatAlException extends RuntimeException {
    public VerrichtingBestaatAlException() {
        super("Deze verrichting bestaat al in de database.");
    }
}
