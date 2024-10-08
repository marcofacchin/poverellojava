package be.vdab.poverello.boekhouding;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "types")
public class VerrichtingsType {
    @Id
    private long id;
    private String inhoud;

    public VerrichtingsType(long id, String inhoud) {
        this.id = id;
        this.inhoud = inhoud;
    }

    public VerrichtingsType() {
    }

    public long getId() {
        return id;
    }

    public String getInhoud() {
        return inhoud;
    }
}
