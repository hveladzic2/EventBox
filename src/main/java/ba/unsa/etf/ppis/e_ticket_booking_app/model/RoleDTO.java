package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private UUID roleID;

    @NotNull
    @Size(max = 255)
    private String name;

}
