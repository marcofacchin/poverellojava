package be.vdab.poverello.boekhouding;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OmschrijvingRepository extends JpaRepository<Omschrijving, Long> {

    List<Omschrijving> findAllByAfdelingIdOrderByInhoud(long afdelingId);

    Optional<Omschrijving> findByAfdelingIdAndId(long afdelingId, long id);

    void deleteByAfdelingIdAndId(long afdelingId, long id);
}
