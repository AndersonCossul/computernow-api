package br.unisinos.computernowapi.controllers.requests;

import br.unisinos.computernowapi.enums.OSEnum;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Data
public class ComputerRequest {
    @NotEmpty
    private String os;
    @NotEmpty
    private String title;
    private String description;
    @Min(0)
    @NotNull
    private Double price;

    public OSEnum getOs() {
        try {
            return OSEnum.valueOf(os.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OS is invalid. Use one of: " + Arrays.toString(OSEnum.values()));
        }
    }
}
