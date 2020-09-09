import java.util.ArrayList;

/**
 * Represents the task list of the Duke application. The task list is
 * responsible for storing and modifying the current tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Returns a TaskList.
     * @param lst List of String objects representing Tasks.
     */
    public TaskList(ArrayList<String> lst) {
        ArrayList<Task> temp = new ArrayList<>();
        for (String s : lst) {
            String[] arr = s.split(" \\| ");
            if (arr[0].equals("T")) {
                ToDo todo = new ToDo(arr[2]);
                if (arr[1].equals("1")) {
                    todo.markAsDone();
                }
                temp.add(todo);
            } else if (arr[0].equals("D")) {
                Deadline deadline = new Deadline(arr[2], arr[3]);
                if (arr[1].equals("1")) {
                    deadline.markAsDone();
                }
                temp.add(deadline);
            } else {
                Event event = new Event(arr[2], arr[3]);
                if (arr[1].equals("1")) {
                    event.markAsDone();
                }
                temp.add(event);
            }
        }
        this.tasks = temp;
    }

    /**
     * Returns the current size of the task list.
     * @return Current size of the task list.
     */
    public int getLength() {
        return this.tasks.size();
    }

    /**
     * Returns the task list.
     * @return Task list.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Creates and sends a list of String objects representing the
     * tasks in the task list to the user interface of the Duke
     * application for display.
     * @param ui User interface of the Duke application.
     */
    public String showList(Ui ui) {
        ArrayList<String> lst = new ArrayList<>();
        for (Task task : tasks) {
            lst.add(task.toString());
        }
        return ui.showList(lst);
    }

    /**
     * Marks a specified task as done and alerts the user interface
     * of the Duke application to display the corresponding message.
     * @param pos Position of the task to be marked as done in the task list.
     * @param ui User interface of the Duke application.
     */
    public String markDone(int pos, Ui ui) {
        tasks.get(pos).markAsDone();
        return ui.showDone(tasks.get(pos));
    }

    public String deleteTasks(ArrayList<String> nums, Ui ui) {
        ArrayList<Task> remove = new ArrayList<>();
        ArrayList<Task> keep = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (nums.contains(Integer.toString(i))) {
                remove.add(tasks.get(i));
            } else {
                keep.add(tasks.get(i));
            }
        }
        tasks.clear();
        for (Task t : keep) {
            tasks.add(t);
        }
        return ui.showDelete(remove, tasks.size());
    }

    public String deleteAll(Ui ui) {
        ArrayList<Task> remove = new ArrayList<>();
        for (Task t : tasks) {
            remove.add(t);
        }
        tasks.clear();
        return ui.showDelete(remove, tasks.size());
    }

    /**
     * Adds a new task to the task list and alerts the user interface
     * of the Duke application to display the corresponding message.
     * @param task New task to be added to the task list.
     * @param ui User interface of the Duke application.
     */
    public String addTask(Task task, Ui ui) {
        tasks.add(task);
        return ui.showAdd(tasks.get(tasks.size() - 1), tasks.size());
    }

    /**
     * Finds the tasks in the task list that match the given keyword.
     * @param keyword Keyword given by the user.
     * @param ui User interface of the Duke application.
     */
    public String findTask(String keyword, Ui ui) {
        ArrayList<String> lst = new ArrayList<>();
        for (Task t : tasks) {
            if (t.toString().contains(keyword)) {
                lst.add(t.toString());
            }
        }
        return ui.showFind(lst);
    }
}
