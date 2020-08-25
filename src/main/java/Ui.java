import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the user interface for the Duke application. The user interface is responsible
 * for receiving commands from and displaying messages to the user.
 */
public class Ui {

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println("-------------------------------------------------------------------------------------");
    }

    /**
     * Displays a welcome message to the user when the Duke application starts up.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        showLine();
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Displays a farewell message to the user when the Duke application exits.
     */
    public void showFarewell() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays an error message to the user when the Duke application encounters
     * an error.
     * @param message the error message.
     */
    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    /**
     * Displays a message indicating that a particular task has been marked as done.
     * @param task the task that has been marked as done.
     */
    public void showDone(Task task) {
        showLine();
        System.out.println("Nice! I've marked this task as done: ");
        System.out.println("    " + task);
        showLine();
    }

    /**
     * Displays a message indicating that a new task has been added to the task list.
     * The total number of tasks in the task list after addition is also displayed.
     * @param task the task that has been added to the task list.
     * @param size the total number of tasks in the task list after addition.
     */
    public void showAdd(Task task, int size) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message indicating that a task has been deleted from the task list.
     * The total number of tasks in the task list after deletion is also displayed.
     * @param task the task that has been deleted from the task list.
     * @param size the total number of tasks in the task list after deletion.
     */
    public void showDelete(Task task, int size) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Displays the tasks in the task list.
     * @param lst a list of String objects representing the tasks in the task list.
     */
    public void showList(ArrayList<String> lst) {
        showLine();
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < lst.size(); i++) {
            System.out.println((i + 1) + "." + lst.get(i));
        }
        showLine();
    }

    /**
     * Reads the command entered by the user.
     * @return the user command.
     */
    public String readCommand() {
        return scanner.nextLine();
    }
}
