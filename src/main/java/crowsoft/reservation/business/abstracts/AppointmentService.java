package crowsoft.reservation.business.abstracts;

import java.util.List;
import crowsoft.reservation.core.utilities.results.DataResult;
import crowsoft.reservation.core.utilities.results.Result;
import crowsoft.reservation.entities.concretes.Appointment;
import crowsoft.reservation.entities.dtos.appointment.AppointmentDTO;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByDoctorIdResponse;
import crowsoft.reservation.entities.dtos.appointment.GetAppointmentByUserIdResponse;
import crowsoft.reservation.entities.dtos.appointment.AppointmentGetByIdResponse;

public interface AppointmentService {
    DataResult<AppointmentGetByIdResponse> getById(int id);
    Result add(Appointment appointment);
    Result update(Appointment appointment);
    Result delete(int id);
    DataResult<List<AppointmentDTO>> getAllAppointmentsWithDetails();
    DataResult<List<GetAppointmentByDoctorIdResponse>> getAppointmentsByDoctorr(int id);
    DataResult<List<GetAppointmentByUserIdResponse>> getAppointmentsByUserId(int id);



}
