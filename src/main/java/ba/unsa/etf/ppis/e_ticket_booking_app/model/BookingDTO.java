package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingDTO {

    private UUID id;

    @NotNull
    private LocalDateTime bookingDate;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 255)
    private String phone;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private Integer totalNumber;

    @NotNull
    private Double totalPrice;

    @NotNull
    private UUID userID;

}
