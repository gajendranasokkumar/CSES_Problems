import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Round_Trip {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            graph.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph.get(from).add(to);
            graph.get(to).add(from);
        }

        boolean ans = isCycle(n+1, graph);

        if (ans) {
            // System.out.println("YES");
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }

    static boolean checkForCycle(ArrayList<ArrayList<Integer>> adj, int s, boolean vis[], int parent[]) {
        Queue<Node> q = new LinkedList<>(); // BFS
        q.add(new Node(s, -1));
        vis[s] = true;

        // until the queue is empty
        while (!q.isEmpty()) {
            // source node and its parent node
            int node = q.peek().first;
            int par = q.peek().second;
            q.remove();

            // go to all the adjacent nodes
            for (Integer it : adj.get(node)) {
                if (!vis[it]) {
                    q.add(new Node(it, node));
                    vis[it] = true;
                    parent[it] = node;
                }

                // if adjacent node is visited and is not its own parent node
                else if (par != it) {
                    // Cycle detected, reconstruct the path
                    ArrayList<Integer> cycle = new ArrayList<>();
                    cycle.add(it);
                    int current = node;
                    while (current != -1 && current != it) { // Ensure valid parent index
                        cycle.add(current);
                        current = parent[current];
                    }
                    cycle.add(it); // Close the cycle
                    System.out.println(cycle.size());
                    // System.out.println("Cycle Path: " + cycle);
                    for(int i : cycle)
                        System.out.print(i + " ");
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean vis[] = new boolean[V];
        Arrays.fill(vis, false);
        int parent[] = new int[V];
        Arrays.fill(parent, -1);

        for (int i = 0; i < V; i++)
            if (vis[i] == false)
                if (checkForCycle(adj, i, vis, parent)) {
                    // System.out.println(Arrays.toString(parent));
                    return true;
                }

        return false;
    }

}

class Node {
    int first;
    int second;

    public Node(int first, int second) {
        this.first = first;
        this.second = second;
    }
}