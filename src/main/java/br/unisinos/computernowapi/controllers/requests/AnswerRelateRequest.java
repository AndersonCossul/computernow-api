package br.unisinos.computernowapi.controllers.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerRelateRequest {
    @NotNull
    private Long computerId;
}
