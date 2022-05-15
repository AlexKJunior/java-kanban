package model;

import java.util.Objects;

public class Task {
    private int id;
    private final String name;
    private final String description;
    private Enum status;

    public Task(String nameTask, String descriptionTask, String statusTask) {
        this.name = nameTask;
        this.description = descriptionTask;

    }

     public Task(String nameTask, String descriptionTask) {

        this.name = nameTask;
        this.description = descriptionTask;
    }

    public Task (Task task) {
        this(task.name, task.description, String.valueOf(task.status));
    }

     public int getId() {
        return id;
    }

     public String getName() {
        return name;
    }

     public String getDescription() {
        return description;
    }

     public Enum getStatus() {
        return status;
    }

    @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;
       Task task = (Task) o;
       return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.getDescription ())
               && Objects.equals(status, task.status);
   }

   @Override
   public int hashCode() {
       return Objects.hash(id, name, description, status);
   }

   @Override
  public String toString() {
      return "ID задачи Task=\"" + id + "\", Название задачи=\"" + name + "\", Описание=\"" + description
              + "\", Статус=\"" + status + "\"";
  }

}