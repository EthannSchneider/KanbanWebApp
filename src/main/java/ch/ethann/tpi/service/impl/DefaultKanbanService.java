package ch.ethann.tpi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ch.ethann.tpi.excpetion.Kanban.KanbanAlreadyExistException;
import ch.ethann.tpi.excpetion.Kanban.KanbanException;
import ch.ethann.tpi.excpetion.Kanban.KanbanNotFoundException;
import ch.ethann.tpi.excpetion.Task.TaskException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.repository.KanbanRepository;
import ch.ethann.tpi.service.KanbanService;
import ch.ethann.tpi.service.TaskService;

public class DefaultKanbanService implements KanbanService {

    @Autowired
    private KanbanRepository kanbanRepository;

    @Autowired 
    private TaskService taskService;

    @Override
    public List<Kanban> getKanbans() {
        return (List<Kanban>) kanbanRepository.findAll();
    }

    @Override
    public Kanban getKanban(String name) throws KanbanException {
        throwExceptionIfKanbanExists(name, null, new KanbanNotFoundException());
        return kanbanRepository.findByName(name).orElse(null);
    }

    @Override
    public Kanban createKanban(Kanban kanban) throws KanbanException {
        throwExceptionIfKanbanExists(kanban.getName(), new KanbanAlreadyExistException(), null);
        return kanbanRepository.save(kanban);
    }

    @Override
    public Kanban updateKanban(Kanban kanban) throws KanbanException{
        throwExceptionIfKanbanExists(kanban.getName(), null, new KanbanNotFoundException());
        return kanbanRepository.save(kanban);
    }

    @Override
    public Kanban renameKanban(String name, String newName) throws KanbanException {
        throwExceptionIfKanbanExists(name, null, new KanbanNotFoundException());
        if (!name.equals(newName)) {
            throwExceptionIfKanbanExists(newName, new KanbanAlreadyExistException(), null);
        }
        Kanban kanban = kanbanRepository.findByName(name).get();
        kanban.setName(newName);
        return kanbanRepository.save(kanban);
    }

    @Override
    public void deleteKanban(String name) throws KanbanException {
        Kanban kanban = getKanban(name);
        taskService.getTasks(kanban).forEach(task -> {
            try {
                taskService.deleteTask(kanban, task.getName());
            } catch (TaskException e) {
                e.printStackTrace();
            }
        });
        kanbanRepository.delete(kanban);
    }

    private void throwExceptionIfKanbanExists(String name, KanbanException exist, KanbanException notExist) throws KanbanException {
        if (kanbanRepository.findByName(name).isPresent()) {
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
