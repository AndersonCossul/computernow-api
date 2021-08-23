package br.unisinos.computernowapi.exceptions;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super("Answer not found for id " + id);
    }
}
