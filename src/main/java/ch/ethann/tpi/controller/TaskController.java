package ch.ethann.tpi.controller;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.ethann.tpi.dto.CreateTaskDTO;
import ch.ethann.tpi.dto.UpdateTaskDTO;
import ch.ethann.tpi.excpetion.Kanban.KanbanException;
import ch.ethann.tpi.excpetion.Task.TaskException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.model.Task;
import ch.ethann.tpi.model.Task.Status;
import ch.ethann.tpi.projection.TaskProjection;
import ch.ethann.tpi.service.KanbanService;
import ch.ethann.tpi.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/kanban/{kanbanName}/task")
@Tags({ @Tag(name = "Task", description = "Task API") })
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private KanbanService kanbanService;

    @Autowired 
    private ProjectionFactory projectionFactory;

    @GetMapping
    public List<TaskProjection> getTask(@PathVariable String kanbanName) throws KanbanException {
        return taskListToTaskProjectionList(taskService.getTasks(kanbanService.getKanban(kanbanName)));
    }

    @GetMapping("{taskName}")
    public TaskProjection getTask(@PathVariable String kanbanName, @PathVariable String taskName) throws KanbanException, TaskException {
        return projectionFactory.createProjection(TaskProjection.class, taskService.getTask(kanbanService.getKanban(kanbanName), taskName));
    }

    @PostMapping
    public ResponseEntity<TaskProjection> createTask(@PathVariable String kanbanName, @RequestBody @Valid CreateTaskDTO taskDTO) throws KanbanException, TaskException {
        Task task = new Task();

        task.setName(taskDTO.getName());
        task.setAssignee(taskDTO.getAssignee());
        task.setDescription(taskDTO.getDescription());
        task.setTimeToRelease(Time.valueOf(taskDTO.getTimeToRelease()));
        task.setStatus(Status.valueOf(taskDTO.getStatus()));
        task.setKanban(kanbanService.getKanban(kanbanName));

        return ResponseEntity.status(HttpStatus.CREATED).body(projectionFactory.createProjection(TaskProjection.class, taskService.createTask(task)));
    }

    @PatchMapping("{taskName}")
    public TaskProjection updateTask(@PathVariable String kanbanName, @PathVariable String taskName, @RequestBody @Valid UpdateTaskDTO taskDTO) throws KanbanException, TaskException {
        Kanban kanban = kanbanService.getKanban(kanbanName);
        Task task = taskService.getTask(kanban, taskName);

        if (taskDTO.getName() != "") {
            task = taskService.renameTask(kanban, task.getName(), taskDTO.getName());
        }

        if (taskDTO.getAssignee() != "") task.setAssignee(taskDTO.getAssignee());
        if (taskDTO.getDescription() != "") task.setDescription(taskDTO.getDescription());
        if (taskDTO.getTimeToRelease() != "") task.setTimeToRelease(Time.valueOf(taskDTO.getTimeToRelease()));
        if (taskDTO.getStatus() != "") task.setStatus(Status.valueOf(taskDTO.getStatus()));

        return projectionFactory.createProjection(TaskProjection.class, taskService.updateTask(task));
    }

    @DeleteMapping("{taskName}")
    public ResponseEntity<Void> deleteTask(@PathVariable String kanbanName, @PathVariable String taskName) throws KanbanException, TaskException {
        taskService.deleteTask(kanbanService.getKanban(kanbanName), taskName);
        return ResponseEntity.noContent().build();
    }

    private List<TaskProjection> taskListToTaskProjectionList(List<Task> kanbans) {
        return kanbans.stream()
            .map(kanban -> projectionFactory.createProjection(TaskProjection.class, kanban))
            .collect(Collectors.toList());
    }
}
