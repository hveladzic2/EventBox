package ba.unsa.etf.ppis.e_ticket_booking_app.service;

import ba.unsa.etf.ppis.e_ticket_booking_app.domain.Role;
import ba.unsa.etf.ppis.e_ticket_booking_app.model.RoleDTO;
import ba.unsa.etf.ppis.e_ticket_booking_app.repos.RoleRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .collect(Collectors.toList());
    }

    public RoleDTO get(final UUID roleID) {
        return roleRepository.findById(roleID)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UUID create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getRoleID();
    }

    public void update(final UUID roleID, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(roleID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final UUID roleID) {
        roleRepository.deleteById(roleID);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setRoleID(role.getRoleID());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setName(roleDTO.getName());
        return role;
    }

}
