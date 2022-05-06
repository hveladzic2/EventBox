package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConcertDTO {

    private UUID concertID;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String musician;

    @NotNull
    @Size(max = 255)
    private String place;

    @NotNull
    private LocalDateTime concertDate;

    @NotNull
    private Integer numberOfTickets;

}
