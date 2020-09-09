import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents the parser of the Duke application. The parser is responsible for
 * analysing the user command so that it can be understood by the application.
 */
public class Parser {

    /**
     * Parses the dates/times of deadlines and events.
     * @param date String that represents either a date in YYYY-MM-DD
     *             format, or a timestamp in YYYY-MM-DD HHMM format.
     * @return String that represents an alternative format of the date/time.
     * @throws DateException If the given date/time is not in a format
     * appropriate for parsing.
     */
    public static String parseDate(String date) throws DateException {
        try {
            String[] dates = date.split(" ");
            String processedDate = "";
            String processedTime = "";
            if (dates.length == 1) {
                LocalDate localDate = LocalDate.parse(dates[0]);
                processedDate = localDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
                return processedDate;
            } else {
                LocalDate localDate = LocalDate.parse(dates[0]);
                processedDate = localDate.format(DateTimeFormatter.ofPattern("MMM d yyyy"));

                double time = Double.parseDouble(dates[1]);
                if (time >= 1300) {
                    time -= 1200;
                }
                int hour = (int) (time / 100);
                if (hour == 24 || hour == 0) {
                    processedTime += "12.";
                } else {
                    processedTime += hour + ".";
                }
                int minute = (int) (time % 100);
                if (minute < 10) {
                    processedTime += "0" + minute;
                } else {
                    processedTime += minute;
                }
                if ((Double.parseDouble(dates[1]) >= 1200)
                        && (Double.parseDouble(dates[1]) < 2400)) {
                    processedTime += "pm";
                } else {
                    processedTime += "am";
                }
                return processedDate + " " + processedTime;
            }
        } catch (java.time.format.DateTimeParseException e) {
            throw new DateException("Sorry! I don't understand the date/time. Please specify the date/time "
                    + "in YYYY-MM-DD or YYYY-MM-DD HHMM format.");
        }
    }

    /**
     * Parses the user command.
     * @param s User command.
     * @param size Current size of the task list.
     * @return List of String objects that represent the parsed user command.
     * @throws InvalidDoneException If for a done command, either no task is
     * specified or the argument provided does not represent a valid task.
     * @throws InvalidTaskArgumentException If the command to add tasks is not
     * correctly or completely specified.
     * @throws InvalidDeleteException If for a delete command, either no task is
     * specified or the argument provided does not represent a valid task.
     * @throws InvalidCommandException If the user command cannot be understood.
     * @throws DateException If the given date/time for an event/deadline is not
     * in a format appropriate for parsing.
     */
    public ArrayList<String> parseString(String s, int size)
            throws InvalidDoneException, InvalidTaskArgumentException,
            InvalidDeleteException, InvalidCommandException, DateException {
        ArrayList<String> lst = new ArrayList<>();
        if (s.equals("list")) {
            lst.add("Show");
            return lst;
        } else if ((s.length() >= 4) && (s.substring(0, 4).equals("done"))) {
            if (s.length() <= 5) {
                throw new InvalidDoneException("\u2639" + " OOPS!!! The task to be marked as done is not "
                        + "specified.");
            } else {
                try {
                    int i = Integer.parseInt(s.substring(5)) - 1;
                    if ((i < 0) || (i >= size)) {
                        throw new InvalidDoneException("\u2639" + " OOPS!!! The number specified does not represent "
                                + "a valid task.");
                    }
                    lst.add("Done");
                    lst.add(Integer.toString(i));
                    return lst;
                } catch (NumberFormatException e) {
                    throw new InvalidDoneException("\u2639" + " OOPS!!! The task to be marked as done is not "
                            + "specified by a valid number.");
                }
            }
        } else if ((s.length() >= 4) && (s.substring(0, 4).equals("todo"))) {
            if (s.length() <= 5) {
                throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The description of a todo cannot "
                        + "be empty.");
            } else {
                lst.add("Add");
                lst.add("ToDo");
                lst.add(s.substring(5));
                return lst;
            }
        } else if ((s.length() >= 8) && (s.substring(0, 8).equals("deadline"))) {
            if (s.length() <= 9) {
                throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The deadline is lacking a "
                        + "description/date.");
            } else {
                String[] arr = s.substring(9).split(" /by ");
                if (arr.length < 2) {
                    throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The deadline is lacking a "
                            + "description/date.");
                } else {
                    lst.add("Add");
                    lst.add("Deadline");
                    lst.add(arr[0]);
                    lst.add(parseDate(arr[1]));
                    return lst;
                }
            }
        } else if ((s.length() >= 5) && (s.substring(0, 5).equals("event"))) {
            if (s.length() <= 6) {
                throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The event is lacking a "
                        + "description/date.");
            } else {
                String[] arr = s.substring(6).split(" /at ");
                if (arr.length < 2) {
                    throw new InvalidTaskArgumentException("\u2639" + " OOPS!!! The event is lacking a "
                            + "description/date.");
                } else {
                    lst.add("Add");
                    lst.add("Event");
                    lst.add(arr[0]);
                    lst.add(parseDate(arr[1]));
                    return lst;
                }
            }
        } else if ((s.length() >= 6) && (s.substring(0, 6).equals("delete"))) {
            if (s.length() <= 7) {
                throw new InvalidDeleteException("\u2639" + "OOPS!!! The tasks to be deleted are not "
                        + "specified.");
            } else if (s.substring(7).equals("all")) {
                lst.add("Delete");
                lst.add("All");
                return lst;
            } else {
                try {
                    lst.add("Delete");
                    String[] numbers = s.substring(7).split(" ");
                    for (int i = 0; i < numbers.length; i++) {
                        int num = Integer.parseInt(numbers[i]) - 1;
                        if ((num < 0) || (num >= size)) {
                            throw new InvalidDeleteException("\u2639" + " OOPS!!! There is a number specified "
                                    + "that does not represent a valid task.");
                        }
                        lst.add(Integer.toString(num));
                    }
                    return lst;
                } catch (NumberFormatException e) {
                    throw new InvalidDeleteException("\u2639" + " OOPS!!! There is a task to be deleted "
                            + " that is not specified by a valid number.");
                }
            }
        } else if ((s.length() >= 4) && (s.substring(0, 4).equals("find"))) {
            lst.add("Find");
            lst.add(s.split(" ")[1]);
            return lst;
        } else {
            throw new InvalidCommandException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
