package br.unisinos.computernowapi.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("Question not found for id " + id);
    }
}
