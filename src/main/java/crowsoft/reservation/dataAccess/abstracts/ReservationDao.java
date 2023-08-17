package crowsoft.reservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import crowsoft.reservation.entities.concretes.Reservation;

public interface ReservationDao extends JpaRepository<Reservation,Integer> {
    
}
