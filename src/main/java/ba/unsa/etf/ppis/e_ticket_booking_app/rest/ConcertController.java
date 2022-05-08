package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import ba.unsa.etf.ppis.e_ticket_booking_app.model.ConcertDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.ConcertService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/concerts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(final ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping
    public ResponseEntity<List<ConcertDTO>> getAllConcerts() {
        return ResponseEntity.ok(concertService.findAll());
    }

    @GetMapping("/{concertID}")
    public ResponseEntity<ConcertDTO> getConcert(@PathVariable final UUID concertID) {
        return ResponseEntity.ok(concertService.get(concertID));
    }
    @GetMapping("/user/booking")
    public ResponseEntity<List<ConcertDTO>> getConcertsForUsersBooking(@RequestParam final String userId, @RequestParam final String bookingId) {
        return ResponseEntity.ok(concertService.getConcertsForUsersBooking(UUID.fromString(userId), UUID.fromString(bookingId)));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createConcert(@RequestBody @Valid final ConcertDTO concertDTO) {
        return new ResponseEntity<>(concertService.create(concertDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{concertID}")
    public ResponseEntity<Void> updateConcert(@PathVariable final UUID concertID,
            @RequestBody @Valid final ConcertDTO concertDTO) {
        concertService.update(concertID, concertDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{concertID}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteConcert(@PathVariable final UUID concertID) {
        concertService.delete(concertID);
        return ResponseEntity.noContent().build();
    }

}
