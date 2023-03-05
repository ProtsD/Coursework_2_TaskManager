import enums.Periodicity;
import enums.TaskType;
import exception.WrongInputException;
import utils.Validation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class TaskService {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Task> taskList = new ArrayList<>();
    private static final List<Task> removedTasks = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            updateDateTime();
            System.out.println();
            System.out.println("Operation:          |   Print tasks:");
            System.out.println("  1 - create        |     5 - today");
            System.out.println("  2 - change        |     6 - specific date");
            System.out.println("  3 - delete        |     7 - next date for a task");
            System.out.println("  4 - order by date |     8 - all");
            System.out.println("                    |     9 - removed");
            System.out.print("Your requested operation:");

            switch (scanner.nextInt()) {
                case 1 -> createTask();
                case 2 -> changeTask();
                case 3 -> deleteTask();
                case 4 -> orderByDate();

                case 5 -> printTaskOnToday(LocalDate.now());
                case 6 -> printTaskOnDate();
                case 7 -> getNextDate();
                case 8 -> printAll();
                case 9 -> printRemoved();
            }
        }
    }

    public static void createTask() {
        try {
            scanner.nextLine();
            LocalDateTime date = insertDateTime();

            System.out.print("Insert type of task (0 - WORK, 1 - PERSONAL): ");
            TaskType type = TaskType.values()[scanner.nextInt()];

            System.out.print("Insert periodicity of task (0 - SINGLE, 1 - DAILY, 2 - WEEKLY, 3 - MONTHLY, 4 - ANNUAL): ");
            Periodicity periodicity = Periodicity.values()[scanner.nextInt()];
            scanner.nextLine();

            System.out.print("Insert title: ");
            String title = Validation.validateString(scanner.nextLine());

            System.out.print("Insert description: ");
            String description = Validation.validateString(scanner.nextLine());
            System.out.println();

            Task task = new Task(date, type, periodicity, title, description);
            taskList.add(task);
            System.out.println("The task has been added: \n" + task);

        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void changeTask() {
        try {
            System.out.print("Task id to change: ");

            int taskId = scanner.nextInt();
            int taskIndex = -1;
            for (Task task : taskList) {
                if (task.getId() == taskId) {
                    System.out.println(task);
                    taskIndex = taskList.indexOf(task);
                    break;
                }
            }
            System.out.println("Select the field you want to change");
            System.out.println("1 - date | 2 - type | 3 - periodicity | 4 - title | 5 - description");

            switch (scanner.nextInt()) {
                case 1 -> {
                    scanner.nextLine();
                    taskList.get(taskIndex).setDateTime(insertDateTime());
                }
                case 2 -> {
                    System.out.print("Insert type of task (0 - WORK, 1 - PERSONAL): ");
                    taskList.get(taskIndex).setType(TaskType.values()[scanner.nextInt()]);
                }
                case 3 -> {
                    System.out.print("Insert periodicity of task (0 - SINGLE, 1 - DAILY, 2 - WEEKLY, 3 - MONTHLY, 4 - ANNUAL): ");
                    taskList.get(taskIndex).setPeriodicity(Periodicity.values()[scanner.nextInt()]);
                }
                case 4 -> {
                    scanner.nextLine();
                    System.out.print("Insert title: ");
                    taskList.get(taskIndex).setTitle(Validation.validateString(scanner.nextLine()));
                }
                case 5 -> {
                    scanner.nextLine();
                    System.out.print("Insert description: ");
                    taskList.get(taskIndex).setDescription(Validation.validateString(scanner.nextLine()));
                }
            }
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteTask() {
        System.out.print("Task id to delete: ");
        int taskId = scanner.nextInt();
        for (Task task : taskList) {
            if (task.getId() == taskId) {
                System.out.println(task);
                removedTasks.add(task);
                taskList.remove(task);
                break;
            } else {
                System.out.println("Given id is absent");
            }
        }
    }

    public static void orderByDate() {
        taskList = taskList.stream()
                .sorted(Comparator.comparing(Task::getDateTime))
                .collect(Collectors.toList());
    }

    public static void printTaskOnToday(LocalDate ld) {
        long numOfTasks = taskList.stream()
                .filter(t -> t.isActive())
                .peek(t -> System.out.println("*********** Tasks for today ***********\n" + t))
                .count();
        if (numOfTasks == 0) System.out.println("There is no task for today");
    }

    public static void printTaskOnDate() {
        scanner.nextLine();
        LocalDate ld = insertDate();
        System.out.println("*********** Tasks for requested date ***********");
        long numOfTasks = taskList.stream()
                .filter(t -> LocalDate.from(t.getDateTime()).isEqual(ld))
                .peek(System.out::println)
                .count();
        if (numOfTasks == 0) System.out.println("There is no task for requested date");
    }

    public static void getNextDate() {
        System.out.print("Task id: ");
        int taskId = scanner.nextInt();
        for (Task task : taskList) {
            if (task.getId() == taskId) {
                System.out.println(task);
                System.out.println("Next date:     " + task.nextDate());
                break;
            } else {
                System.out.println("Given id is absent");
            }
        }
    }

    public static void printAll() {
        for (Task t : taskList) {
            System.out.println(t);
        }
    }

    public static void printRemoved() {
        if (removedTasks.size() == 0) System.out.println("There is no removed task");
        for (Task t : removedTasks) {
            System.out.println(t);
        }
    }

    public static LocalDateTime insertDateTime() {
        LocalDateTime ldt = LocalDateTime.of(1111, 11, 11, 11, 11); //Как обойтись без инициализации?
        try {
            System.out.print("Type the date in format dd.MM.yyyy HH:mm: ");
            String str = scanner.nextLine();
            ldt = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Wrong date format!");
            System.out.println();
            insertDateTime();
        }
        return ldt;
    }

    public static LocalDate insertDate() {
        LocalDate ld = LocalDate.of(1111, 11, 11); //Как обойтись без инициализации?
        try {
            System.out.print("Type the date in format dd.MM.yyyy: ");
            String str = scanner.nextLine();
            ld = LocalDate.parse(str, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Wrong date format!");
            System.out.println();
            insertDate();
        }
        return ld;
    }

    public static void updateDateTime() {
        for (Task t : taskList) {
            t.updateDateTime();
        }
    }
}