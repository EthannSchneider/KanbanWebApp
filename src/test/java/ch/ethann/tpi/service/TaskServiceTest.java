package ch.ethann.tpi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import ch.ethann.tpi.excpetion.Task.TaskAlreadyExistException;
import ch.ethann.tpi.excpetion.Task.TaskNotFoundException;
import ch.ethann.tpi.model.Kanban;
import ch.ethann.tpi.model.Task;
import ch.ethann.tpi.model.Task.Status;
import ch.ethann.tpi.repository.KanbanRepository;
import ch.ethann.tpi.repository.TaskRepository;

@EnableAutoConfiguration
@SpringBootTest(properties = "spring.profiles.active=test")
public class TaskServiceTest {
    @Autowired
    private KanbanService kanbanService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private KanbanRepository kanbanRepository;

    private static String KANBAN_NAME = "Test Kanban";
    private static String KANBAN_DESCRIPTION = "Test Description";
    private static String KANBAN_BOX_COLORS = "777777";

    private static String TASK_NAME = "Task1";
    private static String TASK_ASSIGNEE = "Assignee1";
    private static String TASK_DESCRIPTION = "Description1";
    private static Time TASK_TIME_TO_RELEASE = Time.valueOf("00:00:00");
    private static Status TASK_STATUS = Status.TODO;

    @BeforeEach
    void setUp() {
        Kanban kanban = new Kanban();
        kanban.setName(KANBAN_NAME);
        kanban.setDescription(KANBAN_DESCRIPTION);
        kanban.setBoxColors(KANBAN_BOX_COLORS);
        
        kanbanRepository.save(kanban);

        Task task = new Task();
        task.setName(TASK_NAME);
        task.setAssignee(TASK_ASSIGNEE);
        task.setDescription(TASK_DESCRIPTION);
        task.setTimeToRelease(TASK_TIME_TO_RELEASE);
        task.setStatus(TASK_STATUS);
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
        Task task = taskService.getTasks(getKanban()).get(0);

        assert(task.getName().equals(TASK_NAME));
        assert(task.getAssignee().equals(TASK_ASSIGNEE));
        assert(task.getDescription().equals(TASK_DESCRIPTION));
        assert(task.getTimeToRelease().equals(TASK_TIME_TO_RELEASE));
        assert(task.getStatus().equals(TASK_STATUS));
    }

    @Test
    void testGetTask() throws Exception {
        Task task = taskService.getTask(getKanban(), TASK_NAME);

        assert(task.getName().equals(TASK_NAME));
        assert(task.getAssignee().equals(TASK_ASSIGNEE));
        assert(task.getDescription().equals(TASK_DESCRIPTION));
        assert(task.getTimeToRelease().equals(TASK_TIME_TO_RELEASE));
        assert(task.getStatus().equals(TASK_STATUS));
    }

    @Test
    void testCreateTask() throws Exception {
        Task task = new Task();
        task.setName("Task2");
        task.setAssignee("Assignee2");
        task.setDescription("Description2");
        task.setTimeToRelease(Time.valueOf("00:00:00"));
        task.setStatus(Status.TODO);
        task.setKanban(kanbanService.getKanban(KANBAN_NAME));

        taskService.createTask(task);

        Task createdTask = taskService.getTask(getKanban(), "Task2");

        assert(createdTask.getName().equals("Task2"));
        assert(createdTask.getAssignee().equals("Assignee2"));
        assert(createdTask.getDescription().equals("Description2"));
        assert(createdTask.getTimeToRelease().equals(Time.valueOf("00:00:00")));
        assert(createdTask.getStatus().equals(Status.TODO));
    }

    @Test
    void testUpdateTask() throws Exception {
        Task task = taskService.getTask(kanbanService.getKanban(KANBAN_NAME), TASK_NAME);
        task.setAssignee("Assignee2");
        task.setDescription("Description2");
        task.setTimeToRelease(Time.valueOf("00:00:00"));
        task.setStatus(Status.IN_PROGRESS);

        taskService.updateTask(task);

        Task updatedTask = taskRepository.findByKanbanAndName(getKanban(), TASK_NAME).get();

        assert(updatedTask.getName().equals("Task1"));
        assert(updatedTask.getAssignee().equals("Assignee2"));
        assert(updatedTask.getDescription().equals("Description2"));
        assert(updatedTask.getTimeToRelease().equals(Time.valueOf("00:00:00")));
        assert(updatedTask.getStatus().equals(Status.IN_PROGRESS));
    }

    @Test
    void testRenameTask() throws Exception {
        taskService.renameTask(getKanban(), TASK_NAME, "Task2");

        Task renamedTask = taskRepository.findByKanbanAndName(getKanban(), "Task2").get();

        assert(renamedTask.getName().equals("Task2"));
        assert(renamedTask.getAssignee().equals("Assignee1"));
        assert(renamedTask.getDescription().equals("Description1"));
        assert(renamedTask.getTimeToRelease().equals(Time.valueOf("00:00:00")));
        assert(renamedTask.getStatus().equals(Status.TODO));
    }

    @Test
    void testDeleteTask() throws Exception {
        taskService.deleteTask(getKanban(), TASK_NAME);

        assert(taskRepository.findByKanbanAndName(getKanban(), TASK_NAME).isEmpty());
    }

    @Test
    void testGetTaskThatNotExist() throws Exception {
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTask(getKanban(), "Task2");
        });
    }

    @Test
    void testCreateTaskThatAlreadyExist() throws Exception {
        Task task = new Task();
        task.setName(TASK_NAME);
        task.setAssignee(TASK_ASSIGNEE);
        task.setDescription(TASK_DESCRIPTION);
        task.setTimeToRelease(TASK_TIME_TO_RELEASE);
        task.setStatus(TASK_STATUS);
        task.setKanban(getKanban());

        assertThrows(TaskAlreadyExistException.class, () -> {
            taskService.createTask(task);
        });
    }

    @Test
    void testUpdateTaskThatNotExist() throws Exception {
        Task task = new Task();
        task.setName("Task2");
        task.setAssignee("Assignee2");
        task.setDescription("Description2");
        task.setTimeToRelease(Time.valueOf("00:00:00"));
        task.setStatus(Status.IN_PROGRESS);
        task.setKanban(getKanban());

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTask(task);
        });
    }

    @Test
    void testRenameTaskThatNotExist() throws Exception {
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.renameTask(getKanban(), "Task2", "Task3");
        });
    }
    @Test 
    void testRenameTaskThatAlreadyExist() throws Exception {
        Task task = new Task();
        task.setName(TASK_NAME + "2");
        task.setAssignee(TASK_ASSIGNEE);
        task.setDescription(TASK_DESCRIPTION);
        task.setTimeToRelease(TASK_TIME_TO_RELEASE);
        task.setStatus(TASK_STATUS);
        task.setKanban(getKanban());

        taskRepository.save(task);
        
        assertThrows(TaskAlreadyExistException.class, () -> {
            taskService.renameTask(getKanban(), TASK_NAME, TASK_NAME + "2");
        });
    }

    @Test
    void testDeleteTaskThatNotExist() throws Exception {
        assertThrows(TaskNotFoundException.class, () -> {
            taskService.deleteTask(getKanban(), "Task2");
        });
    }

    private Kanban getKanban() {
        return kanbanRepository.findByName(KANBAN_NAME).get();
    }
}
