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

    public ConcertDTO(String name, String musician, String place, UUID concertFile, LocalDateTime concertDate, Integer numberOfTickets) {
        this.name = name;
        this.musician = musician;
        this.place = place;
        this.concertFile = concertFile;
        this.concertDate = concertDate;
        this.numberOfTickets = numberOfTickets;
    }

    @NotNull
    @Size(max = 255)
    private String musician;

    public ConcertDTO() {
    }

    private FileDTO fileDTO;

    @NotNull
    @Size(max = 255)
    private String place;

    @NotNull
    private UUID concertFile;

    @NotNull
    private LocalDateTime concertDate;

    @NotNull
    private Integer numberOfTickets;

}
