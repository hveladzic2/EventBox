package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RezervacijaDTO {

    private UUID id;

    @NotNull
    private UUID concertID;

    @NotNull
    private UUID userID;

    private UserDTO userDTO;

    private ConcertDTO concertDTO;

    private FileDTO fileDTO;

    private UUID fileID;

    private boolean eTicket;

    public RezervacijaDTO() {
    }
    public RezervacijaDTO(UUID concertID, UUID userID, boolean eTicket) {
        this.concertID = concertID;
        this.userID = userID;
        this.eTicket = eTicket;
    }
}
