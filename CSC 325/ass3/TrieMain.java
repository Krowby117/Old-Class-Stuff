import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;



//// RAN INTO ISSUE WITH THE DISPLAY CLASS; COULD NOT GET IT TO WORK BUT I KEPT THE LINES OF CODE AND COMMENTED THEM OUT


class Node {
    private Node[] children;
    private boolean isTerminal;

    public Node() {
        children = new Node[128];
        isTerminal = false;
    }

    // GETTER METHODS
    public Node[] getChildren() {
        return children;
    }
    public boolean getIsTerminal() {
        return isTerminal;
    }

    // SETTER METHODS
    public void setChild(int index, Node node) {
        children[index] = node;
    }
    public void setIsTerminal(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }
}

class Trie {
    private Node root;
    private final int ALPHABET_SIZE = 128;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) // INSERT ITEM
    {
        Node hold = root;
        for (char c : word.toCharArray()) {
            int i = (int) c;
            if (hold.getChildren()[i] == null) {
                hold.setChild(i, new Node());
            }
            hold = hold.getChildren()[i];
        }
        hold.setIsTerminal(true);
    }

    public boolean search(String word) // FIND ITEM
    {
        Node hold = root;
        for (char c : word.toCharArray()) {
            int i = (int) c;
            if (hold.getChildren()[i] == null) {
                return false;
            }
            hold = hold.getChildren()[i];
        }
        return hold.getIsTerminal();
    }

    public void printAllWords() // MAIN PRINT ALL METHOD
    {
        List<String> val = new ArrayList<>();
        printAllWordsRecursive(root, "", val);
        System.out.println(String.join(", ", val));
    }

    private void printAllWordsRecursive(Node node, String curr, List<String> val) // SPECIAL PRINT ALL METHOD JUST INCASE
    {
        if (node.getIsTerminal()) {
            val.add(curr);
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            Node child = node.getChildren()[i];
            if (child != null) {
                printAllWordsRecursive(child, curr + (char) i, val);
            }
        }
    }

    public void printAllWords(String prefix) // PRINT ALL WORDS WITH PREFIX
    {
        Node current = root;
        for (char ch : prefix.toCharArray()) {
            int index = (int) ch;
            if (current.getChildren()[index] == null) {
                System.out.println("No words with the prefix: " + prefix);
                return;
            }
            current = current.getChildren()[index];
        }
        List<String> result = new ArrayList<>();
        printAllWordsRecursive(current, prefix, result);
        System.out.println("Words with prefix '" + prefix + "': " + String.join(", ", result));
    }
}

public class TrieMain {
    public static void main(String[] args) 
    {
        Trie trie = new Trie();
        if (args.length > 0 && args[0].equals("test")) { // enter test mode
            testMode(trie);
        } else if (args.length > 0 && args[0].equals("memuse")) { //  enter memUse mode
            memUseMode();
        }
    }

    public static void testMode(Trie trie) 
    {
        String[] words = { "banana", "bandana", "bandaid", "bandage", "letter", "lettuce", "let", "tool", "toy", "toilet" };
        
        for (String word : words) {
            trie.insert(word);
        }

        Scanner scanner = new Scanner(System.in);
        while (true) 
        {
            System.out.println("{add WORD}: Add a word");
            System.out.println("{printAll}: Print all words");
            System.out.println("{startsWith PREFIX}: Print words that start with");
            System.out.println("{search WORD}: Match a specific word");
            System.out.println("{quit}: Quit");
            System.out.print("? ");
            
            String input = scanner.nextLine().trim();
            String command;
            String argument = "";
        
            // SPLIT UP THE 
            if (input.contains(" ")) {
                int spaceIndex = input.indexOf(' ');
                command = input.substring(0, spaceIndex);
                argument = input.substring(spaceIndex + 1);
            } else {
                command = input;
            }
        
            // COMMAND READING AND DO ACTION
            switch (command) {
                case "add":
                    trie.insert(argument);
                    System.out.println("Added: " + argument);
                    break;
                case "printAll":
                    trie.printAllWords();
                    break;
                case "startsWith":
                    trie.printAllWords(argument);
                    break;
                case "search":
                    if (trie.search(argument)) {
                        System.out.println("word '" + argument + "' was found in the trie");
                    } else {
                        System.out.println("word '" + argument + "' was NOT found in the trie");
                    }
                    break;
                case "quit":
                    System.out.println("Exiting...");
                    return; // Break out of the loop and end the program
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            System.out.print("\033[H\033[2J");
        }
    }      

    public static void memUseMode() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> wordsList = new ArrayList<>();
        Runtime rt = Runtime.getRuntime();
        long heapMemoryInBytes;
        int wordCount = 0;

        //Display.setYMax(900);  
        //Display.setYInc(15);   

        Trie trie = new Trie();
        System.out.println("Adding words to Trie...");
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            wordsList.add(word);
            trie.insert(word);
            wordCount++;

            heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
        }

        trie = null;
        rt.gc();

        HashSet<String> hashSet = new HashSet<>();
        wordCount = 0;
        System.out.println("Adding words to HashSet...");
        for (String word : wordsList) {
            hashSet.add(word);
            wordCount++;

            heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
            //Display.show(hashSet, heapMemoryInBytes, wordCount);
        }

        System.out.println("Memory usage visualization completed.");
    }
}