package br.com.codethebasics.authmanager.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateResponse<T> {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("body")
    private T body;

    @JsonProperty("message")
    private String message;

}
