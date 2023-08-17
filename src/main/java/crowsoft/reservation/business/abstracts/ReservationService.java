package crowsoft.reservation.business.abstracts;

import java.util.List;

import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.concretes.Reservation;

public interface ReservationService {
    DataResult<List<Reservation>> getAll();
    Result add(Reservation reservation);
}
