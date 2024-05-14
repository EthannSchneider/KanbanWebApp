package ch.ethann.tpi.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateTaskDTO {
    @NotEmpty
    @Length(max = 255, message = "Name should not be longer than 255 characters", min = 1)
    private String name;

    @NotEmpty
    @Length(max = 255, message = "Assignee should not be longer than 255 characters", min = 1)
    private String assignee;

    @NotEmpty
    @Length(max = 1024, message = "Description should not be longer than 1024 characters")
    private String description = "";

    @NotEmpty
    @DateTimeFormat(pattern = "hh:mm:ss")
    private String timeToRelease;

    @NotEmpty
    @Pattern(regexp = "BACKLOG|TODO|IN_PROGRESS|TO_TEST|DONE", message = "Statues should be BACKLOG, TODO, IN_PROGRESS, TO_TEST, DONE")
    private String status = "BACKLOG";
}