package ch.ethann.tpi.excpetion.Kanban;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Kanban Not Found")
public class KanbanNotFoundException extends KanbanException {
    
}
