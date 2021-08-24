package br.unisinos.computernowapi.controllers.responses;

import br.unisinos.computernowapi.enums.OSEnum;
import lombok.Data;


@Data
public class ComputerResponse {
    private Long id;
    private OSEnum os;
    private String title;
    private String description;
    private Double price;
}
