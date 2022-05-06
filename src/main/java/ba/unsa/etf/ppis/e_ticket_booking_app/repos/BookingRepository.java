package ba.unsa.etf.ppis.e_ticket_booking_app.repos;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Booking;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
