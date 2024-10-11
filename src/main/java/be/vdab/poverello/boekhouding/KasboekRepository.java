package be.vdab.poverello.boekhouding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KasboekRepository extends JpaRepository<Kasboek, Long> {
    @Query("""
            select k from Kasboek k
            join fetch k.afdeling
            join fetch k.verrichtingen
            join fetch k.verrichtingen.omschrijving
            where k.id=:id
            """)
    Optional<Kasboek> findKasboekByIdMetDetails(long id);

    @Query("""
            select k from Kasboek k
            join fetch k.afdeling
            join fetch k.verrichtingen
            join fetch k.verrichtingen.omschrijving
            where k.afdeling=:afdeling and k.jaar=:jaar and k.maand=:maand
            """)
    Optional<Kasboek> findKasboekByAfdelingJaarMaandMetDetails(Afdeling afdeling, int jaar, int maand);

    @Query("""
            select a from Afdeling a
            where a.id=:afdelingId
            """)
    Optional<Afdeling> findAfdelingByAfdelingId(long afdelingId);
}
