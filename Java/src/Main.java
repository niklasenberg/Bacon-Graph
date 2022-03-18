import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Main class that handles user input, establishes {@link BaconGraph}, and changes which actor to determine path to.
 * (By default, Kevin Bacon).
 *
 * @author Niklas Enberg
 */
public class Main {
    /**
     * Scanner used to read user inputs
     */
    private final Scanner in = new Scanner(System.in);

    /**
     * Instantiated object of {@link BaconGraph}.
     */
    private final BaconGraph graph = new BaconGraph();

    /**
     * The actor to determine the shortest path to. By default <a href="https://en.wikipedia.org/wiki/Kevin_Bacon">Kevin Bacon</a>.
     */
    private String actor = "<a>Bacon, Kevin (I)";

    /**
     * The main method, which creates an object of {@link Main}. and then calls {@link Main#run()}.
     * @param args The command line arguments.
     * @throws java.io.IOException when FileReader or BufferedReader can't read the correct file
     **/
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.run();
    }

    /**
     * Runs the program by loading the graph with the textfile, using {@link BaconGraph#readFile(String)}.
     * Displays options for the user via {@link Main#printMenu()} and then handles input until user quits program.
     * @throws java.io.IOException when FileReader or BufferedReader can't read the correct file
     **/
    private void run() throws IOException {
        System.out.println("Welcome! Loading actors and movies...");
        long startTimer = System.nanoTime();
        graph.readFile("moviedata.txt");
        long endTimer = System.nanoTime();
        System.out.println("Loading complete. Load time: " + TimeUnit.NANOSECONDS.toMillis(endTimer - startTimer) + " milliseconds.");
        System.out.println();

        boolean running = true;
        while(running){
            try{
                printMenu();
                int command = in.nextInt();
                switch (command) {
                    case 1 -> findActor();
                    case 2 -> changeActor();
                    case 3 -> running = false;
                    default -> System.out.println("Sorry, unknown command. Try again!");
                }
            }catch (InputMismatchException exception){
                System.out.println("Please use numeric values to enter a command.");
                in.next();
            }
        }
        System.out.println("Goodbye!");
        in.close();
    }

    /**
     * Retrieves actor input from user and formats it for use in {@link BaconGraph#breadthFirstSearch(String, String)}.
     * @return the formatted input
     **/
    private String getInput(){
        in.nextLine();
        System.out.println("Enter first name: ");
        String firstName = formatName(in.nextLine());
        System.out.println("Enter last name: ");
        String lastName = formatName(in.nextLine());

        return "<a>"+lastName+", "+firstName;
    }

    /**
     * Changes the actor to be determined the shortest path to. Actor needs to exist in current graph.
     **/
    private void changeActor() {
        String newActor = getInput();
        if(graph.contains(newActor)){
            actor = newActor;
            System.out.println("Actor changed.");
        }else{
            System.out.println("No such actor in the database.");
        }
        System.out.println();
    }

    /**
     * Asks user for a specific actor. Calculates Bacon-number, and prints it along with the path between Kevin Bacon (or other set actor)
     * and this actor.
     **/
    private void findActor() {
        String key = getInput();

        if(graph.contains(key)){
            List<String> path = graph.breadthFirstSearch(key, actor);
            if(!path.isEmpty()){
                StringBuilder sb = new StringBuilder();
                int baconNo = 0;
                for(String node : path){
                    if(node.equals(key)){
                        sb.append(node).append("</a>");
                    } else if(node.charAt(1) == 'a'){
                        sb.append(node).append("<a>");
                        baconNo++;
                    }else{
                        sb.append(node).append("<t>");
                    }
                }

                System.out.println("\""+key.substring(3)+"\""+" is " + baconNo + " steps away from "+actor.substring(3)+".");
                System.out.println("The path is: " + sb);
                System.out.println();
            }
        }else{
            System.out.println("No such actor in the database.");
        }
    }

    /**
     * Formats an already received input to be used as part of a node in {@link Main#getInput()}.
     * @return the formatted name
     **/
    private String formatName(String input) {
        StringBuilder sb = new StringBuilder(input.trim());
        sb.setCharAt(0, Character.toTitleCase(sb.charAt(0)));
        return sb.toString();
    }

    /**
     * Prints the users available options.
     **/
    private void printMenu(){
        System.out.println("Please choose an option:");
        System.out.println("1. Get Bacon-number for specific actor");
        System.out.println("2. Change actor to be searched for");
        System.out.println("3. Quit");
    }
}
