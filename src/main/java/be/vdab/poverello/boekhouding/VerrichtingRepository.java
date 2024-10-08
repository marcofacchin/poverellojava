package be.vdab.poverello.boekhouding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VerrichtingRepository extends JpaRepository<Verrichting, Long> {

    @Query("select v from Verrichting v join fetch v.omschrijving join fetch v.type")
    List<Verrichting> findAllMetOmschrijvingEnType();

    List<Verrichting> findByAfdelingId(int id);



}
