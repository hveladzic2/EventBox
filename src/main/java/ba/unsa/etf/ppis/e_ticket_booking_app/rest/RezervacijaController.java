package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import ba.unsa.etf.ppis.e_ticket_booking_app.model.ConcertDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.RezervacijaDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.RezervacijaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/rezervacije", produces = MediaType.APPLICATION_JSON_VALUE)
public class RezervacijaController {

    private final RezervacijaService rezervacijaService;

    public RezervacijaController (final RezervacijaService rezervacijaService) {
        this.rezervacijaService = rezervacijaService;
    }

    @GetMapping
    public ResponseEntity<List<RezervacijaDTO>> getAllRezervacija() {
        return ResponseEntity.ok(rezervacijaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RezervacijaDTO> getRezervacija(@PathVariable final UUID id) {
        return ResponseEntity.ok(rezervacijaService.get(id));
    }

    @GetMapping("/eTicket")
    public ResponseEntity<List<RezervacijaDTO>> rezervacijeWithEticket() {
        return ResponseEntity.ok(rezervacijaService.rezervacijeWithEticket());
    }

    @GetMapping("/toNotify")
    public ResponseEntity<List<RezervacijaDTO>> toNotify() {
        return ResponseEntity.ok(rezervacijaService.toNotify());
    }
    // http://localhost:8080/api/rezervacije/eRezervacije/user?userID=xxx
    @GetMapping("/eRezervacije/user")
    public ResponseEntity<List<RezervacijaDTO>> getERezervacijeForUser(@RequestParam final String userID) {
        return ResponseEntity.ok(rezervacijaService.getERezervacijeForUser(UUID.fromString(userID)));
    }

    // http://localhost:8080/api/rezervacije/user?userID=xxx
    @GetMapping("/user")
    public ResponseEntity<List<RezervacijaDTO>> getRezervacijeForUser(@RequestParam final String userID) {
        return ResponseEntity.ok(rezervacijaService.getRezervacijeForUser(UUID.fromString(userID)));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createRezervacije(
            @RequestBody @Valid final RezervacijaDTO rezervacijaDTO) {
        return new ResponseEntity<>(rezervacijaService.create(rezervacijaDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRezervacija(@PathVariable final UUID id,
                                                    @RequestBody @Valid final RezervacijaDTO rezervacijaDTO) {
        rezervacijaService.update(id, rezervacijaDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRezervacija(@PathVariable final UUID id) {
        rezervacijaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
