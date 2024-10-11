package be.vdab.poverello.boekhouding;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class KasboekBestaatAlExcpetion extends RuntimeException {
    public KasboekBestaatAlExcpetion() {
        super("Dit kasboek bestaat reeds.");
    }
}
