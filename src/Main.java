import java.util.Date;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TaskManager sys= TaskManager.getInstance();
        //Create Users
        User user1= new User("1","John","john@mail.com");
        User user2= new User("2","Mel","mel@mail.com");

        //Create Tasks
        Task task1= new Task("1","Task1","1st task", new Date(), 1, user1);
        Task task2= new Task("2","Task2","2nd Task",new Date(),2,user2);
        // Add Tasks
        sys.createTask(task1);
        sys.createTask(task2);
        //updateTask
        task2.setDesc("Updated Description");
        sys.updateTask(task2);
        //searchTask
        List<Task> searchResults =sys.searchTask("Task");
        System.out.println("Search Results: ");
        for (Task task: searchResults){
            System.out.println(task.getTitle());
        }
        //Filter Task
        List<Task> filtered= sys.filterTask(TaskStatus.PENDING, new Date(0), new Date(),1);
        System.out.println("Filtered Tasks: ");
        for (Task task: filtered ){
            System.out.println(task.getTitle());
        }
        //Mark Completed
        sys.markTaskAsCompleted("1");
        //Get Task History
        List<Task> history= sys.getTaskHistory(user1);
        System.out.println("Task History of "+user1.getName()+" is");
        for (Task task: history){
            System.out.println(task.getTitle());
        }
        // Delete Task
        sys.deleteTask("1");

    }
}