package crowsoft.reservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import crowsoft.reservation.entities.concretes.Doctor;

public interface DoctorDao extends  JpaRepository<Doctor,Integer> {
    
    
}
