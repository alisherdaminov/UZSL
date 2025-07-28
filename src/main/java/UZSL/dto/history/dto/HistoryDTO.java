package UZSL.dto.history.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryDTO {

    private String historyId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
