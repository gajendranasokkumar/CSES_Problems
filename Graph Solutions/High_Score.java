import java.util.*;

public class High_Score {
    static class Edge {
        int a, b;
        long c;
        Edge(int a, int b, long c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    static final int MAX_N = 2501;
    static final int MAX_M = 5001;
    static final long INF = Long.MAX_VALUE / 2;

    static int N, M;
    static long[] dp = new long[MAX_N];
    static boolean[] vis = new boolean[MAX_N];
    static boolean[] visR = new boolean[MAX_N];
    @SuppressWarnings("unchecked")
    static List<Integer>[] G = new ArrayList[MAX_N];
    @SuppressWarnings("unchecked")
    static List<Integer>[] GR = new ArrayList[MAX_N];
    static Edge[] edges = new Edge[MAX_M];

    static void dfs(int u, boolean[] visited, List<Integer>[] graph) {
        visited[u] = true;
        for (int v : graph[u]) {
            if (!visited[v]) {
                dfs(v, visited, graph);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        for (int i = 0; i <= N; i++) {
            G[i] = new ArrayList<>();
            GR[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            long c = sc.nextLong();
            edges[i] = new Edge(a, b, -c);
            G[a].add(b);
            GR[b].add(a);
        }
        sc.close();

        dfs(1, vis, G);
        dfs(N, visR, GR);

        Arrays.fill(dp, 2, N + 1, INF);
        dp[1] = 0;
        boolean improvement = true;

        for (int iter = 0; iter < N && improvement; iter++) {
            improvement = false;
            for (int i = 0; i < M; i++) {
                int u = edges[i].a;
                int v = edges[i].b;
                long w = edges[i].c;

                if (dp[u] != INF && dp[v] > dp[u] + w) {
                    dp[v] = dp[u] + w;
                    improvement = true;

                    if (iter == N - 1 && vis[v] && visR[v]) {
                        System.out.println("-1");
                        return;
                    }
                }
            }
        }

        System.out.println(-dp[N]);
    }
}
