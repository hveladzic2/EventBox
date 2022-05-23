package ba.unsa.etf.ppis.e_ticket_booking_app.rest;

import ba.unsa.etf.ppis.e_ticket_booking_app.model.UserDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getUser(@PathVariable final UUID userID) {
        return ResponseEntity.ok(userService.get(userID));
    }



    @GetMapping("/me/{email}")
    public ResponseEntity<UserDTO> getUser(@PathVariable final String email) {
        return ResponseEntity.ok(userService.getByEmailAndPassword(email, "nasiha"));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createUser(@RequestBody @Valid final UserDTO userDTO) {
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{userID}")
    public ResponseEntity<Void> updateUser(@PathVariable final UUID userID,
            @RequestBody @Valid final UserDTO userDTO) {
        userService.update(userID, userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userID}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable final UUID userID) {
        userService.delete(userID);
        return ResponseEntity.noContent().build();
    }

}
