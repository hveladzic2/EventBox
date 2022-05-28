package ba.unsa.etf.ppis.e_ticket_booking_app.repos;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RezervacijaRepository extends JpaRepository<Rezervacija, UUID> {
}

