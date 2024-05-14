package ch.ethann.tpi.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateKanbanDTO {
    @NotEmpty(message = "Name should not be empty")
    @Length(max = 255, message = "Name should not be longer than 255 characters", min = 1)
    private String name;

    @Length(max = 1024, message = "Description should not be longer than 1024 characters")
    private String description = "";

    @Pattern(regexp = "^([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|$")
    private String boxColors = "ADD8E6";
}