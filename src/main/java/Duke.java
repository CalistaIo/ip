import java.util.Scanner;

public class Duke {

    private static Task[] lst = new Task[100];
    private static int counter = 0;

    public static void showList() {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < counter; i++) {
            System.out.println((i + 1) + "." + lst[i]);
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }

    public static void markDone(int i) {
        lst[i].markAsDone();
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Nice! I've marked this task as done: ");
        System.out.println("    " + lst[i]);
        System.out.println("-------------------------------------------------------------------------------------");
    }

    public static void addTask(Task t) {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Got it. I've added this task:");
        lst[counter] = t;
        System.out.println("    " + lst[counter]);
        counter += 1;
        System.out.println("Now you have " + counter + " tasks in the list.");
        System.out.println("-------------------------------------------------------------------------------------");
    }

    public static void processString(String st) throws InvalidDoneException, InvalidTaskArgumentException,
            InvalidCommandException {
        if (st.equals("list")) {
            showList();
        } else if ((st.length() >= 4) && (st.substring(0, 4).equals("done"))) {
            if (st.length() <= 5) {
                throw new InvalidDoneException("\u2639" + " OOPS!!! The task to be marked as done is not " +
                        "specified.");
            } else {
                try {
                    int i = Integer.parseInt(st.substring(5)) - 1;
                    if (i >= counter) {
                        throw new InvalidDoneException("\u2639" + " OOPS!!! The number specified does not represent " +
                                "a valid task.");
                    }
                    markDone(i);
                } catch (NumberFormatException e) {
                    throw new InvalidDoneException("\u2639" + " OOPS!!! The task to be marked as done is not " +
                            "specified by a valid number.");
                }
            }
        } else if ((st.length() >= 4) && (st.substring(0, 4).equals("todo"))) {
            if (st.length() <= 5) {
                throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The description of a todo cannot " +
                        "be empty.");
            } else {
                addTask(new ToDo(st.substring(5)));
            }
        } else if ((st.length() >= 8) && (st.substring(0, 8).equals("deadline"))) {
            if (st.length() <= 9) {
                throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The deadline is lacking a " +
                        "description/date.");
            } else {
                String[] arr = st.substring(9).split(" /by ");
                if (arr.length < 2) {
                    throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The deadline is lacking a " +
                            "description/date.");
                } else {
                    addTask(new Deadline(arr[0], arr[1]));
                }
            }
        } else if ((st.length() >= 5) && (st.substring(0, 5).equals("event"))) {
            if (st.length() <= 6) {
                throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The event is lacking a " +
                        "description/date.");
            } else {
                String[] arr = st.substring(6).split(" /at ");
                if (arr.length < 2) {
                    throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The event is lacking a " +
                            "description/date.");
                } else {
                    addTask(new Event(arr[0], arr[1]));
                }
            }
        } else {
            throw new InvalidCommandException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");
        System.out.println("-------------------------------------------------------------------------------------");

        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        while (!s.equals("bye")) {
            try {
                processString(s);
            } catch (InvalidTaskArgumentException | InvalidDoneException | InvalidCommandException e) {
                System.out.println("-------------------------------------------------------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("-------------------------------------------------------------------------------------");
            } finally {
                s = scan.nextLine();
            }
        }

        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("-------------------------------------------------------------------------------------");
    }
}
