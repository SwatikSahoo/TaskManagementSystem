import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskManager {
    private static TaskManager instance;
    private final Map<String, Task> tasks;
    private final Map<String, List<Task>> userTasks;


    public TaskManager() {
        this.tasks = new ConcurrentHashMap<>();
        this.userTasks = new ConcurrentHashMap<>();
    }
    public static synchronized TaskManager getInstance(){
        if (instance==null){
            instance= new TaskManager();
        }
        return instance;
    }
    public void createTask(Task task){
        tasks.put(task.getId(), task);
        assignTaskToUser(task.getAssignedUser(),task);

    }

    private void assignTaskToUser(User user, Task task) {
        userTasks.computeIfAbsent(user.getId(),k-> new CopyOnWriteArrayList<>()).add(task);
    }
    public void updateTask(Task updatedTask){
        Task existingTask= tasks.get(updatedTask.getId());
        if (existingTask!=null){
            existingTask.setDesc(updatedTask.getDescription());
            existingTask.setDue(updatedTask.getDue());
            existingTask.setPriority(updatedTask.getPriority());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setTitle(updatedTask.getTitle());
            User prev= existingTask.getAssignedUser();
            User newUser=updatedTask.getAssignedUser();
            if(prev!=newUser){
                unassignTask(prev,existingTask);
                assignTaskToUser(newUser,existingTask);
            }
        }
    }

    private void unassignTask(User prev, Task task) {
        List<Task> t = userTasks.get(prev.getId());
        if (t!=null){
            t.remove(task);
        }
    }
    public void deleteTask(String taskId){
        Task task= tasks.remove(taskId);
        if (task!=null){
            unassignTask(task.getAssignedUser(), task);
        }
    }
    public List<Task> searchTask(String keyword){
        List<Task> matchingTask= new ArrayList<>();
        for (Task task:tasks.values()){
            if (task.getTitle().contains(keyword) || task.getDescription().contains(keyword)){
                matchingTask.add(task);
            }
        }
        return matchingTask;
    }
    public List<Task> filterTask(TaskStatus status, Date startDate, Date endDate, int priority){
        List<Task> filteredTask= new ArrayList<>();
        for (Task task: tasks.values()){
            if (task.getStatus() ==status && task.getDue().compareTo(startDate) >= 0 &&
                    task.getDue().compareTo(endDate)<=0 && task.getPriority()==priority){
                filteredTask.add(task);
            }

        }
        return filteredTask;
    }
    public List<Task> getTaskHistory(User user){
        return new ArrayList<>(userTasks.getOrDefault(user.getId(), new ArrayList<>()));
    }
    public void markTaskAsCompleted(String taskId){
        Task task= tasks.get(taskId);
        if (task != null){
            synchronized (task){
                task.setStatus(TaskStatus.COMPLETED);
            }
        }
    }
}
