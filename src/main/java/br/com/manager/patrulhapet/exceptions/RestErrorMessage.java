package br.com.manager.patrulhapet.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {
    private HttpStatus status;
    private String message;
    private List<RestFieldErrors> fieldErrors;

    public RestErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
