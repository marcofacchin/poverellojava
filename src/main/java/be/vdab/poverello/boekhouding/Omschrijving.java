package be.vdab.poverello.boekhouding;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "omschrijvingen")
public class Omschrijving {
    @Id
    private long id;
    private long afdelingId;
    private String inhoud;

    public Omschrijving(long id, long afdelingId, String inhoud) {
        this.id = id;
        this.afdelingId = afdelingId;
        this.inhoud = inhoud;
    }

    public Omschrijving() {
    }

    public long getId() {
        return id;
    }

    public long getAfdelingId() {
        return afdelingId;
    }

    public String getInhoud() {
        return inhoud;
    }
}
