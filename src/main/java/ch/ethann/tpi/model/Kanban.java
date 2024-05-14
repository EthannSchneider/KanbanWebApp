package ch.ethann.tpi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Kanban {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ToString.Exclude
	private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length=1024)
    private String description;

    @Column(nullable = false, length = 6)
    private String boxColors;
}
