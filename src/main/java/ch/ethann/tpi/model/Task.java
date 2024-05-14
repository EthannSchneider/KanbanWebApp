package ch.ethann.tpi.model;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "kanban_id" }) })
public class Task {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ToString.Exclude
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String assignee;

    @Column(nullable = false, length=1024)
    private String description = "";

    @Column(nullable = false)
    private Time timeToRelease;

    @Column(nullable = false)
    private Status status = Status.BACKLOG;

    @ManyToOne
    @JoinColumn(nullable = false, name = "kanban_id")
    private Kanban kanban;

    public enum Status {
        BACKLOG, TODO, IN_PROGRESS, TO_TEST, DONE
    }
}
