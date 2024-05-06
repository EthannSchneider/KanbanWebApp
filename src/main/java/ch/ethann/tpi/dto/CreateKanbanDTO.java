package ch.ethann.tpi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateKanbanDTO {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    private String description = "";

    @Pattern(regexp = "^([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|$")
    private String boxColors = "ADD8E6";
}