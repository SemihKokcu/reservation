package crowsoft.reservation.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowsoft.reservation.business.abstracts.ReservationService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.core.utilities.results.SuccessDataResult;
import crowsoft.reservation.core.utilities.results.SuccessResult;
import crowsoft.reservation.dataAccess.abstracts.ReservationDao;
import crowsoft.reservation.entities.concretes.Reservation;

@Service
public class ReservationManager implements ReservationService {

    private ReservationDao _reservationDao;

    @Autowired
    public ReservationManager(ReservationDao reservationDao) {
        super();
        this._reservationDao = reservationDao;
    }
    @Override
    public DataResult<List<Reservation>> getAll() {
       return new SuccessDataResult<List<Reservation>>
            (this._reservationDao.findAll(),"Data Listed");
    }
    @Override
    public Result add(Reservation reservation) {
        this._reservationDao.save(reservation);
        return new SuccessResult("Reservation added");
    }
    
}
