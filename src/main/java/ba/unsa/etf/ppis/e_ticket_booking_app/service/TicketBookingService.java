package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Booking;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Ticket;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.TicketBooking;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.TicketBookingDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.BookingRepository;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.TicketBookingRepository;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.TicketRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TicketBookingService {

    private final TicketBookingRepository ticketBookingRepository;
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;

    public TicketBookingService(final TicketBookingRepository ticketBookingRepository,
            final TicketRepository ticketRepository, final BookingRepository bookingRepository) {
        this.ticketBookingRepository = ticketBookingRepository;
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<TicketBookingDTO> findAll() {
        return ticketBookingRepository.findAll()
                .stream()
                .map(ticketBooking -> mapToDTO(ticketBooking, new TicketBookingDTO()))
                .collect(Collectors.toList());
    }

    public TicketBookingDTO get(final UUID id) {
        return ticketBookingRepository.findById(id)
                .map(ticketBooking -> mapToDTO(ticketBooking, new TicketBookingDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final TicketBookingDTO ticketBookingDTO) {
        final TicketBooking ticketBooking = new TicketBooking();
        mapToEntity(ticketBookingDTO, ticketBooking);
        return ticketBookingRepository.save(ticketBooking).getId();
    }

    public void update(final UUID id, final TicketBookingDTO ticketBookingDTO) {
        final TicketBooking ticketBooking = ticketBookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(ticketBookingDTO, ticketBooking);
        ticketBookingRepository.save(ticketBooking);
    }

    public void delete(final UUID id) {
        ticketBookingRepository.deleteById(id);
    }

    private TicketBookingDTO mapToDTO(final TicketBooking ticketBooking,
            final TicketBookingDTO ticketBookingDTO) {
        ticketBookingDTO.setId(ticketBooking.getId());
        ticketBookingDTO.setTicketID(ticketBooking.getTicketID() == null ? null : ticketBooking.getTicketID().getTicketID());
        ticketBookingDTO.setBookingID(ticketBooking.getBookingID() == null ? null : ticketBooking.getBookingID().getId());
        return ticketBookingDTO;
    }

    private TicketBooking mapToEntity(final TicketBookingDTO ticketBookingDTO,
            final TicketBooking ticketBooking) {
        if (ticketBookingDTO.getTicketID() != null && (ticketBooking.getTicketID() == null || !ticketBooking.getTicketID().getTicketID().equals(ticketBookingDTO.getTicketID()))) {
            final Ticket ticketID = ticketRepository.findById(ticketBookingDTO.getTicketID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ticketID not found"));
            ticketBooking.setTicketID(ticketID);
        }
        if (ticketBookingDTO.getBookingID() != null && (ticketBooking.getBookingID() == null || !ticketBooking.getBookingID().getId().equals(ticketBookingDTO.getBookingID()))) {
            final Booking bookingID = bookingRepository.findById(ticketBookingDTO.getBookingID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookingID not found"));
            ticketBooking.setBookingID(bookingID);
        }
        return ticketBooking;
    }

}
