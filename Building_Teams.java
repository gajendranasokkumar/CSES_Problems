import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Building_Teams {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        int[] team = new int[n + 1]; // 0 = unassigned

        for (int i = 1; i <= n; i++) {
            if (team[i] == 0) {
                if (!bfs(i, graph, team)) {
                    System.out.println("IMPOSSIBLE");
                    return;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(team[i]).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    static boolean bfs(int start, List<List<Integer>> graph, int[] team) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        team[start] = 1;

        while (!q.isEmpty()) {
            int node = q.poll();
            for (int nei : graph.get(node)) {
                if (team[nei] == 0) {
                    team[nei] = 3 - team[node]; // Switch team
                    q.offer(nei);
                } else if (team[nei] == team[node]) {
                    return false; // Same team on both sides → not bipartite
                }
            }
        }

        return true;
    }
}
