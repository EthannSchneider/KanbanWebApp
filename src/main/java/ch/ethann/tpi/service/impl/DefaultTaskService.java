package ch.ethann.tpi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ch.ethann.tpi.excpetion.Task.TaskAlreadyExistException;
import ch.ethann.tpi.excpetion.Task.TaskException;
import ch.ethann.tpi.excpetion.Task.TaskNotFoundException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.model.Task;
import ch.ethann.tpi.repository.TaskRepository;
import ch.ethann.tpi.service.TaskService;

public class DefaultTaskService implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getTasks(Kanban kanban) {
        return taskRepository.findByKanban(kanban);
    }

    @Override
    public Task getTask(Kanban kanban, String name) throws TaskException {
        return taskRepository.findByKanbanAndName(kanban, name).orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Task createTask(Task task) throws TaskException {
        throwExceptionIfTaskExists(task.getKanban(), task.getName(), new TaskAlreadyExistException(), null);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Kanban kanban, String name) throws TaskException {
        Task task = getTask(kanban, name);
        taskRepository.delete(task);
    }

    @Override
    public Task updateTask(Task task) throws TaskException {
        throwExceptionIfTaskExists(task.getKanban(), task.getName(), null, new TaskNotFoundException());
        return taskRepository.save(task);
    }

    @Override
    public Task renameTask(Kanban kanban, String oldName, String newName) throws TaskException {
        throwExceptionIfTaskExists(kanban, oldName, null, new TaskNotFoundException());
        if (!oldName.equals(newName)) {
            throwExceptionIfTaskExists(kanban, newName, new TaskAlreadyExistException(), null);
        }
        Task task = taskRepository.findByKanbanAndName(kanban, oldName).get();
        task.setName(newName);
        return taskRepository.save(task);
    }

    private void throwExceptionIfTaskExists(Kanban kanban, String name, TaskException exist, TaskException notExist) throws TaskException {
        if (taskRepository.findByKanbanAndName(kanban, name).isPresent()) {
            if(exist != null) {
                throw exist;
            }
        }else {
            if(notExist != null) {
                throw notExist;
            }
        }
    }
}
