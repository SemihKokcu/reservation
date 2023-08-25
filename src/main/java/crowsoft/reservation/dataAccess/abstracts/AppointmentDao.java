package crowsoft.reservation.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import crowsoft.reservation.entities.concretes.Appointment;

public interface AppointmentDao extends JpaRepository<Appointment,Integer> {
    List<Appointment> findByDoctorId(int id);
    List<Appointment> findByPatientId(int id);
}
