package br.unisinos.computernowapi.controllers.requests;

import br.unisinos.computernowapi.controllers.responses.ComputerResponse;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AnswerRequest {
    @NotEmpty
    private String title;
    private List<ComputerResponse> computers;
}
