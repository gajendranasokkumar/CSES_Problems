import java.io.*;
import java.util.*;

public class Monsters {

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static char[] dirChar = {'U', 'D', 'L', 'R'};

    static class Cell {
        int x, y;
        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] grid = new char[n][m];
        int[][] monsterTime = new int[n][m];
        for (int[] row : monsterTime)
            Arrays.fill(row, Integer.MAX_VALUE);

        Queue<Cell> monsterQueue = new LinkedList<>();
        Cell start = null;

        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = row.charAt(j);
                if (grid[i][j] == 'M') {
                    monsterQueue.add(new Cell(i, j));
                    monsterTime[i][j] = 0;
                } else if (grid[i][j] == 'A') {
                    start = new Cell(i, j);
                }
            }
        }

        // Multi-source BFS for monsters
        while (!monsterQueue.isEmpty()) {
            Cell curr = monsterQueue.poll();
            for (int d = 0; d < 4; d++) {
                int nx = curr.x + dx[d];
                int ny = curr.y + dy[d];
                if (nx >= 0 && ny >= 0 && nx < n && ny < m && grid[nx][ny] != '#' &&
                        monsterTime[nx][ny] > monsterTime[curr.x][curr.y] + 1) {
                    monsterTime[nx][ny] = monsterTime[curr.x][curr.y] + 1;
                    monsterQueue.add(new Cell(nx, ny));
                }
            }
        }

        // BFS for player
        Queue<Cell> queue = new LinkedList<>();
        int[][] playerTime = new int[n][m];
        char[][] fromDir = new char[n][m];
        Cell[][] parent = new Cell[n][m];
        boolean[][] visited = new boolean[n][m];

        queue.add(start);
        visited[start.x][start.y] = true;
        playerTime[start.x][start.y] = 0;

        Cell escape = null;

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();

            if (curr.x == 0 || curr.x == n - 1 || curr.y == 0 || curr.y == m - 1) {
                escape = curr;
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nx = curr.x + dx[d];
                int ny = curr.y + dy[d];
                if (nx >= 0 && ny >= 0 && nx < n && ny < m &&
                        !visited[nx][ny] && grid[nx][ny] != '#' &&
                        playerTime[curr.x][curr.y] + 1 < monsterTime[nx][ny]) {
                    visited[nx][ny] = true;
                    playerTime[nx][ny] = playerTime[curr.x][curr.y] + 1;
                    fromDir[nx][ny] = dirChar[d];
                    parent[nx][ny] = curr;
                    queue.add(new Cell(nx, ny));
                }
            }
        }

        if (escape == null) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            StringBuilder path = new StringBuilder();
            Cell curr = escape;
            while (!curr.equals(start)) {
                Cell prev = parent[curr.x][curr.y];
                for (int d = 0; d < 4; d++) {
                    if (prev.x + dx[d] == curr.x && prev.y + dy[d] == curr.y) {
                        path.append(dirChar[d]);
                        break;
                    }
                }
                curr = prev;
            }
            path.reverse();
            System.out.println(path.length());
            System.out.println(path);
        }
    }
}
