package br.unisinos.computernowapi.controllers.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnswerRequest {
    @NotEmpty
    private String title;
}
