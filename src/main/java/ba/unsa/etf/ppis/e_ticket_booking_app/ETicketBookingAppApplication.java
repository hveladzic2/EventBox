package ba.unsa.etf.ppis.e_ticket_booking_app;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Concert;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Ticket;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Type;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.User;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.*;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.*;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


@SpringBootApplication
public class ETicketBookingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETicketBookingAppApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo(RezervacijaRepository rezervacijaRepository, RezervacijaService rezervacijaService, FileRepository fileRepository, RoleService roleService, UserService userService, ConcertService concertService, FileService fileService, TypeService typeService, TicketService ticketService, TicketBookingService ticketBookingService, BookingService bookingService) {
        return (args) -> {

            ticketBookingService.deleteAll();
            rezervacijaRepository.deleteAll();
            bookingService.deleteAll();
            ticketService.deleteAll();
            concertService.deleteAll();
            typeService.deleteAll();
            userService.deleteAll();
            roleService.deleteAll();
            fileRepository.deleteAll();


            File file = new File("src/main/java/ba/unsa/etf/ppis/e_ticket_booking_app/files/demo.pdf");
            FileInputStream fis = new FileInputStream(file);
            MockMultipartFile multipart = new MockMultipartFile("file", file.getName(), "application/pdf", fis);
            UUID fileID=fileService.create(multipart);

            File image = new File("src/main/java/ba/unsa/etf/ppis/e_ticket_booking_app/files/image.jpg");
            FileInputStream img = new FileInputStream(image);
            MockMultipartFile mltprt = new MockMultipartFile("file", image.getName(), "image/jpg", img);
            UUID imageID=fileService.create(mltprt);

            UUID roleAdmin=roleService.create(new RoleDTO("ADMIN"));
            UUID roleUser=roleService.create(new RoleDTO("USER"));
            // save a few users
            UUID adminId = userService.create(new UserDTO("admin","admin","admin","admin@nesto.com","PasswordFirst1!",roleAdmin));
            UUID userId = userService.create(new UserDTO("User","User","user","user@nesto.com","PasswordSecond2!",roleUser));

//            UUID typeId = typeService.create(new TypeDTO("V1", 12.4, true));
            UUID concertId = concertService.create(new ConcertDTO("Summer Concert", "Artist", "Sarajevo",imageID, LocalDateTime.of(2023, Month.JANUARY, 1, 10, 30, 00), 100,1000,"Summer nights in the Sarajevo"));
//            UUID ticketId = ticketService.create(new TicketDTO("MM-123-Abfnsj-11", typeId, concertId));
//            UUID bookingId = bookingService.create(new BookingDTO(LocalDateTime.now(), "Adresa bb", "033133133", "user@gmail.com", 2, 2.5, userId, fileID));
//            UUID ticketBookingId = ticketBookingService.create(new TicketBookingDTO(ticketId, bookingId));
            rezervacijaService.create(new RezervacijaDTO(concertId, userId, true));
        };
    }
}
