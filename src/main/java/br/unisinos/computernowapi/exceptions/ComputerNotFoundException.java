package br.unisinos.computernowapi.exceptions;

public class ComputerNotFoundException extends RuntimeException {
    public ComputerNotFoundException(Long id) {
        super("Computer not found for id " + id);
    }
}
