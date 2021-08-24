package br.unisinos.computernowapi.controllers.responses;

import br.unisinos.computernowapi.entities.AnswerEntity;
import lombok.Data;

import java.util.List;

@Data
public class QuestionResponse {
    private Long id;
    private String title;
    private List<AnswerEntity> answers;
}
