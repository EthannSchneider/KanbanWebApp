package ch.ethann.tpi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ch.ethann.tpi.model.Kanban;

public interface KanbanRepository extends CrudRepository<Kanban, Long> {
    Optional<Kanban> findByName(String name);
}
