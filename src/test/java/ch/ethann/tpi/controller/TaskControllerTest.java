package ch.ethann.tpi.controller;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.model.Task;
import ch.ethann.tpi.model.Task.Status;
import ch.ethann.tpi.repository.KanbanRepository;
import ch.ethann.tpi.repository.TaskRepository;

@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=test")
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KanbanRepository kanbanRepository;

    @Autowired
    private TaskRepository taskRepository;

    private String name = "Kanban1";
    private String description = "Kanban1";
    private String boxColors = "777777";

    @BeforeEach
    public void setUp() {
        Kanban kanban = new Kanban();
        kanban.setName(name);
        kanban.setDescription(description);
        kanban.setBoxColors(boxColors);
        kanbanRepository.save(kanban);

        Task task = new Task();
        task.setName("Task1");
        task.setAssignee("Assignee1");
        task.setDescription("Description1");
        task.setTimeToRelease(Time.valueOf("00:00:00"));
        task.setStatus(Status.TODO);
        task.setKanban(kanban);
        taskRepository.save(task);
    }

    @AfterEach
    public void tearDown() {
        taskRepository.deleteAll();
        kanbanRepository.deleteAll();
    }

    @Test
    void testGetTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/kanban/{name}/task", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Task1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].assignee").value("Assignee1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timeToRelease").value("00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("TODO"));
    }

    @Test
    void testGetTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/kanban/{name}/task/{taskName}", name, "Task1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Task1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignee").value("Assignee1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToRelease").value("00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("TODO"));
    }

    @Test
    void testCreateTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/kanban/{name}/task", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Task2\", \"assignee\": \"Assignee2\", \"description\": \"Description2\", \"timeToRelease\": \"00:00:00\", \"status\": \"TODO\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Task2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignee").value("Assignee2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToRelease").value("00:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("TODO"));
    }

    @Test
    void testUpdateTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}/task/{taskName}", name, "Task1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Task2\", \"assignee\": \"Assignee2\", \"description\": \"Description2\", \"timeToRelease\": \"10:00:00\", \"status\": \"IN_PROGRESS\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Task2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignee").value("Assignee2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToRelease").value("10:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/kanban/{name}/task/{taskName}", name, "Task1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testMoveTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}/task/{taskName}", name, "Task1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"DONE\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("DONE"));
    }

    @Test 
    void testCreateTaskAlreadyExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/kanban/{name}/task", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Task1\", \"assignee\": \"Assignee2\", \"description\": \"Description2\", \"timeToRelease\": \"00:00:00\", \"status\": \"TODO\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test 
    void testGetTaskNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/kanban/{name}/task/{taskName}", name, "Task2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateTaskNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}/task/{taskName}", name, "Task2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Task2\", \"assignee\": \"Assignee2\", \"description\": \"Description2\", \"timeToRelease\": \"10:00:00\", \"status\": \"IN_PROGRESS\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteTaskNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/kanban/{name}/task/{taskName}", name, "Task2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }    
}
