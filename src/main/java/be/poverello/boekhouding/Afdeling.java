package be.poverello.boekhouding;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity
@Table(name = "afdelingen")
public class Afdeling {
    @Id
    private long id;
    private String naam;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
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