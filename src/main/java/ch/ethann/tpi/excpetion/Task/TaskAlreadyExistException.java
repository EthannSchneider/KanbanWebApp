package ch.ethann.tpi.excpetion.Task;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Task Already Exist")
public class TaskAlreadyExistException extends TaskException {
    
}
