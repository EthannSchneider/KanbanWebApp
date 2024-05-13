package ch.ethann.tpi.service;

import java.util.List;

import ch.ethann.tpi.excpetion.Task.TaskException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.model.Task;

public interface TaskService {
    public List<Task> getTasks(Kanban kanban); 

    public Task getTask(Kanban kanban, String name) throws TaskException;

    public Task createTask(Task task) throws TaskException;

    public void deleteTask(Kanban kanban, String name) throws TaskException;

    public Task updateTask(Task task) throws TaskException;

    public Task renameTask(Kanban kanban, String oldName, String newName) throws TaskException;
}