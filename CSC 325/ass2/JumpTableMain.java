import java.util.HashMap;
import java.util.Stack;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// states
enum State{
    IDLE,
    QUEUE,
    STACK,
    LIST,
}
// the interfaces
interface StateEnterExitMeth {
    public void invoke();
}
interface StateStayMeth {
    public boolean invoke();
}

// the Screen class where everything will take place
class Screen
{
    private HashMap<State, StateEnterExitMeth> stateEnterMethods;
    private HashMap<State, StateEnterExitMeth> stateExitMethods;
    private HashMap<State, StateStayMeth> stateStayMethods;

    private Stack<Character> stack;
    private Queue<Character> queue;
    private ArrayList<Character> list;

    private State curr;
    private Scanner input;

    public Screen()
    {
        // initialize the hashmaps
        stateEnterMethods = new HashMap<State, StateEnterExitMeth>();
        stateExitMethods = new HashMap<State, StateEnterExitMeth>();
        stateStayMethods = new HashMap<State, StateStayMeth>();

        // initialize the data structures
        stack = new Stack<Character>();
        queue = new LinkedList<Character>();
        list = new ArrayList<Character>();

        // set up scanner
        input = new Scanner(System.in);

        /// add in the enter methods
        stateEnterMethods.put(State.IDLE, this::EnterIdle);
        stateEnterMethods.put(State.STACK, this::EnterStack);
        stateEnterMethods.put(State.QUEUE, this::EnterQueue);
        stateEnterMethods.put(State.LIST, this::EnterList);

        /// add in the stay methods
        stateStayMethods.put(State.IDLE, this::StayIdle);
        stateStayMethods.put(State.STACK, this::StayStack);
        stateStayMethods.put(State.QUEUE, this::StayQueue);
        stateStayMethods.put(State.LIST, this::StayList);

        /// add in the exit methods
        stateExitMethods.put(State.IDLE, this::ExitIdle);
        stateExitMethods.put(State.STACK, this::ExitStack);
        stateExitMethods.put(State.QUEUE, this::ExitQueue);
        stateExitMethods.put(State.LIST, this::ExitList);

        // set up the curr state
        curr = State.IDLE;
        stateEnterMethods.get(curr).invoke();
    }   

    /// ENTER METHODS
    private void EnterIdle() {}
    private void EnterStack() // read the stack file, print it
    {  
        ///// empty the current stack and fill it with data from the file
        stack.clear();
        try (Scanner readFile = new Scanner(new File("stack.txt"))) {
            readFile.useDelimiter(",");
            while(readFile.hasNext()) {
                String hold = readFile.next().trim();
                if (!hold.isEmpty()) {
                    stack.push(hold.charAt(0));
                }
            }
        } catch (IOException e) {
            System.out.println("File could not be read.");
        }

        ///// now that the stack has been read, print it to the screen
        printStack();


    }
    private void EnterQueue() // read the queue file, print it
    {
         ///// empty the current queue and fill it with data from the file
         queue.clear();
         try (Scanner readFile = new Scanner(new File("queue.txt"))) {
             readFile.useDelimiter(",");
             while(readFile.hasNext()) {
                 String hold = readFile.next().trim();
                 if (!hold.isEmpty()) {
                     queue.add(hold.charAt(0));
                 }
             }
         } catch (IOException e) {
             System.out.println("File could not be read.");
         }
 
         ///// now that the queue has been read, print it to the screen
         printQueue();
    }
    private void EnterList() // read the list file, print it
    {
         ///// empty the current stack and fill it with data from the file
         list.clear();
         try (Scanner readFile = new Scanner(new File("list.txt"))) {
             readFile.useDelimiter(",");
             while(readFile.hasNext()) {
                 String hold = readFile.next().trim();
                 if (!hold.isEmpty()) {
                     list.add(hold.charAt(0));
                 }
             }
         } catch (IOException e) {
             System.out.println("File could not be read.");
         }
 
         ///// now that the list has been read, print it to the screen
         printList();
    }
    
