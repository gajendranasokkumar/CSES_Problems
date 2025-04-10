import java.util.*;

public class counting_rooms {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] strs = input.nextLine().split(" ");
        int rows = Integer.parseInt(strs[0]);
        int cols = Integer.parseInt(strs[1]);
        char[][] grid = new char[rows][cols];
        for(int i = 0; i < rows; i++) {
            String line = input.nextLine();
            for(int j = 0; j < cols; j++) 
                grid[i][j] = line.charAt(j); 
        }

        int count = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                if(grid[i][j] == '.') {
                    count++;
                    bfs(grid, i, j, rows, cols);
                }
            }
        }
        System.out.println(count);
        input.close();
    }

    public static void bfs(char[][] grid, int row, int col, int rows, int cols) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{row, col});
        grid[row][col] = '#'; 

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            for (int i = 0; i < 4; i++) {
                int newRow = r + dx[i];
                int newCol = c + dy[i];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol] == '.') {
                    grid[newRow][newCol] = '#';
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }
}
