import java.io.*;
import java.util.*;

public class Round_Trip {
    public static List<Integer> result = new ArrayList<>();
    public static boolean found = false;
    public static int cycleStart = -1, cycleEnd = -1;

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

        boolean[] visited = new boolean[n + 1];
        int[] parent = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                if (dfs(graph, visited, parent, i, -1)) {
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("IMPOSSIBLE");
        } else {
            List<Integer> path = new ArrayList<>();
            path.add(cycleStart);
            for (int v = cycleEnd; v != cycleStart; v = parent[v]) {
                path.add(v);
            }
            path.add(cycleStart);
            Collections.reverse(path);

            System.out.println(path.size());
            for (int v : path) {
                System.out.print(v + " ");
            }
        }
    }

    public static boolean dfs(ArrayList<ArrayList<Integer>> graph, boolean[] visited, int[] parent, int v, int par) {
        visited[v] = true;
        parent[v] = par;

        for (int to : graph.get(v)) {
            if (to == par) continue;

            if (visited[to]) {
                cycleStart = to;
                cycleEnd = v;
                found = true;
                return true;
            } else {
                if (dfs(graph, visited, parent, to, v))
                    return true;
            }
        }
        return false;
    }
}
