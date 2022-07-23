package inmemory.manager;

import exceptions.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static int uniqueTaskId;
    public static final String HEAD = "id,type,name,status,description,epic\n";

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        if (Files.exists(file.toPath())) {
            try (Reader reader = new FileReader(file.toPath().toString(), StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                bufferedReader.readLine();
                while (bufferedReader.ready()) {
                    String str = bufferedReader.readLine();
                    if (str.isEmpty()) {
                        String history = bufferedReader.readLine();
                         if(history != null) {
                             fileBackedTasksManager.historyFromString ( history );
                         }
                    } else {
                        fileBackedTasksManager.tasksFromString(str);
                    }
                }
                uniqueTaskId +=1;
            } catch (IOException e) {
            }
        }
        return fileBackedTasksManager;
}

    public static void main (String[] args) {// Создаем задачи и тестируем программу

        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile ( new File (
                "resources/task.csv" ) );

        // Создаю задачу 1
        Task task1 = new Task ( 100, "Тестирование 1", "Создать тестовый Task 1" );
        fileBackedTasksManager.creationOfTask ( task1 );

        // Создаю задачу 2
        Task task2 = new Task ( 100, "Тестирование 2", "Создать тестовый Task 2" );
        fileBackedTasksManager.creationOfTask ( task2 );
        System.out.println(fileBackedTasksManager.getHistory());
        // Создаю Epic1 с двумя подзадачами
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
        System.out.println(fileBackedTasksManager.getHistory());

        System.out.println("Выводим Task 1" + "\n" + fileBackedTasksManager.getTaskById(0));
        System.out.println("Выводим Epic 2" + "\n" + fileBackedTasksManager.getTaskById(6));
        // Обращаюсь к задачам по их ID для заполнения истории просмотров
        fileBackedTasksManager.getTaskById ( 2 );
        fileBackedTasksManager.getTaskById ( 1 );
        fileBackedTasksManager.getTaskById ( 2 );
        fileBackedTasksManager.getEpicById ( 3 );
        fileBackedTasksManager.getEpicById ( 3 );
        fileBackedTasksManager.getSubTaskById ( 5 );
        fileBackedTasksManager.getSubTaskById ( 4 );
        System.out.println("Выводим историю" + "\n" + fileBackedTasksManager.getHistory());

        fileBackedTasksManager.deleteTaskById(task1.getId());  //Тестируем удаление
        System.out.println("Выводим историю без задачи 1" + "\n"
                + fileBackedTasksManager.getHistory());
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
        switch (task.getTypeTask()) {
            case TASK :
                stringOfTask.append(task.getId()).append(',')
                    .append(TypeTask.TASK).append(',')
                    .append(task.getName()).append(',')
                    .append(task.getStatus().toString()).append(',')
                    .append(task.getDescription()).append(',')
                    .append("\n");
                break;
            case EPIC : {
                Epic epic = (Epic) task;
                stringOfTask.append(epic.getId()).append(',')
                        .append(TypeTask.EPIC).append(',')
                        .append(epic.getName()).append(',')
                        .append(epic.getStatus().toString()).append(',')
                        .append(epic.getDescription()).append(',')
                        .append("\n");
                break;
            }
            case SUBTASK : {
                SubTask subTask = (SubTask) task;
                stringOfTask.append(subTask.getId()).append(',')
                        .append(TypeTask.SUBTASK).append(',')
                        .append(subTask.getName()).append(',')
                        .append(subTask.getStatus().toString()).append(',')
                        .append(subTask.getDescription()).append(',')
                        .append(subTask.getEpicId()).append("\n");
                break;
            }
        }
        return stringOfTask.toString();
    }

    private void tasksFromString(String stringOfTask) {

        String[] words = stringOfTask.split(",");
        TypeTask typeTasks = TypeTask.valueOf(words[1]);
        int id = Integer.parseInt(words[0]);
        if (uniqueTaskId < id) {
            uniqueTaskId = id;
        }

        switch (typeTasks) {

            case TASK :
                Task task = new Task(id, words[2], words[4] );
                task.setStatus(Status.valueOf(words[3]));
                tasks.put(id, task);
                break;
            case EPIC :
                Epic epic = new Epic( id, words[2], words[4]);
                epic.setStatus(Status.valueOf(words[3]));
                epics.put(id, epic);
                break;
            case SUBTASK :
                SubTask subTask = new SubTask(id, words[2], words[4],Integer.parseInt(words[5]));
                subTask.setStatus(Status.valueOf(words[3]));
                subtasks.put(id, subTask);
                epics.get(subTask.getEpicId());
                epics.get(subTask.getEpicId()).getSubTaskIdList().add(subTask.getId());
                break;
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
    
    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        saveToFile();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        saveToFile();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        saveToFile();
    }

    @Override
    public Task creationOfTask(Task task) {
        super.creationOfTask(task);
        saveToFile();
        return task;
    }

    @Override
    public Epic creationOfEpic(Epic epic) {
        super.creationOfEpic(epic);
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