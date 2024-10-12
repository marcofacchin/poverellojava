package be.vdab.poverello.boekhouding;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OmschrijvingRepository extends JpaRepository<Omschrijving, Long> {

    List<Omschrijving> findAllByAfdelingIdOrderByInhoud(long afdelingId);


}
