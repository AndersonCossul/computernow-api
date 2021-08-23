package br.unisinos.computernowapi.controllers.responses;

import lombok.Data;

import java.util.List;

@Data
public class AnswerResponse {
    private Long id;
    private String title;
    private List<ComputerResponse> computers;
}
