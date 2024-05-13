package ch.ethann.tpi.dto;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateTaskDTO {
    private String name = "";

    private String assignee = "";

    private String description = "";

    @DateTimeFormat(pattern = "hh:mm:ss")
    private String timeToRelease = "";

    @Pattern(regexp = "^BACKLOG|TODO|IN_PROGRESS|TO_TEST|DONE|$", message = "Statues should be BACKLOG, TODO, IN_PROGRESS, TO_TEST, DONE")
    private String status = "";
}