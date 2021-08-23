package br.unisinos.computernowapi.controllers.requests;

import br.unisinos.computernowapi.enums.SOEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

@Data
public class ComputerRequest {
    @NotEmpty
    private String so;
    @NotEmpty
    private String name;
    private String description;
    @Min(0)
    private Double price;

    public SOEnum getSo() {
        try {
            return SOEnum.valueOf(so.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SO is invalid. Use one of: " + Arrays.toString(SOEnum.values()));
        }
    }
}
