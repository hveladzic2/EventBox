package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import ba.unsa.etf.ppis.e_ticket_booking_app.model.FileDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.RezervacijaDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    @Autowired
    private final FileService fileService;

    public FileController(final FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        return ResponseEntity.ok(fileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFile(@PathVariable final UUID id) throws IOException {
        return ResponseEntity.ok(fileService.get(id));
    }
    @PostMapping(value = "/uploadFile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileDTO> uploadFileAndGetFile(@RequestPart("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.uploadFileAndGetFile(file), HttpStatus.CREATED);
    }

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.create(file), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateFile(@PathVariable final UUID id,
                                                @RequestPart("file") MultipartFile file) throws IOException {
        fileService.update(id, file);
        return ResponseEntity.ok("Successfully updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable final UUID id) {
        fileService.delete(id);
        return ResponseEntity.ok("Successfully deleted!");
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        fileService.deleteAll();
        return ResponseEntity.ok("Successfully deleted!");
    }

}
