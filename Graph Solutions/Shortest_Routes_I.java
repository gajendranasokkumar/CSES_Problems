import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Shortest_Routes_I {

    static BufferedReader br;
    static PrintWriter out;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(new OutputStreamWriter(System.out));

        int n = readInt();
        int m = readInt();

        // Adjacency list
        List<List<long[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // Reading edges
        for (int i = 0; i < m; i++) {
            int a = readInt();
            int b = readInt();
            int c = readInt();
            graph.get(a).add(new long[]{b, c});
        }

        // Dijkstra
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.add(new long[]{1, 0});

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int node = (int) curr[0];
            long currDist = curr[1];

            if (currDist > dist[node]) continue;

            for (long[] nei : graph.get(node)) {
                int to = (int) nei[0];
                long weight = nei[1];
                if (dist[to] > dist[node] + weight) {
                    dist[to] = dist[node] + weight;
                    pq.add(new long[]{to, dist[to]});
                }
            }
        }

        // Output
        for (int i = 1; i <= n; i++) {
            out.print(dist[i]);
            out.print(" ");
        }
        out.flush();
        out.close();
    }

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine().trim());
        return st.nextToken();
    }

    static long readLong() throws IOException {
        return Long.parseLong(next());
    }

    static int readInt() throws IOException {
        return Integer.parseInt(next());
    }

    static double readDouble() throws IOException {
        return Double.parseDouble(next());
    }

    static char readCharacter() throws IOException {
        return next().charAt(0);
    }

    static String readLine() throws IOException {
        return br.readLine().trim();
    }
}
