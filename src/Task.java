import enums.Periodicity;
import enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    //    private UUID idGenerator = UUID.randomUUID();
    private static int idGenerator;
    private final int id;
    private LocalDateTime dateTime;
    private TaskType taskType;
    private Periodicity periodicity;
    private String title;
    private String description;

    public Task(LocalDateTime dateTime, TaskType taskType, Periodicity periodicity, String title, String description) {
        this.id = ++idGenerator;
        this.dateTime = dateTime;
        this.taskType = taskType;
        this.periodicity = periodicity;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TaskType getType() {
        return taskType;
    }

    public void setType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return LocalDate.from(dateTime).isEqual(LocalDate.now());
    }

    public void updateDateTime() {
        while (dateTime.isBefore(LocalDateTime.now().minusDays(1))) {

            switch (periodicity) {
                case DAILY -> dateTime = dateTime.plusDays(1);
                case WEEKLY -> dateTime = dateTime.plusWeeks(1);
                case MONTHLY -> dateTime = dateTime.plusMonths(1);
                case ANNUAL -> dateTime = dateTime.plusYears(1);
            }
        }
    }

    public LocalDateTime nextDate() {
        LocalDateTime ldt = LocalDateTime.of(1111, 11, 11, 11, 11); //Как обойтись без инициализации?
        switch (periodicity) {
            case DAILY -> ldt = dateTime.plusDays(1);
            case WEEKLY -> ldt = dateTime.plusWeeks(1);
            case MONTHLY -> ldt = dateTime.plusMonths(1);
            case ANNUAL -> ldt = dateTime.plusYears(1);
        }
        return ldt;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(dateTime, task.dateTime) && taskType == task.taskType
                && periodicity == task.periodicity && Objects.equals(title, task.title)
                && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, taskType, periodicity, title, description);
    }

    @Override
    public String toString() {
        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "Task id" + id +
                ":      " + dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                ", type=" + taskType +
                ", periodicity=" + periodicity +
                ", title='" + title + '\'' +
                ", description='" + description;
    }
}
