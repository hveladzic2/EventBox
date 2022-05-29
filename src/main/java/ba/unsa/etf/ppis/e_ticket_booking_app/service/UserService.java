package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Role;
import ba.unsa.etf.ppis.e_ticket_booking_app.domain.User;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.UserDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.RoleRepository;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final UUID userID) {
        return userRepository.findById(userID)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UserDTO getByEmailAndPassword(final String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getUserID();
    }

    public void update(final UUID userID, final UserDTO userDTO) {
        final User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final UUID userID) {
        userRepository.deleteById(userID);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserID(user.getUserID());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoleId(user.getRoleId() == null ? null : user.getRoleId().getRoleID());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        final Role role;
        if (userDTO.getRoleId()==null){
            role = roleRepository.findByName("USER");
        }
        else {
            role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "role not found"));
        }
        user.setRoleId(role);
        return user;
    }
    public UserDTO getUserByUsername(final String username) {
        return mapToDTO(userRepository.getUserByUsername(username),new UserDTO());
    }
    public void deleteAll() {
        userRepository.deleteAll();
    }

}
