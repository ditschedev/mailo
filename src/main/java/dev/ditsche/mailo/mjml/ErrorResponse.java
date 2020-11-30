package dev.ditsche.mailo.mjml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ErrorResponse {

    private String message;

}
