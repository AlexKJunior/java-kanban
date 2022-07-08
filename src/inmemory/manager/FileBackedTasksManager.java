package inmemory.manager;

import exceptions.ManagerSaveException;
import model.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager newTaskManager = new FileBackedTasksManager(file);
        return newTaskManager;
    }

    private final File saveFile;

    public FileBackedTasksManager (File saveFile) {
        this.saveFile = saveFile;

    }

    public void checkSaveFile() {
        File directory = new File(saveFile.getParent());
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new ManagerSaveException("Ошибка при создании директории для сохранения");
            }
        }
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка при создании файла сохранения" + e.getMessage());
            }
        }
    }

    public void saveToFile() {
        checkSaveFile();
        final String ADD_HEAD = "id,type,name,status,description,epic,subtasks\n";
        StringBuilder tasksInString = new StringBuilder();
        for (Task task : getListOfAllTasks().values()) {
            tasksInString.append(tasksToString());
        }
        try (FileWriter fileWriter = new FileWriter(saveFile)) {
            String head = new String();
            fileWriter.write(head + tasksInString + historyToString());
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла сохранения");
        }
    }

    public void readSaveFromFile() {
        checkSaveFile();
        StringBuilder resultOfReading = new StringBuilder();
        try (FileReader fileReader = new FileReader(saveFile)) {
            while (fileReader.ready()) {
                resultOfReading.append((char) fileReader.read());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла сохранения");
        }
        String value = resultOfReading.toString();
        if (!value.isBlank()) {
            String[] lines = value.split("\n");
            for (int i = 1; i < lines.length; i++) {
                if (!lines[i].equals("")) {
                    tasksFromString(lines[i]);
                } else {
                    historyFromString(lines[i + 1]);
                    break;
                }
            }
            saveToFile();
        }
    }

    public String tasksToString() {
        StringBuilder stringOfTask = new StringBuilder();
        Task task = null;
        TypeTask typeTasks = TypeTask.valueOf(task.getClass().getSimpleName().toUpperCase());
        switch (typeTasks) {
            case TASK -> stringOfTask.append(task.getId()).append(',')
                    .append(TypeTask.TASK).append(',')
                    .append(task.getName()).append(',')
                    .append(task.getStatus().toString()).append(',')
                    .append(task.getDescription()).append(',')
                    .append("-").append(',').append("-,\n");
            case EPIC -> {
                Epic epic = (Epic) task;
                StringBuilder subTasks = new StringBuilder(epic.getSubTaskIdList().toString()
                        .replace(',', '/'));
                subTasks.deleteCharAt(0).deleteCharAt(subTasks.length() - 1);
                stringOfTask.append(epic.getId()).append(',')
                        .append(TypeTask.EPIC).append(',')
                        .append(epic.getName()).append(',')
                        .append(epic.getStatus().toString()).append(',')
                        .append(epic.getDescription()).append(',')
                        .append("-").append(",").append(subTasks).append(",\n");
            }
            case SUBTASK -> {
                SubTask subTask = (SubTask) task;
                stringOfTask.append(subTask.getId()).append(',')
                        .append(TypeTask.SUBTASK).append(',')
                        .append(subTask.getName()).append(',')
                        .append(subTask.getStatus().toString()).append(',')
                        .append(subTask.getDescription()).append(',')
                        .append(subTask.getEpicId()).append(',').append("-,\n");
            }
        }
        return stringOfTask.toString();
    }

    public void tasksFromString(String stringOfTask) {
        String[] words = stringOfTask.split(",");
        TypeTask typeTasks = TypeTask.valueOf(words[1]);
        switch (typeTasks) {
            case TASK -> creationOfTask(new Task(Integer.parseInt(words[0]), words[2], words[4]))
                    .setStatus(readStatus(words[3]));
            case EPIC ->
                    creationOfEpic(new Epic(Integer.parseInt(words[0]), words[2], words[4], new ArrayList<Integer>()));
            case SUBTASK -> creationOfSubTask(new SubTask(Integer.parseInt(words[0]), words[2], words[4],
                    Integer.parseInt(words[5]))).setStatus(readStatus(words[3]));
        }
    }

    public Status readStatus(String status) {
        if (status == Status.NEW.toString()) {
            return Status.NEW;
        } else if (status == Status.IN_PROGRESS.toString()) {
            return Status.IN_PROGRESS;
        } else {
            return Status.DONE;
        }
    }

    public String historyToString() {
        StringBuilder historyOfViews = new StringBuilder("\n");
        for (Task task : getHistory()) {
            historyOfViews.append(task.getId()).append(',');
        }
        historyOfViews.deleteCharAt(historyOfViews.length() - 1);
        return historyOfViews.toString();
    }

    public void historyFromString(String stringOfHistory) {
        String[] words = stringOfHistory.split(",");
        for (String word : words) {
            if (getListOfAllTasks().get(Integer.parseInt(word)) != null) {
                historyManager.add(getListOfAllTasks().get(Integer.parseInt(word)));
            }
        }
    }

    @Override
    public Task creationOfTask(Task task) {
        saveToFile();
        return task;
    }

    @Override
    public Epic creationOfEpic(Epic epic) {
        saveToFile();
        return epic;
    }

    @Override
    public SubTask creationOfSubTask(SubTask subTask) {
        saveToFile();
        return subTask;
    }

    @Override
    public Map<Integer, Task> deleteAllTasks() {
        saveToFile();
        return getListOfAllTasks();
    }

    @Override
    public Map<Integer, Task> deleteTasks() {
        saveToFile();
        return taskList;
    }

    @Override
    public Map<Integer, Epic> deleteEpics() {
        saveToFile();
        return epicList;
    }

    @Override
    public Map<Integer, SubTask> deleteSubTasks() {
        saveToFile();
        return null;
    }

    @Override
    public Task getTaskById(Integer id) {
        saveToFile();
        return null;
    }

    @Override
    public Epic getEpicById(Integer id) {
        saveToFile();
        return epicList.get(id);
    }

    @Override
    public SubTask getSubTaskById(Integer id) {
        saveToFile();
        return subTaskList.get(id);
    }


    @Override
    public Task updateTaskByNewTask(Task task) {
        saveToFile();
        return task;
    }

    @Override
    public Epic updateEpicByNewEpic(Epic epic) {
        saveToFile();
        return epic;
    }

    @Override
    public SubTask updateSubTaskByNewSubTask(SubTask subTask) {
        saveToFile();
        return subTask;
    }

    @Override
    public Task deleteTaskById(Integer id) {
        saveToFile();
        return null;
    }

    @Override
    public Epic deleteEpicById(Integer id) {
        saveToFile();
        return null;
    }

    @Override
    public SubTask deleteSubTaskById(Integer id) {
        saveToFile();
        return null;
    }
}