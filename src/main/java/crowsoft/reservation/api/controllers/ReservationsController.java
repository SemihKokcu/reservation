package crowsoft.reservation.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crowsoft.reservation.business.abstracts.ReservationService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.core.utilities.results.SuccessResult;
import crowsoft.reservation.entities.concretes.Reservation;

@RestController
@RequestMapping("api/reservation")
public class ReservationsController {
    
    private ReservationService _reservationService;
    
    @Autowired
    public ReservationsController(ReservationService reservationService) {
        super();
        this._reservationService = reservationService;
    }
    @GetMapping("/getall")
    public DataResult<List<Reservation>> getAll(){
        try {
            return _reservationService.getAll();
        } catch (Exception e) {
            return new ErrorDataResult<List<Reservation>>("Something went wrong when getting all reservation");
        }
    }

    @PostMapping
    public Result add(@RequestBody Reservation reservation){
        return this._reservationService.add(reservation);
    }

    

}
