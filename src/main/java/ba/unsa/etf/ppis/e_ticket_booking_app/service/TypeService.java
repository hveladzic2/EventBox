package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Type;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.TypeDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.TypeRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(final TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<TypeDTO> findAll() {
        return typeRepository.findAll()
                .stream()
                .map(type -> mapToDTO(type, new TypeDTO()))
                .collect(Collectors.toList());
    }

    public TypeDTO get(final UUID typeID) {
        return typeRepository.findById(typeID)
                .map(type -> mapToDTO(type, new TypeDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final TypeDTO typeDTO) {
        final Type type = new Type();
        mapToEntity(typeDTO, type);
        return typeRepository.save(type).getTypeID();
    }

    public void update(final UUID typeID, final TypeDTO typeDTO) {
        final Type type = typeRepository.findById(typeID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(typeDTO, type);
        typeRepository.save(type);
    }

    public void delete(final UUID typeID) {
        typeRepository.deleteById(typeID);
    }

    private TypeDTO mapToDTO(final Type type, final TypeDTO typeDTO) {
        typeDTO.setTypeID(type.getTypeID());
        typeDTO.setSeat(type.getSeat());
        typeDTO.setPrice(type.getPrice());
        typeDTO.setPdfExport(type.getPdfExport());
        return typeDTO;
    }

    private Type mapToEntity(final TypeDTO typeDTO, final Type type) {
        type.setSeat(typeDTO.getSeat());
        type.setPrice(typeDTO.getPrice());
        type.setPdfExport(typeDTO.getPdfExport());
        return type;
    }

}
