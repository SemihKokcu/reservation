package crowsoft.reservation.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crowsoft.reservation.business.abstracts.AppointmentService;
import crowsoft.reservation.core.entities.User;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.ErrorDataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.core.utilities.results.SuccessDataResult;
import crowsoft.reservation.core.utilities.results.SuccessResult;
import crowsoft.reservation.dataAccess.abstracts.AppointmentDao;
import crowsoft.reservation.entities.concretes.Appointment;
import crowsoft.reservation.entities.dtos.appointment.AppointmentDTO;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByDoctorIdResponse;

@Service
public class AppointmentManager implements AppointmentService {

    private AppointmentDao _reservationDao;

   
    @Autowired
    public AppointmentManager(AppointmentDao _reservationDao) {
        this._reservationDao = _reservationDao;
    }

    @Override
    public DataResult<List<Appointment>> getAll() {
        return new SuccessDataResult<List<Appointment>>(this._reservationDao.findAll(), "Data Listed");
    }

    public DataResult<List<AppointmentDTO>> getAllAppointmentsWithDetails() {
    List<Appointment> appointments = this._reservationDao.findAll();
    List<AppointmentDTO> appointmentDTOs = new ArrayList<>();

    for (Appointment appointment : appointments) {
      
        AppointmentDTO appointmentDTO = new AppointmentDTO(
            appointment.getId(),
            appointment.getDoctor().getFirstName(),
            appointment.getPatient().getFirstname(),
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

    @Override
    public DataResult<List<Appointment>> getAppointmentsByDoctor(int id) {
        try {
            List<Appointment> result = this._reservationDao.findByDoctorId(id);
            return new SuccessDataResult<List<Appointment>>(result, "AppointmentsByDoctor Listed");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while getting appointments: " + e.getMessage());
        }
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
                .patientName(apoint.getPatient().getFirstname())
                .build();

                res.add(byDoctorId);
            }

            return new SuccessDataResult<List<GetAppointmentByDoctorIdResponse>>(res, "AppointmentsByDoctor Listed");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while getting appointments: " + e.getMessage());
        }
    }
}
