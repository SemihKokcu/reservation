package crowsoft.reservation.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import crowsoft.reservation.business.abstracts.AppointmentService;
import crowsoft.reservation.config.JwtService;
import crowsoft.reservation.core.entities.User;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.dataAccess.abstracts.UserDao;
import crowsoft.reservation.entities.concretes.Appointment;
import crowsoft.reservation.entities.dtos.appointment.AppointmentDTO;
import crowsoft.reservation.entities.dtos.appointment.AppointmentGetByIdResponse;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByDoctorIdResponse;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByUserIdResponse;

import org.springframework.validation.FieldError;
@RestController
@RequestMapping("api/reservation")
public class AppointmentController {
    
    private AppointmentService _reservationService;
    private JwtService _jwtService;
    private UserDao _userDao;
    
    
    public AppointmentController(AppointmentService _reservationService, JwtService _jwtService, UserDao _userDao) {
        this._reservationService = _reservationService;
        this._jwtService = _jwtService;
        this._userDao = _userDao;
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

     @GetMapping("/get/{id}")
    public ResponseEntity<DataResult<AppointmentGetByIdResponse>> getById(@PathVariable int id) {
            DataResult<AppointmentGetByIdResponse> result = _reservationService.getById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get/{id}/appointment")
    public ResponseEntity<DataResult<List<GetAppointmentByDoctorIdResponse>>> getAppointmentByDoctorId(@PathVariable int id) {
            DataResult<List<GetAppointmentByDoctorIdResponse>> result = _reservationService.getAppointmentsByDoctorr(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }

      @GetMapping("/get/{id}/user")
    public ResponseEntity<DataResult<List<GetAppointmentByUserIdResponse>>> getAppointmentByUserId(@PathVariable int id) {
            DataResult<List<GetAppointmentByUserIdResponse>> result = _reservationService.getAppointmentsByUserId(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Appointment reservation, @RequestHeader("Authorization") String token){
        String originalToken = token.substring(7);
         String userEmail = this._jwtService.extractUsername(originalToken);
        return ResponseEntity.ok(this._reservationService.add(reservation,userEmail));
    }

    
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Appointment appointment){
        return ResponseEntity.ok(this._reservationService.update(appointment));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return ResponseEntity.ok(this._reservationService.delete(id));
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
