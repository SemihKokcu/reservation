package crowsoft.reservation.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import crowsoft.reservation.business.abstracts.AppointmentService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.entities.concretes.Appointment;

import org.springframework.validation.FieldError;
@RestController
@RequestMapping("api/reservation")
public class AppointmentController {
    
    private AppointmentService _reservationService;
    
    @Autowired
    public AppointmentController(AppointmentService reservationService) {
        super();
        this._reservationService = reservationService;
    }
    @GetMapping("/getall")
    public DataResult<List<Appointment>> getAll(){
        try {
            return _reservationService.getAll();
        } catch (Exception e) {
            return new ErrorDataResult<List<Appointment>>("Something went wrong when getting all reservation");
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Appointment reservation){
        return ResponseEntity.ok(this._reservationService.add(reservation));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
        Map<String, String> validationErrors = new HashMap<String, String>();
        for(FieldError fieldError:exceptions.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors,"Doğrılama Hataları");
        return errors;
    }
    

}
