package ch.ethann.tpi.service;

import java.util.List;

import ch.ethann.tpi.excpetion.Kanban.KanbanException;
import ch.ethann.tpi.model.Kanban;

public interface KanbanService {
    public List<Kanban> getKanbans();

    public Kanban getKanban(String name) throws KanbanException;

    public Kanban createKanban(Kanban kanban) throws KanbanException;

    public Kanban updateKanban(Kanban kanban) throws KanbanException;

    public Kanban renameKanban(String name, String newName) throws KanbanException;

    public void deleteKanban(String name) throws KanbanException;
}