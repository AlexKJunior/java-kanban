package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task<Object> {
    private List<Integer> subTaskIdList= new ArrayList<> ();

    public Epic() {
        super();
        this.subTaskIdList = subTaskIdList;
    }

    public List<Integer> getSubTaskIdList() {
        return subTaskIdList;
    }

    public Object getType() {
        return  TaskType ;
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
}