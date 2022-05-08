package ba.unsa.etf.ppis.e_ticket_booking_app.model;


import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FileDTO {

    private UUID id;

    private String name;
    private String type;
    private byte[] fileByte;

    public FileDTO(String name, String type, byte[] fileByte) {
        this.name = name;
        this.type = type;
        this.fileByte = fileByte;
    }

    public FileDTO() {
    }
}