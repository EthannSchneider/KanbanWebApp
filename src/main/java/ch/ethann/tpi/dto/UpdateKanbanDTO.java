package ch.ethann.tpi.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateKanbanDTO {
    private String name = "";

    private String description = "";

    @Pattern(regexp = "^([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|$")
    private String boxColors = "";
}
