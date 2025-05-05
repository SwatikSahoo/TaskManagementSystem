import java.util.Date;

public class Task {
    private final String id;
    private String title;
    private String desc;
    private Date due;
    private int priority;
    private  TaskStatus status;
    private final User user;

    public Task(String id, String title, String desc, Date due, int priority, User user) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.due = due;
        this.priority = priority;
        status=TaskStatus.PENDING;
        this.user = user;
    }

    public User getAssignedUser() {
        return user;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public Date getDue() {
        return due;
    }

    public String getDescription() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
