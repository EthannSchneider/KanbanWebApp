package ch.ethann.tpi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ch.ethann.tpi.excpetion.Kanban.KanbanAlreadyExistException;
import ch.ethann.tpi.excpetion.Kanban.KanbanException;
import ch.ethann.tpi.excpetion.Kanban.KanbanNotFoundException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.repository.KanbanRepository;
import ch.ethann.tpi.service.KanbanService;

public class DefaultKanbanService implements KanbanService {

    @Autowired
    private KanbanRepository kanbanRepository;

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
        throwExceptionIfKanbanExists(newName, new KanbanAlreadyExistException(), null);
        Kanban kanban = kanbanRepository.findByName(name).get();
        kanban.setName(newName);
        return kanbanRepository.save(kanban);
    }

    @Override
    public void deleteKanban(String name) throws KanbanException {
        throwExceptionIfKanbanExists(name, null, new KanbanNotFoundException());
        kanbanRepository.delete(kanbanRepository.findByName(name).get());
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
