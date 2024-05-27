package ch.ethann.tpi.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateTaskDTO {
    @Length(max = 255, message = "Name should not be longer than 255 characters")
    private String name = "";

    @Length(max = 255, message = "Assignee should not be longer than 255 characters")
    private String assignee = "";

    @Length(max = 1024, message = "Description should not be longer than 1024 characters")
    private String description = "";

    @DateTimeFormat(pattern = "hh:mm:ss")
    private String timeToRelease = "";

    @Pattern(regexp = "^BACKLOG|TODO|IN_PROGRESS|TO_TEST|DONE|$", message = "Statues should be BACKLOG, TODO, IN_PROGRESS, TO_TEST, DONE")
    private String status = "";
}