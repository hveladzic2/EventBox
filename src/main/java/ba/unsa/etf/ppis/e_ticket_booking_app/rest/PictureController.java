package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import ba.unsa.etf.ppis.e_ticket_booking_app.model.PictureDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/pictures", produces = MediaType.APPLICATION_JSON_VALUE)
public class PictureController {

    @Autowired
    private final PictureService pictureService;

    public PictureController(final PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping
    public ResponseEntity<List<PictureDTO>> getAllPictures() {
        return ResponseEntity.ok(pictureService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureDTO> getPicture(@PathVariable final UUID id) throws IOException {
        return ResponseEntity.ok(pictureService.get(id));
    }

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> uploadPicture(@RequestPart("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(pictureService.create(file), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updatePicture(@PathVariable final UUID id,
                                                @RequestPart("file") MultipartFile file) throws IOException {
        pictureService.update(id, file);
        return ResponseEntity.ok("Successfully updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePicture(@PathVariable final UUID id) {
        pictureService.delete(id);
        return ResponseEntity.ok("Successfully delated!");
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        pictureService.deleteAll();
        return ResponseEntity.ok("Successfully deleted!");
    }

}
