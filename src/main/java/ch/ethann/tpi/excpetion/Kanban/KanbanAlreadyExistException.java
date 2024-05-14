package ch.ethann.tpi.excpetion.Kanban;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Kanban Already Exist")
public class KanbanAlreadyExistException extends KanbanException {
    
}
