package ch.ethann.tpi.controller;

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

import ch.ethann.tpi.dto.CreateKanbanDTO;
import ch.ethann.tpi.dto.UpdateKanbanDTO;
import ch.ethann.tpi.excpetion.Kanban.KanbanException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.projection.KanbanProjection;
import ch.ethann.tpi.service.KanbanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/kanban")
@Tags({ @Tag(name = "Kanban", description = "Kanban API") })
public class KanbanController {
    @Autowired 
    private KanbanService kanbanService;

    @Autowired 
    private ProjectionFactory projectionFactory;
    
    @GetMapping
    public List<KanbanProjection> getKanban() {
        return kanbanListToKanbanProjectionList(kanbanService.getKanbans());
    }

    @PostMapping
    public ResponseEntity<KanbanProjection> createKanban(@RequestBody @Valid CreateKanbanDTO createKanbanDTO) throws KanbanException {
        Kanban kanban = new Kanban();
        kanban.setName(createKanbanDTO.getName());
        kanban.setDescription(createKanbanDTO.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(projectionFactory.createProjection(KanbanProjection.class, kanbanService.createKanban(kanban)));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteKanban(@PathVariable String name) throws KanbanException {
        kanbanService.deleteKanban(name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<KanbanProjection> updateKanban(@PathVariable String name, @RequestBody @Valid UpdateKanbanDTO updateKanbanDTO) throws KanbanException {
        Kanban kanban = kanbanService.getKanban(name);
        
        if (updateKanbanDTO.getName() != "") {
            kanban = kanbanService.renameKanban(name, updateKanbanDTO.getName());
        }

        if (updateKanbanDTO.getDescription() != "") {
            kanban.setDescription(updateKanbanDTO.getDescription()); 
        }

        return ResponseEntity.ok(projectionFactory.createProjection(KanbanProjection.class, kanbanService.updateKanban(kanban)));
    }

    private List<KanbanProjection> kanbanListToKanbanProjectionList(List<Kanban> kanbans) {
        return kanbans.stream()
            .map(kanban -> projectionFactory.createProjection(KanbanProjection.class, kanban))
            .collect(Collectors.toList());
    }
}