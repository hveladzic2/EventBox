package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Concert;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.File;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.ConcertDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.ConcertRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import ba.unsa.etf.ppis.e_ticket_booking_app.repos.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    @Autowired
    private final FileRepository fileRepository;

    public ConcertService(final ConcertRepository concertRepository, final FileRepository fileRepository) {
        this.concertRepository = concertRepository;
        this.fileRepository = fileRepository;
    }

    public List<ConcertDTO> findAll() {
        return concertRepository.findAll()
                .stream()
                .map(concert -> mapToDTO(concert, new ConcertDTO()))
                .collect(Collectors.toList());
    }

    public ConcertDTO get(final UUID concertID) {
        return concertRepository.findById(concertID)
                .map(concert -> mapToDTO(concert, new ConcertDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final ConcertDTO concertDTO) {
        final Concert concert = new Concert();
        mapToEntity(concertDTO, concert);
        return concertRepository.save(concert).getConcertID();
    }

    public void update(final UUID concertID, final ConcertDTO concertDTO) {
        final Concert concert = concertRepository.findById(concertID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(concertDTO, concert);
        concertRepository.save(concert);
    }

    public void delete(final UUID concertID) {
        concertRepository.deleteById(concertID);
    }

    private ConcertDTO mapToDTO(final Concert concert, final ConcertDTO concertDTO) {
        concertDTO.setConcertID(concert.getConcertID());
        concertDTO.setName(concert.getName());
        concertDTO.setMusician(concert.getMusician());
        concertDTO.setPlace(concert.getPlace());
        concertDTO.setConcertDate(concert.getConcertDate());
        concertDTO.setNumberOfTickets(concert.getNumberOfTickets());
        concertDTO.setConcertFile(concert.getConcertFile() == null ? null : concert.getConcertFile().getId());
        return concertDTO;
    }

    private Concert mapToEntity(final ConcertDTO concertDTO, final Concert concert) {
        concert.setName(concertDTO.getName());
        concert.setMusician(concertDTO.getMusician());
        concert.setPlace(concertDTO.getPlace());
        concert.setConcertDate(concertDTO.getConcertDate());
        concert.setNumberOfTickets(concertDTO.getNumberOfTickets());
        if (concertDTO.getConcertFile() != null && (concert.getConcertFile() == null || !concert.getConcertFile().getId().equals(concertDTO.getConcertFile()))) {
            final File concertFile = fileRepository.findById(concertDTO.getConcertFile())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "concertFile not found"));
            concert.setConcertFile(concertFile);
        }
        return concert;
    }

}
