package crowsoft.reservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import crowsoft.reservation.entities.concretes.Appointment;

public interface AppointmentDao extends JpaRepository<Appointment,Integer> {
    
}
