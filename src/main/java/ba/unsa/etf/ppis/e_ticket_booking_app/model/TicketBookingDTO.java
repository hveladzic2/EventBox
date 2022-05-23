package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketBookingDTO {

    private UUID id;

    @NotNull
    private UUID ticketID;

    @NotNull
    private UUID bookingID;

    public TicketBookingDTO() {
    }

    public TicketBookingDTO(UUID ticketID, UUID bookingID) {
        this.ticketID = ticketID;
        this.bookingID = bookingID;
    }
}
