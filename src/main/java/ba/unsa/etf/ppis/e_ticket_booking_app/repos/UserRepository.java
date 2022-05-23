package ba.unsa.etf.ppis.e_ticket_booking_app.repos;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    @Query(value = "SELECT * FROM public.user u, role r WHERE r.id = u.role_id AND r.name =:roleName", nativeQuery = true)
    List<User> getUsersByRole(@Param("roleName") String roleName);
    User getUserByUsername(String username);
}
