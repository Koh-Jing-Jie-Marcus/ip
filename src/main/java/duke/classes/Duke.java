package duke.classes;

import java.util.Objects;
import java.util.Scanner;

public class Duke {
    private static void checkError(String input) throws DukeException {
        if (Objects.equals(input, "event") || Objects.equals(input, "todo") || Objects.equals(input, "deadline")) {
            throw new DukeException("The description of the body cannot be empty! Please enter a proper input.");
        } else if (Objects.equals(input, "")) {
            throw new DukeException("You did not enter any input! Please enter a proper input.");
        } else {
            throw new DukeException("I'm sorry, but i don't know what that means. Please enter a proper input.");
        }
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println("Hello from\n" + logo);
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you\n");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        boolean isBye = false;
        Task[] inputs = new Task[100];
        for (int k = 0; k < 100; k++) {
            inputs[k] = new Task("");
        }

        int count = 0;
        int number = 1;

        while (!isBye) {

            if (Objects.equals(input, "bye")) {
                isBye = true;
                break;

            } else if (Objects.equals(input, "list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println(number + "." + inputs[i]);
                    number++;
                }
                number = 1;

            } else if (input.length() > 5 && (input.substring(0,5)).equals("mark ") && input.substring(5, input.length()).matches("[0-9]+")) {
                    Integer order = Integer.valueOf(input.substring(5, input.length()));
                    inputs[order - 1].markDone();
                    System.out.println("Nice! I've marked this task as done:\n" + inputs[order - 1]);

            } else if (input.length() > 7 && (input.substring(0,7)).equals("unmark ") && input.substring(7, input.length()).matches("[0-9]+")) {
                    Integer order = Integer.valueOf(input.substring(7, input.length()));
                    inputs[order - 1].markUndone();
                    System.out.println("OK, I've marked this task as not done yet:\n" + inputs[order - 1]);

            } else if (Objects.equals(input, "event") || Objects.equals(input, "todo") || Objects.equals(input, "deadline")) {
                try {
                    checkError(input);
                } catch(DukeException e) {
                    System.out.println("Error: " + e);
                }
            } else {
                if (input.length() > 3 && input.substring(0,4).equals("todo")) {
                    String info = input.substring(5,input.length());
                    inputs[count] = new Todo(info);
                    inputs[count].isDone = false;
                    System.out.println("Got it. I've added this task: \n" + inputs[count] + "\nNow you have " + (count + 1) + " tasks in your list." );
                } else if (input.length() > 7 && input.substring(0,8).equals("deadline")) {
                    String info = input.substring(9,input.indexOf("/"));
                    String timeBy = input.substring(input.indexOf("/")+1, input.length());
                    inputs[count] = new Deadline(info, timeBy);
                    inputs[count].isDone = false;
                    System.out.println("Got it. I've added this task: \n" + inputs[count] + "\nNow you have " + (count + 1) + " tasks in your list." );
                } else if (input.length() > 4 && input.substring(0,5).equals("event")) {
                    String info = input.substring(6,input.indexOf("/"));
                    String timeFrom = input.substring(input.indexOf("/")+1, input.lastIndexOf("/") - 1);
                    String timeBy = input.substring(input.lastIndexOf("/")+1, input.length());
                    inputs[count] = new Event(info, timeFrom, timeBy);
                    inputs[count].isDone = false;
                    System.out.println("Got it. I've added this task: \n" + inputs[count] + "\nNow you have " + (count + 1) + " tasks in your list." );
                } else {
                    try {
                        checkError(input);
                    } catch(DukeException e) {
                        System.out.println("Error: " + e);
                    }
                }
                count++;
            }
            input = scan.nextLine();
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}