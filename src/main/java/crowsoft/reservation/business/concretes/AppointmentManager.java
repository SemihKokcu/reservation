package crowsoft.reservation.business.concretes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowsoft.reservation.business.abstracts.AppointmentService;
import crowsoft.reservation.core.entities.User;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.core.utilities.results.ErrorResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.core.utilities.results.SuccessDataResult;
import crowsoft.reservation.core.utilities.results.SuccessResult;
import crowsoft.reservation.dataAccess.abstracts.AppointmentDao;
import crowsoft.reservation.entities.concretes.Appointment;
import crowsoft.reservation.entities.concretes.Doctor;
import crowsoft.reservation.entities.dtos.appointment.AppointmentDTO;
import crowsoft.reservation.entities.dtos.appointment.AppointmentGetByIdResponse;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByDoctorIdResponse;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByUserIdResponse;

@Service
public class AppointmentManager implements AppointmentService {

    private AppointmentDao _reservationDao;

   
    @Autowired
    public AppointmentManager(AppointmentDao _reservationDao) {
        this._reservationDao = _reservationDao;
    }

    public DataResult<List<AppointmentDTO>> getAllAppointmentsWithDetails() {
    List<Appointment> appointments = this._reservationDao.findAll();
    List<AppointmentDTO> appointmentDTOs = new ArrayList<>();

    for (Appointment appointment : appointments) {
      
        AppointmentDTO appointmentDTO = new AppointmentDTO(
            appointment.getId(),
            appointment.getDoctor().getFirstName(),
            appointment.getPatient().getName(),
            appointment.getStartTime(),
            appointment.getEndTime(),
            appointment.isConfirmed()
        );

        appointmentDTOs.add(appointmentDTO);
    }

    return new SuccessDataResult<List<AppointmentDTO>>(appointmentDTOs, "Data Listed");
}
@Override
public Result add(Appointment reservation) {

    List<Appointment> overlappingAppointments = checkStartAndEndTime(reservation.getStartTime(), reservation.getEndTime());
    if (!overlappingAppointments.isEmpty()) {
        return new ErrorResult("There is an overlapping appointment.");
    }
    this._reservationDao.save(reservation);
    return new SuccessResult("Reservation added");
}
    @Override
    public DataResult<AppointmentGetByIdResponse> getById(int id) {
       
        Appointment appointment = this._reservationDao.findById(id).orElse(null);
        AppointmentGetByIdResponse response = new AppointmentGetByIdResponse();
        var doctor = Doctor.builder()
        .id(appointment.getDoctor().getId())
        .firstName(appointment.getDoctor().getFirstName())
        .lastName(appointment.getDoctor().getLastName())
        .build();
        var user = User.builder()
            .id(appointment.getPatient().getId())
            .name(appointment.getPatient().getName())
            .role(appointment.getPatient().getRole())
            .build();
        response.setId(appointment.getId());
        response.setDoctorName(doctor);
        response.setPatientName(user);
        response.setConfirmed(appointment.isConfirmed());
        response.setStartTime(appointment.getStartTime());
        response.setEndTime(appointment.getEndTime());     
        
        return new SuccessDataResult<AppointmentGetByIdResponse>(response, "Appointment found");
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

    @Override
    public DataResult<List<GetAppointmentByDoctorIdResponse>> getAppointmentsByDoctorr(int id) {
        try {
            List<Appointment> results = this._reservationDao.findByDoctorId(id);
            List<GetAppointmentByDoctorIdResponse> res = new ArrayList<>();

            for(Appointment apoint: results) {

                var byDoctorId = GetAppointmentByDoctorIdResponse.builder()
                .doctorName(apoint.getDoctor().getFirstName())
                .confirmed(apoint.isConfirmed())
                .endTime(apoint.getEndTime())
                .startTime(apoint.getStartTime())
                .id(apoint.getId())
                .patientName(apoint.getPatient().getName())
                .build();

                res.add(byDoctorId);
            }

            return new SuccessDataResult<List<GetAppointmentByDoctorIdResponse>>(res, "AppointmentsByDoctor Listed");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while getting appointments: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<GetAppointmentByUserIdResponse>> getAppointmentsByUserId(int id) {
        List<Appointment> appointmentsByUserId = this._reservationDao.findByPatientId(id);
        List<GetAppointmentByUserIdResponse> result = new ArrayList<>();

        for (Appointment appointment : appointmentsByUserId) {
           var byUserId = GetAppointmentByUserIdResponse.builder()
           .id(appointment.getId())
           .confirmed(appointment.isConfirmed())
           .doctorId(appointment.getDoctor().getId())
           .doctorName(appointment.getDoctor().getFirstName())
           .startTime(appointment.getStartTime())
           .endTime(appointment.getEndTime())
           .patientName(appointment.getPatient().getName())
           .build();

           result.add(byUserId);
        }

         return new SuccessDataResult<List<GetAppointmentByUserIdResponse>>(result, "AppointmentsByUserId Listed");

    }

    private List<Appointment> checkStartAndEndTime(LocalDateTime startDateTime,LocalDateTime endDateTime){
        
        List<Appointment> overlappingAppointments = _reservationDao.findByStartTimeBetweenAndEndTimeBetween(
            startDateTime, endDateTime,
            startDateTime, endDateTime);

            return overlappingAppointments;
    }
}
