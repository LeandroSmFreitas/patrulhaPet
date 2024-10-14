package br.com.manager.patrulhapet.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestFieldErrors {

    private String objectName;

    private String field;

    private String message;

}
