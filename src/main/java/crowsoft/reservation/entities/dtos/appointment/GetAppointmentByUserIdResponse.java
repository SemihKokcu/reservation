package crowsoft.reservation.entities.dtos.appointment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAppointmentByUserIdResponse {
    
    private int id;
    private int doctorId;
    private String doctorName;
    private String patientName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean confirmed;
}
