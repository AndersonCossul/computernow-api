package br.unisinos.computernowapi.controllers.responses;

import br.unisinos.computernowapi.enums.SOEnum;
import lombok.Data;


@Data
public class ComputerResponse {
    private Long id;
    private SOEnum so;
    private String name;
    private String description;
    private Double price;
}
