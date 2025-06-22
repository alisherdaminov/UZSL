package UZSL.dto.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse<T> {
    private T data;
    private String message;
    private Date timestamp;

    public AppResponse(String message) {
        this.message = message;
    }

    public AppResponse(T data, String message, Date timestamp) {
        this.data = data;
        this.message = message;
        this.timestamp = timestamp;
    }
}
