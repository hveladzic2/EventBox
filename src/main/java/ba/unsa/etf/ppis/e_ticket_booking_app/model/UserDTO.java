package ba.unsa.etf.ppis.e_ticket_booking_app.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private UUID userID;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String email;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String username, String email, String password, UUID roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    @NotNull
    @Size(max = 255)
    private String password;

    private UUID roleId;


}
