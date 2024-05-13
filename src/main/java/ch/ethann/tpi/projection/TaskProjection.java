package ch.ethann.tpi.projection;

import java.sql.Time;

import ch.ethann.tpi.model.Task.Status;

public interface TaskProjection {
    public String getName();

    public String getAssignee();

    public String getDescription();

    public Time getTimeToRelease();

    public Status getStatus();

    public KanbanProjection getKanban();
}
