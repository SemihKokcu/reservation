package crowsoft.reservation.dataAccess.abstracts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crowsoft.reservation.core.entities.User;

public interface UserDao extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
}
