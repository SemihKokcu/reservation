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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import crowsoft.reservation.business.abstracts.AppointmentService;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.entities.concretes.Appointment;
import crowsoft.reservation.entities.dtos.appointment.AppointmentDTO;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByDoctorIdResponse;

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
    public ResponseEntity<DataResult<List<AppointmentDTO>>> getAll() {
        try {
            DataResult<List<AppointmentDTO>> result = _reservationService.getAllAppointmentsWithDetails();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDataResult<>("Something went wrong when getting all doctors"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get/{id}/appointment")
    public ResponseEntity<DataResult<List<GetAppointmentByDoctorIdResponse>>> getAppointmentById(@PathVariable int id) {
            DataResult<List<GetAppointmentByDoctorIdResponse>> result = _reservationService.getAppointmentsByDoctorr(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/add")
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
