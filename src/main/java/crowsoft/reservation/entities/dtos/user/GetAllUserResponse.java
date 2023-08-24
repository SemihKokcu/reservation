package crowsoft.reservation.entities.dtos.user;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllUserResponse {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private List<SimpleGrantedAuthority> roles;
}
