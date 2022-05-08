package ba.unsa.etf.ppis.e_ticket_booking_app.repos;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Concert;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ConcertRepository extends JpaRepository<Concert, UUID> {
    @Query(value = "SELECT * FROM concert c, booking b, ticket_booking l, ticket t, type y where b.userid_id =:userID and b.id = l.bookingid_id and l.ticketid_id=t.ticketid and t.concertid_id = c.concertid and t.typeid_id = y.typeid and y.e_ticket=true", nativeQuery = true)
    List<Concert> getConcertsForUsersBooking(@Param("userID") UUID userID);
}
