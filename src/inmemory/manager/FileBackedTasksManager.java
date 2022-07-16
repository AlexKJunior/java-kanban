package inmemory.manager;

import exceptions.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    public static final String HEAD = "id,type,name,status,description,epic\n";

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        if (Files.exists(file.toPath())) {
            try (Reader reader = new FileReader(file.toPath().toString(), StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                bufferedReader.readLine();
                while (bufferedReader.ready()) {
                    String str = bufferedReader.readLine();
                    if (str != null && str.isEmpty()) {
                        String history = bufferedReader.readLine();
                        historyToList(history);
                        List<Integer> tasks = taskfromString(history);
                        Map<Integer, Task> taskMap = fileBackedTasksManager.getTasksMap();
                        Map<Integer, SubTask> subTaskMap = fileBackedTasksManager.getSubTasksMap();
                        if (tasks != null) {
                            for (Integer taskId : tasks) {
                                if (taskMap.containsKey(taskId)) {
                                    fileBackedTasksManager.getTaskSuper(taskId);
                                } else if (subTaskMap.containsKey(taskId)) {
                                    fileBackedTasksManager.getSubTaskSuper(taskId);
                                } else {
                                    fileBackedTasksManager.getEpicSuper(taskId);
                                }
                            }
                        }
                    } else {
                        fileBackedTasksManager.addTypeTask(str);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileBackedTasksManager;

    }

    public static void main (String[] args) {
        FileBackedTasksManager fileBackedTasksManager  = new FileBackedTasksManager (new File (
                "resources/task.csv" ));

        // Создаю задачу 1
        Task task1 = new Task ( 100, "Тестирование 1", "Создать тестовый Task 1", model.Status.NEW );
        fileBackedTasksManager.creationOfTask ( task1 );

        // Создаю задачу 2
        Task task2 = new Task ( 100, "Тестирование 2", "Создать тестовый Task 2", model.Status.NEW );
        fileBackedTasksManager.creationOfTask ( task2 );

        // Создаю Epic1 с двумя подзадачами
        ArrayList<Integer> subTasksListIdOfEpic1 = new ArrayList<> ();
        Epic epic1 = new Epic ( 100, "Тестирование 3", "Создать тестовый Epic 1" );
        int idOfCreatedEpic1 = fileBackedTasksManager.creationOfEpic ( epic1 ).getId ();

        // - подзадача 1 для Epic1
        SubTask subTask1OfEpic1 = new SubTask ( 100, "Тестирование 4"
                , "Создать тестовый SubTask 1", idOfCreatedEpic1 );
        fileBackedTasksManager.creationOfSubTask ( subTask1OfEpic1 );

        //  - подзадача 2 для Epic1
        SubTask subTask2OfEpic1 = new SubTask ( 10, "Тестирование 5"
                , "Создать тестовый SubTask 2", idOfCreatedEpic1 );
        fileBackedTasksManager.creationOfSubTask ( subTask2OfEpic1 );

        // Обращаюсь к задачам по их ID для заполнения истории просмотров
        fileBackedTasksManager.getTaskById ( 2 );
        fileBackedTasksManager.getTaskById ( 1 );
        fileBackedTasksManager.getTaskById ( 2 );
        fileBackedTasksManager.getEpicById ( 3 );
        fileBackedTasksManager.getEpicById ( 3 );
        fileBackedTasksManager.getSubTaskById ( 5 );
        fileBackedTasksManager.getSubTaskById ( 4 );

    }

    private static void historyToList (String history) {
        if (history == null || history.equals("")) {
            return;
        }
        String[] historyList = history.split(",");
    }

    private static List<Integer> taskfromString (String history) {
        return null;
    }

    private void getTaskSuper(int id) {
        super.deleteTaskById(id);
    }

    private void getEpicSuper(int id) {
        super.deleteEpicById(id);
    }

    private void getSubTaskSuper(int id) {
        super.deleteSubTaskById(id);
    }
    private void addTypeTask(String str) {
        String[] taskArray = str.split(",");
        String type = taskArray[1];
        TypeTask TYPE = TypeTask.valueOf(type);

    }
    private final File saveFile;

    public FileBackedTasksManager (File saveFile) {
        this.saveFile = saveFile;
    }

    private void checkSaveFile() {
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

    private void saveToFile() {
        checkSaveFile();
        StringBuilder tasksInString = new StringBuilder();
        for (Task task : getListOfAllTasks().values()) {
            tasksInString.append(tasksToString(task));
        }
        try (FileWriter fileWriter = new FileWriter(saveFile)) {
            fileWriter.write( HEAD + tasksInString + historyToString());
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи файла сохранения");
        }
    }

    private String tasksToString (Task task) {
        StringBuilder stringOfTask = new StringBuilder();
        switch (task.getType()) {
            case TASK : stringOfTask.append(task.getId()).append(',')
                    .append(TypeTask.TASK).append(',')
                    .append(task.getName()).append(',')
                    .append(task.getStatus().toString()).append(',')
                    .append(task.getDescription()).append(',')
                    .append(",").append(',').append("\n");
            case EPIC : {
                Epic epic = (Epic) task;
                StringBuilder subTasks = new StringBuilder(epic.getSubTaskIdList().toString()
                        .replace(',', '/'));
                subTasks.deleteCharAt(0).deleteCharAt(subTasks.length() - 1);
                stringOfTask.append(epic.getId()).append(',')
                        .append(TypeTask.EPIC).append(',')
                        .append(epic.getName()).append(',')
                        .append(epic.getStatus().toString()).append(',')
                        .append(epic.getDescription()).append(',')
                        .append(",").append(",").append("\n");
            }
            case SUBTASK : {
                SubTask subTask = (SubTask) task;
                stringOfTask.append(subTask.getId()).append(',')
                        .append(TypeTask.SUBTASK).append(',')
                        .append(subTask.getName()).append(',')
                        .append(subTask.getStatus().toString()).append(',')
                        .append(subTask.getDescription()).append(',')
                        .append(subTask.getEpicId()).append("\n");
            }
        }
        return stringOfTask.toString();
    }

    private void tasksFromString(String stringOfTask) {
        String[] words = stringOfTask.split(",");
        TypeTask typeTasks = TypeTask.valueOf(words[1]);
        switch (typeTasks) {
            case TASK : creationOfTask( new Task (Integer.parseInt(words[0]), words[2], words[4], model.Status.NEW ))
                    .setStatus( Status.valueOf ( Status.valueOf("resources/task.csv").toString()));
            case EPIC :
                creationOfEpic(new Epic(Integer.parseInt(words[0]), words[2], words[4]));
            case SUBTASK : creationOfSubTask(new SubTask(Integer.parseInt(words[0]), words[2], words[4],
                    Integer.parseInt(words[5]))).setStatus( Status.valueOf ( Status.valueOf("resources/task.csv").
                    toString()));
        }
    }

    Enum<model.Status> valueOf(String status){
        if (status.equals(Status.NEW.toString())) {
            return Status.NEW;
        } else if (status.equals(Status.IN_PROGRESS.toString())) {
            return Status.IN_PROGRESS;
        } else {
            return Status.DONE;
        }
    }

    private String historyToString() {
        StringBuilder historyOfViews = new StringBuilder("\n");
        for (Task task : getHistory()) {
            historyOfViews.append(task.getId()).append(',');
        }
        historyOfViews.deleteCharAt(historyOfViews.length() - 1);
        return historyOfViews.toString();
    }

    private void historyFromString(String stringOfHistory) {
        String[] words = stringOfHistory.split(",");
        for (String word : words) {
            if (getListOfAllTasks().get(Integer.parseInt(word)) != null) {
                historyManager.add(getListOfAllTasks().get(Integer.parseInt(word)));
            }
        }
    }

    public Map<Integer, Task> deleteTasks () {
        saveToFile();
        return taskList;
    }

    public Map<Integer, Epic> deleteEpics() {
        saveToFile();
        return epicList;
    }

    public Map<Integer, SubTask> deleteSubTasks() {
        saveToFile();
        return subTaskList;
    }

    @Override
    public TypeTask getType() {
        saveToFile ();
        return TypeTask.EPIC;
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
        super.creationOfSubTask(subTask);
        saveToFile();
        return subTask;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        saveToFile();
        getListOfAllTasks ();
    }


    @Override
    public Task getTaskById(Integer id) {
        Task task = super.getTaskById(id);
        saveToFile();
        return task;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = super.getEpicById(id);
        saveToFile();
        return epic;
    }

    @Override
    public SubTask getSubTaskById(Integer id) {
        SubTask subTask = super.getSubTaskById(id);
        saveToFile();
        return subTask;
    }


    @Override
    public Task updateTaskByNewTask(Task task) {
        super.updateTaskByNewTask(task);
        saveToFile();
        return task;
    }

    @Override
    public Epic updateEpicByNewEpic(Epic epic) {
        super.updateEpicByNewEpic(epic);
        saveToFile();
        return epic;
    }

    @Override
    public SubTask updateSubTaskByNewSubTask(SubTask subTask) {
        super.updateSubTaskByNewSubTask(subTask);
        saveToFile();
        return subTask;
    }

    @Override
    public Task deleteTaskById(Integer id) {
        Task task = super.deleteTaskById(id);
        saveToFile();
        return task;
    }

    @Override
    public Epic deleteEpicById(Integer id) {
        Epic epic = super.deleteEpicById(id);
        saveToFile();
        return epic;
    }

    @Override
    public SubTask deleteSubTaskById(Integer id) {
        SubTask subTask = super.deleteSubTaskById(id);
        saveToFile();
        return subTask;
    }

}