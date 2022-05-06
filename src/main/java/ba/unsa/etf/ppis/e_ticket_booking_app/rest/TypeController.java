package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import ba.unsa.etf.ppis.e_ticket_booking_app.model.TypeDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.TypeService;
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
@RequestMapping(value = "/api/types", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeController {

    private final TypeService typeService;

    public TypeController(final TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        return ResponseEntity.ok(typeService.findAll());
    }

    @GetMapping("/{typeID}")
    public ResponseEntity<TypeDTO> getType(@PathVariable final UUID typeID) {
        return ResponseEntity.ok(typeService.get(typeID));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createType(@RequestBody @Valid final TypeDTO typeDTO) {
        return new ResponseEntity<>(typeService.create(typeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{typeID}")
    public ResponseEntity<Void> updateType(@PathVariable final UUID typeID,
            @RequestBody @Valid final TypeDTO typeDTO) {
        typeService.update(typeID, typeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{typeID}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteType(@PathVariable final UUID typeID) {
        typeService.delete(typeID);
        return ResponseEntity.noContent().build();
    }

}