    /// STAY METHODS
    private boolean StayIdle() // print out basic program options -> get input and do input
    {
        while (true) 
        {
            EnterIdle();
            System.out.println("1. Stack");
            System.out.println("2. Queue");
            System.out.println("3. List");
            System.out.println("4. Quit");
            System.out.print("? ");
            
            String command = input.nextLine().trim();
            switch(command) {
                case "1":
                    changeState(State.STACK);
                    return true;
                case "2":
                    changeState(State.QUEUE);
                    return true;
                case "3":
                    changeState(State.LIST);
                    return true;
                case "4":
                    return false;
                default:
                    System.out.println("Input invalid");
                    break;
            }
        }
    }
    private boolean StayStack() // wait for input and then do input
    {
        while(true)
        {
            System.out.print("? ");
            String temp = input.nextLine().trim();
            
            String[] inputs = temp.split(" ");
            String command = inputs[0];
            switch(command) {
                case "1": // PUSH
                    char val = inputs[1].charAt(0);
                    stack.push(val);
                    printStack();
                    break;
                case "2": // POP
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                    printStack();
                    break;
                case "3": // SAVE -> QUEUE
                    changeState(State.QUEUE);
                    return true;
                case "4": // SAVE -> LIST
                    changeState(State.LIST);
                    return true;
                case "5": // QUIT PROGRAM
                    ExitStack();
                    return false;
                default: // WRONG INPUT
                    System.out.println("Input invalid");
                    break;
            }

        }
    }
    private boolean StayQueue() // wait for input and then do input
    {
        while(true)
        {
            System.out.print("? ");
            String temp = input.nextLine().trim();
            
            String[] inputs = temp.split(" ");
            String command = inputs[0];
            switch(command) {
                case "1": // ENQUEUE
                    char val = inputs[1].charAt(0);
                    queue.add(val);
                    printQueue();
                    break;
                case "2": // DEQUEUE
                    if (!queue.isEmpty()) {
                        queue.poll();
                    }
                    printQueue();
                    break;
                case "3": // SAVE -> STACK
                    changeState(State.STACK);
                    return true;
                case "4": // SAVE -> LIST
                    changeState(State.LIST);
                    return true;
                case "5": // QUIT PROGRAM
                    ExitQueue();
                    return false;
                default: // WRONG INPUT
                    System.out.println("Input invalid");
                    break;
            }

        }
    }
    private boolean StayList() // wait for input and then do input
    {
        while(true)
        {
            System.out.print("? ");
            String temp = input.nextLine().trim();
            
            String[] inputs = temp.split(" ");
            String command = inputs[0];
            switch(command) {
                case "1": // APPEND
                    char val = inputs[1].charAt(0);
                    list.add(val);
                    printList();
                    break;
                case "2": // REMOVE
                    if (!list.isEmpty()) {
                        list.remove(list.size() - 1);
                    }
                    printList();
                    break;
                case "3": // SAVE -> STACK
                    changeState(State.STACK);
                    return true;
                case "4": // SAVE -> QUEUE
                    changeState(State.QUEUE);
                    return true;
                case "5": // QUIT PROGRAM
                    ExitList();
                    return false;
                default: // WRONG INPUT
                    System.out.println("Input invalid");
                    break;
            }

        }
    }
    
    /// EXIT METHODS
    private void ExitIdle() {}
    private void ExitStack() // write to the stack file
    {
        // write contents to the file
        try (FileWriter writeFile = new FileWriter("stack.txt")) {
            for (char val : stack) {
                writeFile.write(val + ", ");
            }
        } catch (IOException e) {
            System.out.println("File could not be written.");
        }
    }
    private void ExitQueue() // write to the queue file
    {
        // write contents to the file
        try (FileWriter writeFile = new FileWriter("queue.txt")) {
            for (char val : queue) {
                writeFile.write(val + ", ");
            }
        } catch (IOException e) {
            System.out.println("File could not be written.");
        }
    }
    private void ExitList() // write to the list file
    {
        // write contents to the file
        try (FileWriter writeFile = new FileWriter("list.txt")) {
            for (char val : list) {
                writeFile.write(val + ", ");
            }
        } catch (IOException e) {
            System.out.println("File could not be written.");
        }
    }

    /// IMPORTANT CLASS METHODS
    private void changeState(State newState) // call exit function, switch curr state, call enter function
    {
        stateExitMethods.get(curr).invoke();
        curr = newState;
        stateEnterMethods.get(curr).invoke();
    }
    public boolean doState() {
        return stateStayMethods.get(curr).invoke();
    }


    /// OTHER METHODS I NEED (as to not type the same code over and over again; print methods and the like)
    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // found this ^^ online via stackOverflow
    }

    private void printStack() {
        clear();
        if (stack.isEmpty()){
            System.out.println("|   |");
            System.out.println("|---|");
        }
        else {
            Stack<Character> hold = (Stack<Character>) stack.clone();
            while (!hold.isEmpty()) {
                char val = hold.pop();
                System.out.println("| " + val + " |");
                System.out.println("|---|");
            }
        }

        // print out option list
        System.out.println("1. Push <char>");
        System.out.println("2. Pop");
        System.out.println("3. Save & Move to Queue");
        System.out.println("4. Save & Move to List");
        System.out.println("5. Quit");
    }
    private void printQueue() {
        clear();
        if (queue.isEmpty()) {
            System.out.println("|   |");
        }
        else {
            System.out.print("| ");
            for (char val : queue) {
                    System.out.print(val + " | ");
            }
            
            System.out.println();
        }

        // print out option list
        System.out.println("1. Enqueue <char>");
        System.out.println("2. Dequeue");
        System.out.println("3. Save & Move to Stack");
        System.out.println("4. Save & Move to List");
        System.out.println("5. Quit");
    }
    private void printList() {
        clear();
        if (list.isEmpty()){
            System.out.println("{   }");
        }
        else {
            System.out.print("{ ");
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i));
                if (i < list.size() - 1) {
                    System.out.print(", ");
                }
            }

            System.out.println(" }");
        }

        // print out option list
        System.out.println("1. Append <char>");
        System.out.println("2. Remove");
        System.out.println("3. Save & Move to Stack");
        System.out.println("4. Save & Move to Queue");
        System.out.println("5. Quit");
    }
}

public class JumpTableMain { 
    public static void main(String[] args) { 
      Screen screen = new Screen(); 
      boolean keepRunning = true; 
      while(keepRunning) { 
        keepRunning = screen.doState(); 
      } 
    } 
  }