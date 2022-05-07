package ba.unsa.etf.ppis.e_ticket_booking_app.repos;


import java.util.UUID;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PictureRepository extends JpaRepository<Picture, UUID> {
}
