import java.io.*;
import java.util.*;

public class Flight_Discount {
    static BufferedReader br;
    static PrintWriter out;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(new OutputStreamWriter(System.out));

        int n = readInt(), m = readInt();

        List<List<long[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int a = readInt(), b = readInt(), c = readInt();
            graph.get(a).add(new long[]{b, c});
        }

        long[][] dist = new long[n + 1][2];
        for (int i = 1; i <= n; i++) Arrays.fill(dist[i], Long.MAX_VALUE);
        dist[1][0] = 0;

        // PQ entry: [node, currentCost, couponUsed]
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        pq.add(new long[]{1, 0, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[0];
            long costSoFar = cur[1];
            int used = (int) cur[2];

            if (costSoFar > dist[u][used]) continue;

            for (long[] edge : graph.get(u)) {
                int v = (int) edge[0];
                long weight = edge[1];

                // Without using the discount
                if (dist[v][used] > costSoFar + weight) {
                    dist[v][used] = costSoFar + weight;
                    pq.add(new long[]{v, dist[v][used], used});
                }

                // With using the discount (only if not used yet)
                if (used == 0) {
                    long discounted = weight / 2;
                    if (dist[v][1] > costSoFar + discounted) {
                        dist[v][1] = costSoFar + discounted;
                        pq.add(new long[]{v, dist[v][1], 1});
                    }
                }
            }
        }

        out.println(dist[n][1]);
        out.flush();
        out.close();
    }

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine().trim());
        return st.nextToken();
    }

    static int readInt() throws IOException {
        return Integer.parseInt(next());
    }
}
