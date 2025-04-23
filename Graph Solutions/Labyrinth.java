import java.util.*;

public class Labyrinth {
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static char[] dir = {'D', 'U', 'R', 'L'};

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int rows = input.nextInt();
        int cols = input.nextInt();
        input.nextLine(); 

        char[][] grid = new char[rows][cols];
        int startx = -1, starty = -1;
        int endx = -1, endy = -1;

        for (int i = 0; i < rows; i++) {
            String line = input.nextLine();
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'A') {
                    startx = i;
                    starty = j;
                } else if (grid[i][j] == 'B') {
                    endx = i;
                    endy = j;
                }
            }
        }

        input.close();

        if (startx == -1 || endx == -1) {
            System.out.println("NO");
            return;
        }

        bfs(grid, startx, starty, endx, endy, rows, cols);
    }

    public static void bfs(char[][] grid, int startx, int starty, int endx, int endy, int rows, int cols) {
        boolean[][] visited = new boolean[rows][cols];
        int[][] fromDir = new int[rows][cols];
        int[][] parentX = new int[rows][cols];
        int[][] parentY = new int[rows][cols];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startx, starty});
        visited[startx][starty] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && nx < rows && ny >= 0 && ny < cols &&
                    !visited[nx][ny] && (grid[nx][ny] == '.' || grid[nx][ny] == 'B')) {

                    visited[nx][ny] = true;
                    parentX[nx][ny] = x;
                    parentY[nx][ny] = y;
                    fromDir[nx][ny] = i;

                    if (grid[nx][ny] == 'B') {
                        printPath(fromDir, parentX, parentY, startx, starty, nx, ny);
                        return;
                    }

                    queue.offer(new int[]{nx, ny});
                }
            }
        }

        System.out.println("NO");
    }

    public static void printPath(int[][] fromDir, int[][] parentX, int[][] parentY, int startx, int starty, int endx, int endy) {
        StringBuilder path = new StringBuilder();
        int x = endx, y = endy;

        while (x != startx || y != starty) {
            int dirIndex = fromDir[x][y];
            path.append(dir[dirIndex]);
            int px = parentX[x][y];
            int py = parentY[x][y];
            x = px;
            y = py;
        }

        path.reverse();
        System.out.println("YES");
        System.out.println(path.length());
        System.out.println(path.toString());
    }
}








