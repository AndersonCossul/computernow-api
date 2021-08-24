package br.unisinos.computernowapi.controllers.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuestionRelateRequest {
    @NotNull
    private Long answerId;
}
