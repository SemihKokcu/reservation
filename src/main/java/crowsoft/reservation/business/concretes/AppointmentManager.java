package crowsoft.reservation.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowsoft.reservation.business.abstracts.AppointmentService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.core.utilities.results.SuccessDataResult;
import crowsoft.reservation.core.utilities.results.SuccessResult;
import crowsoft.reservation.dataAccess.abstracts.AppointmentDao;
import crowsoft.reservation.entities.concretes.Appointment;

@Service
public class AppointmentManager implements AppointmentService {

    private AppointmentDao _reservationDao;

    @Autowired
    public AppointmentManager(AppointmentDao reservationDao) {
        super();
        this._reservationDao = reservationDao;
    }
    
    @Override
    public DataResult<List<Appointment>> getAll() {
        return new SuccessDataResult<List<Appointment>>(this._reservationDao.findAll(), "Data Listed");
    }

    @Override
    public Result add(Appointment reservation) {
        this._reservationDao.save(reservation);
        return new SuccessResult("Reservation added");
    }

    @Override
    public DataResult<Appointment> getById(int id) {
        Appointment appointment = this._reservationDao.findById(id).orElse(null);
        if (appointment != null) {
            return new SuccessDataResult<Appointment>(appointment, "Appointment found");
        }
        return new ErrorDataResult<>("Appointment not found");
    }

    @Override
    public Result update(Appointment appointment) {
        this._reservationDao.save(appointment);
        return new SuccessResult("Appointment updated");
    }

    @Override
    public Result delete(int id) {
        this._reservationDao.deleteById(id);
        return new SuccessResult("Appointment deleted");
    }
}
