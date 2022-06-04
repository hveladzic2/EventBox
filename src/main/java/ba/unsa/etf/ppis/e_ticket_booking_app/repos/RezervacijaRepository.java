package ba.unsa.etf.ppis.e_ticket_booking_app.repos;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Concert;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, UUID> {
    @Query(value = "SELECT * FROM rezervacija r where r.userid_id =:userID", nativeQuery = true)
    List<Rezervacija> getRezervacijeForUser(@Param("userID") UUID userID);

    @Query(value = "SELECT * FROM rezervacija r where r.userid_id =:userID and r.e_ticket = true", nativeQuery = true)
    List<Rezervacija> getERezervacijeForUser(@Param("userID") UUID userID);

    @Query(value = "SELECT * FROM rezervacija WHERE e_ticket = true", nativeQuery = true)
    List<Rezervacija> rezervacijeWithEticket();

    @Query(value = "SELECT * FROM rezervacija WHERE e_ticket = false", nativeQuery = true)
    List<Rezervacija> toNotify();
}

