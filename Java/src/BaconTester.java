import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Class for testing {@link BaconGraph} via Junit. Implements some arbitrary tests.
 *
 * @author Niklas Enberg
 */
public class BaconTester {

    /**
     * Array of random actors from textfile
     */
    private static final String[] actors = {"<a>Connery, Sean", "<a>Trump, Donald J.",
            "<a>Bacon, Kevin (I)", "<a>Garbo, Greta", "<a>De Niro, Robert", "<a>Bergqvist, Kjell"};

    /**
     * The actor to determine the shortest path to. By default <a href="https://en.wikipedia.org/wiki/Kevin_Bacon">Kevin Bacon</a>.
     */
    private static final String actor = "<a>Bacon, Kevin (I)";

    /**
     * Instantiated object of {@link BaconGraph}.
     */
    private final BaconGraph graph = new BaconGraph();

    /**
     * Method for loading data from textfile via {@link BaconGraph#readFile(String)}
     * @throws IOException when FileReader or BufferedReader can't read the correct file
     */
    private void setup() throws IOException {
        graph.readFile("moviedata.txt");
    }

    @Test
    public void testContainsActors() throws IOException {
        /*
        Tests if the random selection of actors in the array exists as nodes within the BaconGraph object
         */
        setup();
        for(String actor : actors){
            Assertions.assertTrue(graph.contains(actor));
        }
    }

    @Test
    public void testCountActorsAndMovies() throws IOException {
        /* Tests the amount of actors and movies in graph */
        setup();
        Assertions.assertEquals(graph.countNodes('a'), 2835629);
        Assertions.assertEquals(graph.countNodes('t'), 811167);
    }


    @Test
    public void testBFS() throws IOException {
        /*
        Tests breadth-first search for actors with Bacon numbers ranging from 0 to 3
         */
        setup();
        Assertions.assertEquals(5, graph.breadthFirstSearch("<a>Connery, Sean", actor).size());
        System.out.println(graph.breadthFirstSearch("<a>Connery, Sean", actor));
        Assertions.assertEquals(5, graph.breadthFirstSearch("<a>Trump, Donald J.", actor).size());
        System.out.println(graph.breadthFirstSearch("<a>Trump, Donald J.", actor));
        Assertions.assertEquals(1, graph.breadthFirstSearch("<a>Bacon, Kevin (I)", actor).size());
        System.out.println(graph.breadthFirstSearch("<a>Bacon, Kevin (I)", actor));
        Assertions.assertEquals(5, graph.breadthFirstSearch("<a>Garbo, Greta", actor).size());
        System.out.println(graph.breadthFirstSearch("<a>Garbo, Greta", actor));
        Assertions.assertEquals(3, graph.breadthFirstSearch("<a>De Niro, Robert", actor).size());
        System.out.println(graph.breadthFirstSearch("<a>De Niro, Robert", actor));
        Assertions.assertEquals(7, graph.breadthFirstSearch("<a>Bergqvist, Kjell", actor).size());
        System.out.println(graph.breadthFirstSearch("<a>Bergqvist, Kjell", actor));
    }

    @Test
    public void testLoadTime() throws IOException {
        /*
        Tests the loadtime of loading the textfile, maximum of two minutes
         */
        long startTimer = System.nanoTime();

        setup();

        long endTimer = System.nanoTime();
        long result = TimeUnit.NANOSECONDS.toSeconds(endTimer - startTimer);
        assertTrue(result < 120);
    }
}
