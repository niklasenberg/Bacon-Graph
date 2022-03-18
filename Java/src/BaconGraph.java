import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Implements graph structure used to calculate <a href="https://en.wikipedia.org/wiki/Six_Degrees_of_Kevin_Bacon">Bacon</a>
 * numbers and get paths between different actors. Uses an adjacency list (HashMap) to build the graph.
 *
 * @author Niklas Enberg
 */
public class BaconGraph {

    /**
     * The graphs underlying data structure which implements a <a href="https://en.wikipedia.org/wiki/Adjacency_list">Adjacency list</a>
     */
    private final HashMap<String, ArrayList<String>> industryMap = new HashMap<>();

    /**
     * Checks whether graph contains a certain actor or movie.
     * @param key the actor or movie to be checked for
     * @return true/false whether graph contains node(actor or movie)
     */
    public boolean contains(String key){
        return industryMap.containsKey(key);
    }

    /**
     * Checks how many actors or movies are contained within the graph. Only used for testing purposes.
     * @param nodeType The type of node to be counted ('a' for actor / 't' for movie)
     * @return the amount of nodes in the graph, with the asked type.
     */
    public long countNodes(char nodeType){
        long count = 0;
        for(String s : industryMap.keySet()){
            if(s.charAt(1) == nodeType){
                count++;
            }
        }
        return count;
    }

    /**
     * Establishes graph from external text file containing actors and movies, which can be considered as nodes.
     * @param fileName the filepath for the external text file containing all nodes.
     * @throws IOException when FileReader or BufferedReader can't read the correct file
     */
    public void readFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = bufferedReader.readLine();
        String currentActor = null;
        while(line != null){
            if(line.charAt(1) == 'a'){
                currentActor = line;
                industryMap.put(currentActor, new ArrayList<>());
            } else{
                industryMap.putIfAbsent(line, new ArrayList<>());
                industryMap.get(line).add(currentActor);
                industryMap.get(currentActor).add(line);
            }
            line = bufferedReader.readLine();
        }

        fileReader.close();
        bufferedReader.close();
    }

    /**
     * Traverses the graph by <a href="https://en.wikipedia.org/wiki/Breadth-first_search">BFS</a>
     * from the given startnode to the given endnode, establishing how every seen node has been reached
     * via a Map. Uses this Map to establish a path between the two given nodes via
     * {@link BaconGraph#getPath(String, String, Map)}.
     * @param start the node/actor to establish Bacon-number for
     * @param end the destination node/actor, which by default is Kevin Bacon
     * @return the list of nodes (actors/movies) for the shortest path between the given actors.
     * Is empty if no path can be established
     */
    public List<String> breadthFirstSearch(String start, String end) {
        HashSet<String> seen = new HashSet<>();
        Map<String,String> fromMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        String current = queue.poll();
        seen.add(current);
        try{
            while(!current.equals(end)){
                for(String node : industryMap.get(current)){
                    if(!seen.contains(node)){
                        fromMap.putIfAbsent(node, current);
                        queue.add(node);
                        seen.add(node);
                    }
                }
                current = queue.poll();
            }
        }catch (NullPointerException e){
            System.out.println("No path found between "+start.substring(3)+" and "+end.substring(3)+".");
        }
        return getPath(start, end, fromMap);
    }

    /**
     * Gathers a list of nodes from the previously established Map in {@link BaconGraph#breadthFirstSearch(String, String)}.
     * @param start the node or actor to establish Bacon-number for
     * @param end the destination node, which by default is Kevin Bacon
     * @param fromMap the previously established index for how each node is reached
     * @return the list of nodes (actors/movies) for the shortest path between the given actors
     */
    private List<String> getPath(String start, String end, Map<String, String> fromMap) {
        ArrayList<String> path = new ArrayList<>();
        String current = end;

        if (start.equals(end)) {
            path.add(current);
        } else {
            path.add(current);
            while (!current.equals(start)) {
                String next = fromMap.get(current);
                path.add(next);
                current = next;
            }
        }
        return path;
    }
}
