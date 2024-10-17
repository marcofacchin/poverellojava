package be.vdab.poverello.boekhouding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KasboekRepository extends JpaRepository<Kasboek, Long> {
    @Query("""
            select k from Kasboek k
            join fetch k.verrichtingen
            where k.id=:id
            """)
    Optional<Kasboek> findKasboekByIdMetDetails(long id);

    @Query("""
            select k from Kasboek k
            join fetch k.verrichtingen
            where k.afdelingId=:afdelingId and k.jaar=:jaar and k.maand=:maand
            """)
    Optional<Kasboek> findKasboekByAfdelingJaarMaandMetDetails(long afdelingId, int jaar, int maand);

    @Query("""
            select distinct k.jaar from Kasboek k
            where k.afdelingId = :afdelingId
            """)
    List<Integer> findJarenVanKasboekenAfdelingId(long afdelingId);

    @Query("""
            select k.maand from Kasboek k
            where k.afdelingId = :afdelingId
            and k.jaar = :jaar
            """)
    List<Integer> findMaandenVanKasboekenAfdelingIdEnJaar(long afdelingId, long jaar);

}
