package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketDTO {

    private UUID ticketID;

    @NotNull
    @Size(max = 255)
    private String serialNumber;

    @NotNull
    private UUID typeID;

    @NotNull
    private UUID concertID;

    public TicketDTO() {
    }

    public TicketDTO(String serialNumber, UUID typeID, UUID concertID) {
        this.serialNumber = serialNumber;
        this.typeID = typeID;
        this.concertID = concertID;
    }
}
