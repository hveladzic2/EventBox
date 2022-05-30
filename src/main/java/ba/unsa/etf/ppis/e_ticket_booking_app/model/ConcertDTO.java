package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConcertDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID concertID;

    @NotNull
    @Size(max = 255)
    private String name;

    public ConcertDTO(String name, String musician, String place, UUID concertFile, LocalDateTime concertDate, Integer numberOfTickets,Integer price,String description) {
        this.name = name;
        this.musician = musician;
        this.place = place;
        this.concertFile = concertFile;
        this.concertDate = concertDate;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
        this.description = description;
    }

    @Size(max = 255)
    private String musician;

    public ConcertDTO() {
    }

    private FileDTO fileDTO;

    @NotNull
    @Size(max = 255)
    private String place;

    private UUID concertFile;

    @NotNull
    private LocalDateTime concertDate;


    private Integer numberOfTickets;

    @NotNull
    private Integer price;

    @NotNull
    private String description;
}
