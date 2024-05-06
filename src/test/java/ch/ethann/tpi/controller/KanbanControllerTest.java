package ch.ethann.tpi.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.repository.KanbanRepository;

@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=test")
public class KanbanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KanbanRepository kanbanRepository;

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
    }

    @AfterEach
    public void tearDown() {
        kanbanRepository.deleteAll();
    }

    @Test
    void testGetKanbans() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/kanban")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(description))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].boxColors").value(boxColors));
    }

    @Test
    void testGetKanban() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/kanban/{name}", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boxColors").value(boxColors));
    }

    @Test
    void testCreateKanban() throws Exception {
        String name = "Kanban2";
        String description = "Kanban2";
        String boxColors = "777777";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/kanban")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + name + "\", \"description\": \"" + description + "\", \"boxColors\": \"" + boxColors + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boxColors").value(boxColors));
    }

    @Test
    void testDeleteKanban() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/kanban/{name}", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testUpdateKanban() throws Exception {
        String name2 = "Kanban2";
        String description2 = "Kanban2";
        String boxColors2 = "777777";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + name2 + "\", \"description\": \"" + description2 + "\", \"boxColors\": \"" + boxColors2 + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boxColors").value(boxColors2));
    }

    @Test 
    public void testUpdateKanbanDescription() throws Exception {
        String description2 = "Kanban2";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\": \"" + description2 + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(description2));
    }

    @Test 
    public void testUpdateKanbanName() throws Exception {
        String name2 = "Kanban2";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + name2 + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name2));
    }

    @Test
    public void testUpdateKanbanBoxColors() throws Exception {
        String boxColors2 = "888888";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}", name)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"boxColors\": \"" + boxColors2 + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.boxColors").value(boxColors2));
    }

    @Test
    void testGetKanbanThatNotExist() throws Exception {
        String name2 = "Kanban2";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/kanban/{name}", name2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteKanbanThatNotExist() throws Exception {
        String name2 = "Kanban2";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/kanban/{name}", name2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateKanbanThatNotExist() throws Exception {
        String name2 = "Kanban2";
        String description2 = "Kanban2";
        String boxColors2 = "777777";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/kanban/{name}", name2)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + name2 + "\", \"description\": \"" + description2 + "\", \"boxColors\": \"" + boxColors2 + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateKanbanWithEmptyName() throws Exception {
        String name2 = "";
        String description2 = "Kanban2";
        String boxColors2 = "777777";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/kanban")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + name2 + "\", \"description\": \"" + description2 + "\", \"boxColors\": \"" + boxColors2 + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testCreateKanbanThatAlreadyExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/kanban")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + name + "\", \"description\": \"" + description + "\", \"boxColors\": \"" + boxColors + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
