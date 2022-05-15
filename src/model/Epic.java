package model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private ArrayList<Integer> idSubTasks;

    public <TaskStatus> Epic (String nameTask, String descriptionTask, TaskStatus taskStatus) {
        super ( nameTask, descriptionTask, String.valueOf ( taskStatus ) );
        this.idSubTasks = new ArrayList<> ();
    }

    public Epic (Epic object) {
        super ( object );
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        Epic epic = (Epic) o;
        return Objects.equals ( idSubTasks, epic.idSubTasks );
    }

    @Override
    public int hashCode () {
        return Objects.hash ( idSubTasks );
    }

    @Override
    public String toString () {
        return "Epic{" + "idSubTasks=" + idSubTasks + '}';
    }
}