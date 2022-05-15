package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class EpicTask extends Task {
    private final ArrayList<SubTask> subTasks;

    public EpicTask(String nameEpicTask, String descriptionEpicTask, ArrayList<SubTask> subTasks) {
        super(nameEpicTask, descriptionEpicTask);
        this.setStatus(getEpicTaskStatus());
        this.subTasks = subTasks;
    }

    private String getEpicTaskStatus() {
        return null;
    }

    EpicTask(EpicTask epicTask) {
        this(epicTask.getName(), epicTask.getDescription(), epicTask.subTasks);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return Objects.equals(subTasks, epicTask.subTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTasks);
    }

    @Override
    public String toString() {
        return "ID задачи Epic=\"" + getId() + "\", Название Epic задачи=\"" + getName() + "\", Описание=\""
                + getDescription() + "\"" + ", " + Arrays.toString(subTasks.toArray()) + ", Статус=\""
                + getStatus() + "\"";
    }
    public static class SubTask extends Task {
        private final String nameEpicTask;

        public SubTask(String nameEpicTask, String nameSubTask, String descriptionSubTask, String statusSubTask) {
            super(nameSubTask, descriptionSubTask, statusSubTask);
            this.nameEpicTask = nameEpicTask;
        }



        SubTask(SubTask subtask) {
            this(subtask.nameEpicTask, subtask.getName(), subtask.getDescription(), subtask.getStatus());
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            SubTask subTask = (SubTask) o;
            return Objects.equals(nameEpicTask, subTask.nameEpicTask);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), nameEpicTask);
        }

        @Override
        public String toString() {
            return "ID подзадачи SubTask=\"" + getId() + "\", Название Epic задачи=\"" + nameEpicTask
                    + "\", Название подзадачи=\"" + getName() + "\", Описание=\"" + getDescription() + "\", Статус=\""
                    + getStatus() + "\"";
        }
    }

}
