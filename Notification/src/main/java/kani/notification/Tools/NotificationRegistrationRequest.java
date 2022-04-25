package kani.notification.Tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRegistrationRequest {
    private Long toCustomerId;
    private String message;
    private String type;
}
