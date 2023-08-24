package crowsoft.reservation.entities.dtos.appointment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private int id;
    private String doctorName;
    private String patientName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean confirmed;

   
}