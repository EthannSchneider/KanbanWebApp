package ch.ethann.tpi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import ch.ethann.tpi.excpetion.Kanban.KanbanAlreadyExistException;
import ch.ethann.tpi.excpetion.Kanban.KanbanException;
import ch.ethann.tpi.excpetion.Kanban.KanbanNotFoundException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.repository.KanbanRepository;

@EnableAutoConfiguration
@SpringBootTest(properties = "spring.profiles.active=test")
public class KanbanServiceTest {
    @Autowired
    private KanbanService kanbanService;

    @Autowired
    private KanbanRepository kanbanRepository;

    private static String KANBAN_NAME = "Test Kanban";
    private static String KANBAN_DESCRIPTION = "Test Description";
    private static String KANBAN_BOX_COLORS = "777777";

    @BeforeEach
    void setUp() {
        Kanban kanban = new Kanban();
        kanban.setName(KANBAN_NAME);
        kanban.setDescription(KANBAN_DESCRIPTION);
        kanban.setBoxColors(KANBAN_BOX_COLORS);
        
        kanbanRepository.save(kanban);
    }

    @AfterEach
    public void tearDown() {
        kanbanRepository.deleteAll();
    }

    @Test
    void testCreateKanban() throws KanbanException {
        String name2 = "Test Kanban2";
        String description2 = "Test Description2";

        Kanban kanban = new Kanban();
        kanban.setName(name2);
        kanban.setDescription(description2);
        kanban.setBoxColors(KANBAN_BOX_COLORS);
        kanbanService.createKanban(kanban);

        assertEquals(2, kanbanRepository.count());
        assertEquals(description2, kanbanRepository.findByName("Test Kanban2").get().getDescription());
    }

    @Test
    void testGetKanban() throws KanbanException {
        Kanban kanban = kanbanService.getKanban(KANBAN_NAME);

        assertEquals("Test Kanban", kanban.getName());
        assertEquals(KANBAN_DESCRIPTION, kanban.getDescription());
        assertEquals(KANBAN_BOX_COLORS, kanban.getBoxColors());
    }
    
    @Test
    void testGetKanbans() {
        assertEquals(1, kanbanService.getKanbans().size());
        assertEquals(KANBAN_DESCRIPTION, kanbanService.getKanbans().get(0).getDescription());
        assertEquals(KANBAN_BOX_COLORS, kanbanService.getKanbans().get(0).getBoxColors());
    }

    @Test
    void testUpdateKanban() throws KanbanException {
        String description2 = "Test Description2";
        String boxColors2 = "888888";

        Kanban kanban = kanbanRepository.findByName(KANBAN_NAME).get();
        kanban.setDescription(description2);
        kanban.setBoxColors(boxColors2);
        kanbanService.updateKanban(kanban);

        assertEquals(description2, kanbanRepository.findByName(KANBAN_NAME).get().getDescription());
        assertEquals(boxColors2, kanbanRepository.findByName(KANBAN_NAME).get().getBoxColors());
    }

    @Test
    void testDeleteKanban() throws KanbanException {
        kanbanService.deleteKanban(KANBAN_NAME);
        assertEquals(0, kanbanRepository.count());
    }

    @Test
    void testDeleteKanbanException() {
        assertThrows(KanbanNotFoundException.class, () -> {
            kanbanService.deleteKanban("Nonexistent Kanban");
        });
    }

    @Test
    void testGetKanbanException() {
        assertThrows(KanbanNotFoundException.class, () -> {
            kanbanService.getKanban("Nonexistent Kanban");
        });
    }

    @Test
    void testUpdateKanbanException() {
        assertThrows(KanbanNotFoundException.class, () -> {
            Kanban kanban = new Kanban();
            kanban.setName("Nonexistent Kanban");
            kanbanService.updateKanban(kanban);
        });
    }

    @Test
    void testCreateKanbanException() {
        assertThrows(KanbanAlreadyExistException.class, () -> {
            Kanban kanban = new Kanban();
            kanban.setName(KANBAN_NAME);
            kanban.setDescription(KANBAN_DESCRIPTION);
            kanban.setBoxColors(KANBAN_BOX_COLORS);
            kanbanService.createKanban(kanban);
        });
    }
}
