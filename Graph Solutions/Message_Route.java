import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Message_Route {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int cities = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> list = new ArrayList<>();
        for(int i=0;i<=cities;i++)
            list.add(new ArrayList<>());
        for(int i=0;i<m;i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            list.get(from).add(to);
            list.get(to).add(from);
        }
        int[] parent = new int[cities+1];
        parent[1] = -1;
        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        boolean found = false;
        while(!q.isEmpty()) {
            int from = q.poll();
            for(int to : list.get(from)) {
                if(parent[to] == 0) {
                    parent[to] = from;
                    q.add(to);
                    if(to == cities) {
                        found = true;
                        break;
                    }
                }
            }
            if(found)
                break;
        }

        if(!found) {
            System.out.println("IMPOSSIBLE");
            return;
        }

        int start = cities;
        int count = 0;
        List<Integer> result = new ArrayList<>();
        while (start != -1) {
            count++;
            result.add(start);
            start = parent[start];
        }
        System.out.println(count);
        for(int i=result.size()-1;i>=0;i--)
            System.out.print(result.get(i) + " ");
    }
}