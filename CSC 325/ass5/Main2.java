import java.io.*;
import java.util.*;

public class Main2 {
    private static final int MAX_VERTS = 9;
    private static final int NO_EDGE = 0;

    public static void main(String[] args) 
    {
        try {
            ///// get input from files and then build matrix
            int[][] adjMatrix = read("input.txt", false);
            int[][] adjMatrixWeights = read("input_weights.txt", true);
            
            ///// do BFS and DFS
            List<Character> bfRes = bfsearch(adjMatrix, 0);
            List<Character> dfRes = dfs(adjMatrix, 0);
            
            ///// do dijkstra's
            Map<Character, Integer> dijRes = dijkstra(adjMatrixWeights, 0);
            
            ///// write to file
            write("output.txt", adjMatrix, bfRes, dfRes, adjMatrixWeights, dijRes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] read(String filename, boolean weighted) throws IOException 
    {
        int[][] tempM = new int[MAX_VERTS][MAX_VERTS];
        BufferedReader br = new BufferedReader(new FileReader(filename));
        System.out.println("Reading file: " + filename);
        String hold;
    
        while ((hold = br.readLine()) != null) 
        {
            if (hold.trim().isEmpty()) continue; // skip empty
            // System.out.println("Processing line: " + hold); // debug
    
            String[] parts = hold.split(":");
            int from = parts[0].charAt(0) - 'a';
    
            if (parts.length > 1) 
            {
                String[] edges = parts[1].split(",");
                
                for (String edge : edges) {
                    if (weighted) {
                        String[] edgeParts = edge.split("_");
                        int to = edgeParts[0].charAt(0) - 'a';
                        int weight = Integer.parseInt(edgeParts[1]);
                        tempM[from][to] = weight;
                        // System.out.printf("adding edge"); // debug
                    } else {
                        int to = edge.charAt(0) - 'a';
                        tempM[from][to] = 1;
                        tempM[to][from] = 1; // Undirected graph
                        // System.out.printf("adding edge"); // debug
                    }
                }
            }
        }
    
        br.close();
        //System.out.println("read file: " + filename);
    
        /* // debug - io verification
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < MAX_VERTS; i++) {
            for (int j = 0; j < MAX_VERTS; j++) {
                System.out.print(tempM[i][j] + " ");
            }
            System.out.println();
        } */
        return tempM;
    }        

    private static List<Character> bfsearch(int[][] matrix, int start) 
    {
        boolean[] visited = new boolean[MAX_VERTS];
        Queue<Integer> hold = new LinkedList<>();
        List<Character> result = new ArrayList<>();

        hold.add(start);
        visited[start] = true;

        while (!hold.isEmpty()) {
            int vertex = hold.poll();
            result.add((char) ('a' + vertex));

            for (int i = 0; i < MAX_VERTS; i++) {
                if (matrix[vertex][i] == 1 && !visited[i]) {
                    hold.add(i);
                    visited[i] = true;
                }
            }
        }

        return result;
    }

    private static List<Character> dfs(int[][] matrix, int start) {
        boolean[] visited = new boolean[MAX_VERTS];
        Stack<Integer> hold = new Stack<>();
        List<Character> result = new ArrayList<>();

        hold.push(start);

        while (!hold.isEmpty()) {
            int vertex = hold.pop();

            if (!visited[vertex]) {
                visited[vertex] = true;
                result.add((char) ('a' + vertex));

                for (int i = MAX_VERTS - 1; i >= 0; i--) {
                    if (matrix[vertex][i] == 1 && !visited[i]) {
                        hold.push(i);
                    }
                }
            }
        }

        return result;
    }

    private static Map<Character, Integer> dijkstra(int[][] matrix, int start) {
        int[] dist = new int[MAX_VERTS];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<int[]> hold = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        hold.add(new int[]{start, 0});

        while (!hold.isEmpty()) {
            int[] curr = hold.poll();
            int u = curr[0];
            int distance = curr[1];

            if (distance > dist[u]) continue;

            for (int v = 0; v < MAX_VERTS; v++) {
                if (matrix[u][v] != NO_EDGE) {
                    int newDist = dist[u] + matrix[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        hold.add(new int[]{v, newDist});
                    }
                }
            }
        }

        Map<Character, Integer> result = new LinkedHashMap<>();
        for (int i = 0; i < MAX_VERTS; i++) {
            result.put((char) ('a' + i), dist[i]);
        }

        return result;
    }

    private static void write(String filename, int[][] adjMatrix, List<Character> bfsResult,
                              List<Character> dfsResult, int[][] adjMatrixWeights,
                              Map<Character, Integer> dijkstraResult) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        // Write undirected adjacency matrix
        writer.write("Adjacency Matrix (undirected):\n");
        writer.write(" abcdefghi\n");
        for (int i = 0; i < MAX_VERTS; i++) {
            writer.write((char) ('a' + i) + " ");
            for (int j = 0; j < MAX_VERTS; j++) {
                writer.write(adjMatrix[i][j] + "");
            }
            writer.write("\n");
        }

        // Write BFS and DFS results
        writer.write("BFS: ");
        for (char c : bfsResult) writer.write(c);
        writer.write("\n");

        writer.write("DFS: ");
        for (char c : dfsResult) writer.write(c);
        writer.write("\n");

        // Write directed adjacency matrix with weights
        writer.write("Adjacency Matrix (directed w/weights):\n");
        writer.write(" abcdefghi\n");
        for (int i = 0; i < MAX_VERTS; i++) {
            writer.write((char) ('a' + i) + " ");
            for (int j = 0; j < MAX_VERTS; j++) {
                writer.write(adjMatrixWeights[i][j] + "");
            }
            writer.write("\n");
        }

        // Write Dijkstra’s results
        writer.write("Dijkstra’s: ");
        for (Map.Entry<Character, Integer> entry : dijkstraResult.entrySet()) {
            writer.write(entry.getKey() + ":" + (entry.getValue() == Integer.MAX_VALUE ? "∞" : entry.getValue()) + ",");
        }
        writer.write("\n");

        writer.close();
        System.out.println("Output successfully written to " + filename);
    }
}