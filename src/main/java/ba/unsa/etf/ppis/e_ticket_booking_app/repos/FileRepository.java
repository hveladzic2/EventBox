package ba.unsa.etf.ppis.e_ticket_booking_app.repos;


import java.util.UUID;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<File, UUID> {
}
