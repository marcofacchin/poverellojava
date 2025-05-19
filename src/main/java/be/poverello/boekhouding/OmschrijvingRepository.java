package be.poverello.boekhouding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OmschrijvingRepository extends JpaRepository<Omschrijving, Long> {

    List<Omschrijving> findAllByAfdelingIdOrderByInhoud(long afdelingId);

    @Query("""
            select o from Omschrijving o where o.afdelingId in (1, :afdelingId)
            order by o.inhoud
            """)
    List<Omschrijving> findAllByAfdelingIdAndAfdelingId1OrderByInhoud(long afdelingId);

    Boolean existsByAfdelingIdAndInhoud(long afdelingId, String inhoud);

    Optional<Omschrijving> findByAfdelingIdAndInhoud(long afdelingId, String inhoud);

/*    @Query("""
            delete Omschrijving o where o.id = :id
            """)
    void deleteById(long id);*/
}
