package crowsoft.reservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import crowsoft.reservation.entities.concretes.User;

public interface UserDao extends JpaRepository<User,Integer> {
    
}
