package ch.ethann.tpi.dto;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateTaskDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private String assignee;

    @NotEmpty
    private String description = "";

    @NotEmpty
    @DateTimeFormat(pattern = "hh:mm:ss")
    private String timeToRelease;

    @NotEmpty
    @Pattern(regexp = "BACKLOG|TODO|IN_PROGRESS|TO_TEST|DONE", message = "Statues should be BACKLOG, TODO, IN_PROGRESS, TO_TEST, DONE")
    private String status = "BACKLOG";
}