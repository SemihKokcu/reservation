package crowsoft.reservation.business.abstracts;

import java.util.List;

import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.concretes.Doctor;

public interface DoctorService {
     DataResult<List<Doctor>> getAll();
    DataResult<Doctor> getById(int id);
    Result add(Doctor doctor);
    Result update(Doctor doctor);
    Result delete(int id);
}
