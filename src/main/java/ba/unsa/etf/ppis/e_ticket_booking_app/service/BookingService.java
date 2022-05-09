package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Booking;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.File;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.User;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.BookingDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.BookingRepository;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.FileRepository;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    @Autowired
    private final FileRepository fileRepository;

    public BookingService(final BookingRepository bookingRepository,
            final UserRepository userRepository, final FileRepository fileRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(booking -> mapToDTO(booking, new BookingDTO()))
                .collect(Collectors.toList());
    }

    public BookingDTO get(final UUID id) {
        return bookingRepository.findById(id)
                .map(booking -> mapToDTO(booking, new BookingDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final BookingDTO bookingDTO) {
        final Booking booking = new Booking();
        mapToEntity(bookingDTO, booking);
        return bookingRepository.save(booking).getId();
    }

    public void update(final UUID id, final BookingDTO bookingDTO) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(bookingDTO, booking);
        bookingRepository.save(booking);
    }

    public void delete(final UUID id) {
        bookingRepository.deleteById(id);
    }

    private BookingDTO mapToDTO(final Booking booking, final BookingDTO bookingDTO) {
        bookingDTO.setId(booking.getId());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setAddress(booking.getAddress());
        bookingDTO.setPhone(booking.getPhone());
        bookingDTO.setEmail(booking.getEmail());
        bookingDTO.setTotalNumber(booking.getTotalNumber());
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        bookingDTO.setUserID(booking.getUserID() == null ? null : booking.getUserID().getUserID());
        bookingDTO.setBookingFile(booking.getBookingFile() == null ? null : booking.getBookingFile().getId());
        return bookingDTO;
    }

    private Booking mapToEntity(final BookingDTO bookingDTO, final Booking booking) {
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setAddress(bookingDTO.getAddress());
        booking.setPhone(bookingDTO.getPhone());
        booking.setEmail(bookingDTO.getEmail());
        booking.setTotalNumber(bookingDTO.getTotalNumber());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        if (bookingDTO.getUserID() != null && (booking.getUserID() == null || !booking.getUserID().getUserID().equals(bookingDTO.getUserID()))) {
            final User userID = userRepository.findById(bookingDTO.getUserID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userID not found"));
            booking.setUserID(userID);
        }
        if (bookingDTO.getBookingFile() != null && (booking.getBookingFile() == null || !booking.getBookingFile().getId().equals(bookingDTO.getBookingFile()))) {
            final File bookingFile = fileRepository.findById(bookingDTO.getBookingFile())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookingFile not found"));
            booking.setBookingFile(bookingFile);
        }
        return booking;
    }
    public void deleteAll()
    {
        bookingRepository.deleteAll();
    }

}
