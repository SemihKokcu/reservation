package crowsoft.reservation.entities.dtos.appointment;

import java.time.LocalDateTime;

import crowsoft.reservation.core.entities.User;
import crowsoft.reservation.entities.concretes.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentGetByIdResponse {
    
     private int id;
    private Doctor doctorName;
    private User patientName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean confirmed;
}
