package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Concert;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Ticket;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Type;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.TicketDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.ConcertRepository;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.TicketRepository;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.TypeRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TypeRepository typeRepository;
    private final ConcertRepository concertRepository;

    public TicketService(final TicketRepository ticketRepository,
            final TypeRepository typeRepository, final ConcertRepository concertRepository) {
        this.ticketRepository = ticketRepository;
        this.typeRepository = typeRepository;
        this.concertRepository = concertRepository;
    }

    public List<TicketDTO> findAll() {
        return ticketRepository.findAll()
                .stream()
                .map(ticket -> mapToDTO(ticket, new TicketDTO()))
                .collect(Collectors.toList());
    }

    public TicketDTO get(final UUID ticketID) {
        return ticketRepository.findById(ticketID)
                .map(ticket -> mapToDTO(ticket, new TicketDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final TicketDTO ticketDTO) {
        final Ticket ticket = new Ticket();
        mapToEntity(ticketDTO, ticket);
        return ticketRepository.save(ticket).getTicketID();
    }

    public void update(final UUID ticketID, final TicketDTO ticketDTO) {
        final Ticket ticket = ticketRepository.findById(ticketID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(ticketDTO, ticket);
        ticketRepository.save(ticket);
    }

    public void delete(final UUID ticketID) {
        ticketRepository.deleteById(ticketID);
    }

    private TicketDTO mapToDTO(final Ticket ticket, final TicketDTO ticketDTO) {
        ticketDTO.setTicketID(ticket.getTicketID());
        ticketDTO.setSerialNumber(ticket.getSerialNumber());
        ticketDTO.setTypeID(ticket.getTypeID() == null ? null : ticket.getTypeID().getTypeID());
        ticketDTO.setConcertID(ticket.getConcertID() == null ? null : ticket.getConcertID().getConcertID());
        return ticketDTO;
    }

    private Ticket mapToEntity(final TicketDTO ticketDTO, final Ticket ticket) {
        ticket.setSerialNumber(ticketDTO.getSerialNumber());
        if (ticketDTO.getTypeID() != null && (ticket.getTypeID() == null || !ticket.getTypeID().getTypeID().equals(ticketDTO.getTypeID()))) {
            final Type typeID = typeRepository.findById(ticketDTO.getTypeID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "typeID not found"));
            ticket.setTypeID(typeID);
        }
        if (ticketDTO.getConcertID() != null && (ticket.getConcertID() == null || !ticket.getConcertID().getConcertID().equals(ticketDTO.getConcertID()))) {
            final Concert concertID = concertRepository.findById(ticketDTO.getConcertID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "concertID not found"));
            ticket.setConcertID(concertID);
        }
        return ticket;
    }

}
