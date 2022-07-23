package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    protected LocalDateTime EndTime;
    private final List<Integer> subTaskIdList= new ArrayList<> ();

    public Epic(int id, String name, String description) {
        super(id, name, description );
    }

    public LocalDateTime getEndTime() {
        return startTime;
    }

    public List<Integer> getSubTaskIdList() {
        return subTaskIdList;
    }

    @Override
    public TypeTask getTypeTask() {
        return TypeTask.EPIC;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", subTaskIdList=" + subTaskIdList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskIdList, epic.subTaskIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIdList);
    }

    public boolean updateSubTaskIdList () {
        return false;
    }

    public void add (int id) {
    }
}