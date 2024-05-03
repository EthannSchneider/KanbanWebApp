package ch.ethann.tpi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateKanbanDTO {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    private String description = "";
}