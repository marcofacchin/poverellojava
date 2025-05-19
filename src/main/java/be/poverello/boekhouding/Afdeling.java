package be.poverello.boekhouding;

import jakarta.persistence.*;

@Entity
@Table(name = "afdelingen")
public class Afdeling {
    @Id
    private long id;
    private String naam;
    @Enumerated(EnumType.STRING)
    private Taal taal;

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Taal getTaal() {
        return taal;
    }
}