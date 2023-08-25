package crowsoft.reservation.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowsoft.reservation.business.abstracts.DoctorService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.core.utilities.results.SuccessDataResult;
import crowsoft.reservation.core.utilities.results.SuccessResult;
import crowsoft.reservation.dataAccess.abstracts.AppointmentDao;
import crowsoft.reservation.dataAccess.abstracts.DoctorDao;
import crowsoft.reservation.entities.concretes.Appointment;
import crowsoft.reservation.entities.concretes.Doctor;

@Service
public class DoctorManager implements DoctorService {

    private DoctorDao doctorDao;
    private AppointmentDao appointmentDao;

    @Autowired
    public DoctorManager(DoctorDao doctorDao,AppointmentDao appointmentDao) {
        this.doctorDao = doctorDao;
        this.appointmentDao = appointmentDao;
    }

    @Override
    public DataResult<List<Doctor>> getAll() {
        List<Doctor> doctors = this.doctorDao.findAll();
        return new SuccessDataResult<>(doctors, "Doctors listed successfully.");
    }

    @Override
    public DataResult<Doctor> getById(int id) {
        Doctor doctor = this.doctorDao.findById(id).orElse(null);
        if (doctor != null) {
            return new SuccessDataResult<>(doctor, "Doctor found.");
        }
        return new ErrorDataResult<Doctor>("Doctor not found.");
    }

    @Override
    public Result add(Doctor doctor) {
        this.doctorDao.save(doctor);
        return new SuccessResult("Doctor added successfully.");
    }

    @Override
    public Result update(Doctor doctor) {
        this.doctorDao.save(doctor);
        return new SuccessResult("Doctor updated successfully.");
    }

    @Override
    public Result delete(int id) {
        List<Appointment> appointments =  this.appointmentDao.findByDoctorId(id);
        for (Appointment appointment :appointments){
            this.appointmentDao.deleteById(appointment.getId());
        }
        this.doctorDao.deleteById(id);
        return new SuccessResult("Doctor deleted successfully.");
    }

   

   
}
