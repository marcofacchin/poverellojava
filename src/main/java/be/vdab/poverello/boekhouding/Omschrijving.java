package be.vdab.poverello.boekhouding;

import jakarta.persistence.*;

@Entity
@Table(name = "omschrijvingen")
public class Omschrijving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long afdelingId;
    private String inhoud;

    public Omschrijving(long afdelingId, String inhoud) {
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
