package be.vdab.poverello.boekhouding;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "omschrijvingen")
public class Omschrijving {
    @Id
    private long id;
    private String inhoud;

    public Omschrijving(long id, String inhoud) {
        this.id = id;
        this.inhoud = inhoud;
    }

    public Omschrijving() {
    }

    public long getId() {
        return id;
    }

    public String getInhoud() {
        return inhoud;
    }
}
