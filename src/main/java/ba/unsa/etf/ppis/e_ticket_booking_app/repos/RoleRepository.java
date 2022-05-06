package ba.unsa.etf.ppis.e_ticket_booking_app.repos;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Role;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, UUID> {
}
