package ch.ethann.tpi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Optional<Task> findByKanbanAndName(Kanban kanban, String name);

    List<Task> findByKanban(Kanban kanban);
}