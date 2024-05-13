package ch.ethann.tpi.excpetion.Task;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Task Not Found")
public class TaskNotFoundException extends TaskException {
    
}
