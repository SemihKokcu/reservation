package crowsoft.reservation.business.abstracts;

import java.util.List;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.concretes.Appointment;

public interface AppointmentService {
    DataResult<List<Appointment>> getAll();
    DataResult<Appointment> getById(int id);
    Result add(Appointment appointment);
    Result update(Appointment appointment);
    Result delete(int id);
}
