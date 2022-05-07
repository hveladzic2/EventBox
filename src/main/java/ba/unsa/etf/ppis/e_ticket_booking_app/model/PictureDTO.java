package ba.unsa.etf.ppis.e_ticket_booking_app.model;


import java.util.UUID;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PictureDTO {

    private UUID id;

    private String name;
    private String type;
    private byte[] picByte;

    public PictureDTO(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public PictureDTO() {
    }
}