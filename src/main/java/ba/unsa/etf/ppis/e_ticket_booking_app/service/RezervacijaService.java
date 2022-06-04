package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.*;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.ConcertDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.RezervacijaDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.UserDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class RezervacijaService {

    private final UserService userService;
    private final ConcertService concertService;
    private final FileService fileService;
    private final RezervacijaRepository rezervacijaRepository;
    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;


    public RezervacijaService(final RezervacijaRepository rezervacijaRepository, final UserService userService, final ConcertService concertService, final FileService fileService,
                                final ConcertRepository concertRepository, final UserRepository userRepository, final FileRepository fileRepository) {
        this.rezervacijaRepository = rezervacijaRepository;
        this.concertRepository = concertRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
        this.userService = userService;
        this.concertService = concertService;
    }

    public List<RezervacijaDTO> getRezervacijeForUser(UUID userID) {
        return rezervacijaRepository.getRezervacijeForUser(userID)
                .stream()
                .map(rezervacija -> mapToDTO(rezervacija, new RezervacijaDTO()))
                .collect(Collectors.toList());
    }

    public List<RezervacijaDTO> getERezervacijeForUser(UUID userID) {
        return rezervacijaRepository.getERezervacijeForUser(userID)
                .stream()
                .map(rezervacija -> mapToDTO(rezervacija, new RezervacijaDTO()))
                .collect(Collectors.toList());
    }

    public List<RezervacijaDTO> rezervacijeWithEticket() {
        return rezervacijaRepository.rezervacijeWithEticket()
                .stream()
                .map(rezervacija -> mapToDTO(rezervacija, new RezervacijaDTO()))
                .collect(Collectors.toList());
    }

    public List<RezervacijaDTO> toNotify() {
        return rezervacijaRepository.toNotify()
                .stream()
                .map(rezervacija -> mapToDTO(rezervacija, new RezervacijaDTO()))
                .collect(Collectors.toList());
    }

    public List<RezervacijaDTO> findAll() {
        return rezervacijaRepository.findAll()
                .stream()
                .map(rezervacija -> mapToDTO(rezervacija, new RezervacijaDTO()))
                .collect(Collectors.toList());
    }

    public RezervacijaDTO get(final UUID id) {
        return rezervacijaRepository.findById(id)
                .map(rezervacija -> mapToDTO(rezervacija, new RezervacijaDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final RezervacijaDTO rezervacijaDTO) {
        final Rezervacija rezervacija = new Rezervacija();
        mapToEntity(rezervacijaDTO, rezervacija);
        UUID fileID = generatePDF(rezervacijaDTO.getUserID(), rezervacijaDTO.getConcertID());
        System.out.println(fileID);
        final File bookingFile = fileRepository.findById(fileID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "rezervacijaFile not found"));
        rezervacija.setRezervacijaFile(bookingFile);

        return rezervacijaRepository.save(rezervacija).getId();
    }

    public void update(final UUID id, final RezervacijaDTO rezervacijaDTO) {
        final Rezervacija rezervacija = rezervacijaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(rezervacijaDTO, rezervacija);
        rezervacijaRepository.save(rezervacija);
    }

    public void delete(final UUID id) {
        rezervacijaRepository.deleteById(id);
    }

    private RezervacijaDTO mapToDTO(final Rezervacija rezervacija,
                                      final RezervacijaDTO rezervacijaDTO) {
        rezervacijaDTO.setId(rezervacija.getId());
        rezervacijaDTO.setConcertID(rezervacija.getConcertID() == null ? null : rezervacija.getConcertID().getConcertID());
        rezervacijaDTO.setUserID(rezervacija.getUserID() == null ? null : rezervacija.getUserID().getUserID());
        rezervacijaDTO.setFileID(rezervacija.getRezervacijaFile() == null ? null : rezervacija.getRezervacijaFile().getId());
        rezervacijaDTO.setUserDTO(rezervacija.getUserID() == null ? null : userService.get(rezervacija.getUserID().getUserID()));
        rezervacijaDTO.setConcertDTO(rezervacija.getConcertID() == null ? null : concertService.get(rezervacija.getConcertID().getConcertID()));
        try {
            rezervacijaDTO.setFileDTO(rezervacija.getRezervacijaFile() == null ? null : fileService.get(rezervacija.getRezervacijaFile().getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rezervacijaDTO.setETicket(rezervacija.getETicket());
        return rezervacijaDTO;
    }

    private Rezervacija mapToEntity(final RezervacijaDTO rezervacijaDTO,
                                      final Rezervacija rezervacija) {
        if (rezervacijaDTO.getConcertID() != null && (rezervacija.getConcertID() == null || !rezervacija.getConcertID().getConcertID().equals(rezervacijaDTO.getConcertID()))) {
            final Concert concertID = concertRepository.findById(rezervacijaDTO.getConcertID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "concertID not found"));
            rezervacija.setConcertID(concertID);
        }
        if (rezervacijaDTO.getUserID() != null && (rezervacija.getUserID() == null || !rezervacija.getUserID().getUserID().equals(rezervacijaDTO.getUserID()))) {
            final User userID = userRepository.findById(rezervacijaDTO.getUserID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userID not found"));
            rezervacija.setUserID(userID);
        }
        if (rezervacijaDTO.getFileID() != null && (rezervacija.getRezervacijaFile() == null || !rezervacija.getRezervacijaFile().getId().equals(rezervacijaDTO.getFileID()))) {
            final File bookingFile = fileRepository.findById(rezervacijaDTO.getFileID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "rezervacijaFile not found"));
            rezervacija.setRezervacijaFile(bookingFile);
        }
        rezervacija.setETicket(rezervacijaDTO.isETicket());
        return rezervacija;
    }
    public void deleteAll()
    {
        rezervacijaRepository.deleteAll();
    }
    private UUID generatePDF(UUID userID, UUID concertID) {
        try {
            //Create Document instance.
            Document document = new Document();

            //Create OutputStream instance.
            OutputStream outputStream =
                    new FileOutputStream(new java.io.File("src/main/java/ba/unsa/etf/ppis/e_ticket_booking_app/service/tickets/ticket.pdf"));

            //Create PDFWriter instance.
            Image img = Image.getInstance("src/main/java/ba/unsa/etf/ppis/e_ticket_booking_app/service/tickets/test.jpg");
            img.setAlignment(Element.ALIGN_CENTER);
            PdfWriter.getInstance(document, outputStream);

            Rectangle rect = new Rectangle(800,300);
            rect.setBorderColor(BaseColor.BLACK);
            rect.setBorderWidth(20);
            rect.setBorder(Rectangle.BOX);
            document.setPageSize(rect);
            document.setMargins(2, 2, 2, 2);
            //Open the document.
            document.open();

            ConcertDTO concert = concertService.get(concertID);
            document.addTitle("EVENT BOX");

            Font f = new Font();
            f.setStyle(Font.BOLD);
            //document.add(new Paragraph("EVENT BOX"));
            // Adding image to the document
            document.add(img);
            //Add content to the document.
            LineSeparator l = new LineSeparator();
            Paragraph p1 = new Paragraph("EVENT DETAILS \n");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            Paragraph p2 = new Paragraph( concert.getName() + "\n"  + concert.getConcertDate().getDayOfMonth() +  " " + concert.getConcertDate().getMonth() + " " + concert.getConcertDate().getYear() + " " + concert.getConcertDate().getHour() + ":" + concert.getConcertDate().getMinute() + "\n"   + concert.getPlace());
            p2.setAlignment(Element.ALIGN_CENTER);
            document.add(p2);

            document.add(new Chunk(l));
            UserDTO user = userService.get(userID);
            Paragraph p3 = new Paragraph("USER DETAILS \n");
            p3.setAlignment(Element.ALIGN_CENTER);
            document.add(p3);
            Paragraph p4 = new Paragraph( user.getFirstName() + " " + user.getLastName() + " " + "\n" + user.getEmail());
            p4.setAlignment(Element.ALIGN_CENTER);
            document.add(p4);
            //Close document and outputStream.
            document.add(new Chunk(l));
            Paragraph p5 = new Paragraph("Serial number: " + userID.toString());
            p5.setAlignment(Element.ALIGN_CENTER);
            document.add(p5);
            document.close();
            outputStream.close();

            System.out.println("Pdf created successfully.");

            java.io.File file = new java.io.File("src/main/java/ba/unsa/etf/ppis/e_ticket_booking_app/service/tickets/ticket.pdf");
            FileInputStream fis = new FileInputStream(file);
            MockMultipartFile multipart = new MockMultipartFile("file", file.getName(), "application/pdf", fis);
            UUID fileID=fileService.create(multipart);
            return fileID;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
