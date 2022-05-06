package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import ba.unsa.etf.ppis.e_ticket_booking_app.model.TicketBookingDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.TicketBookingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/ticketBookings", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketBookingController {

    private final TicketBookingService ticketBookingService;

    public TicketBookingController(final TicketBookingService ticketBookingService) {
        this.ticketBookingService = ticketBookingService;
    }

    @GetMapping
    public ResponseEntity<List<TicketBookingDTO>> getAllTicketBookings() {
        return ResponseEntity.ok(ticketBookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketBookingDTO> getTicketBooking(@PathVariable final UUID id) {
        return ResponseEntity.ok(ticketBookingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createTicketBooking(
            @RequestBody @Valid final TicketBookingDTO ticketBookingDTO) {
        return new ResponseEntity<>(ticketBookingService.create(ticketBookingDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTicketBooking(@PathVariable final UUID id,
            @RequestBody @Valid final TicketBookingDTO ticketBookingDTO) {
        ticketBookingService.update(id, ticketBookingDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTicketBooking(@PathVariable final UUID id) {
        ticketBookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
