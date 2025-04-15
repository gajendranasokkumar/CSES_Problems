import java.io.*;
import java.util.*;

public class Shortest_Routes_I {
    static class Node {
        int to;
        long weight;
        Node(int to, long weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // Adjacency list
        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // Reading edges
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph.get(a).add(new Node(b, c));
        }

        // Dijkstra
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.weight));
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            if (curr.weight > dist[curr.to]) continue;

            for (Node nei : graph.get(curr.to)) {
                if (dist[nei.to] > dist[curr.to] + nei.weight) {
                    dist[nei.to] = dist[curr.to] + nei.weight;
                    pq.add(new Node(nei.to, dist[nei.to]));
                }
            }
        }

        // Output
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 1; i <= n; i++) {
            bw.write(dist[i] + " ");
        }
        bw.flush();
    }
}
