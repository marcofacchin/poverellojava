package be.poverello.boekhouding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AfdelingRepository extends JpaRepository<Afdeling, Long> {

    @Query("""
            select a from Afdeling a
            where a.id=:afdelingId
            """)
    Optional<Afdeling> findAfdelingByAfdelingId(long afdelingId);

/*    @Query("""
            select a from Afdeling a
            where a.taal = :taal
            and not (a.id = 1)
            """)
    List<Afdeling> findAfdelingen(Taal taal);*/

    @Query("""
            select a from Afdeling a
            where a.taal = 'NL'
            """)
    List<Afdeling> findAfdelingen(Taal taal);

}
