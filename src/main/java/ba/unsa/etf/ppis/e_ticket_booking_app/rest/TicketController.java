package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Ticket;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.TicketDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.TicketService;
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
@RequestMapping(value = "/api/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {

    private final TicketService ticketService;

    public TicketController(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{ticketID}")
    public ResponseEntity<TicketDTO> getTicket(@PathVariable final UUID ticketID) {
        return ResponseEntity.ok(ticketService.get(ticketID));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createTicket(@RequestBody @Valid final TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketService.create(ticketDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{ticketID}")
    public ResponseEntity<Void> updateTicket(@PathVariable final UUID ticketID,
            @RequestBody @Valid final TicketDTO ticketDTO) {
        ticketService.update(ticketID, ticketDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ticketID}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTicket(@PathVariable final UUID ticketID) {
        ticketService.delete(ticketID);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/userTickets/{userID}")
    public ResponseEntity<List<TicketDTO>> getAllEticketsForUser(@PathVariable final UUID userID) {
        return ResponseEntity.ok(ticketService.getAllEticketsForUser(userID));
    }
        @GetMapping("/adminTickets")
        public ResponseEntity<List<TicketDTO>> getAllLiveTicketsForAdmin() {
            return ResponseEntity.ok(ticketService.getAllLiveTicketsForAdmin());
    }
}
