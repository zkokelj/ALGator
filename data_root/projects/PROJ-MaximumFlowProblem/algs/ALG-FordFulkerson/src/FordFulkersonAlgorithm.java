/**
 *
 * @author Ziga Kokelj
 */
import java.util.LinkedList;

public class FordFulkersonAlgorithm extends MaximumFlowProblemAbsAlgorithm {

  @Override
  protected MaximumFlowProblemOutput execute(MaximumFlowProblemInput input) {
    
    int numberOfNodes = input.n;
    int[][] adjMatrix = input.g;
    int s = input.s;
    int t = input.t;
    
    int[][] graph = fordFulkerson(adjMatrix, s, t, numberOfNodes);
    int flow = graph[0][0];
    graph[0][0] = 0;
    
    
    MaximumFlowProblemOutput output = new MaximumFlowProblemOutput(graph, flow);    
    return output;
  }


  private static boolean bfs(int rGraph[][], int s, int t, int parent[], int n)
    {
        boolean visited[] = new boolean[n];
        for(int i=0; i<n; ++i)
            visited[i]=false;

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        while (queue.size()!=0)
        {
            int u = queue.poll();
            for (int v=0; v<n; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return (visited[t] == true);
    }


  public static int[][] fordFulkerson(int graph[][], int s, int t, int n)
  {
      int u, v;

      int rGraph[][] = new int[n][n];

      for (u = 0; u < n; u++)
          for (v = 0; v < n; v++)
              rGraph[u][v] = graph[u][v];

      int parent[] = new int[n];
      int max_flow = 0;

      while (bfs(rGraph, s, t, parent,n))
      {
          int path_flow = Integer.MAX_VALUE;
          for (v=t; v!=s; v=parent[v])
          {
              u = parent[v];
              path_flow = Math.min(path_flow, rGraph[u][v]);
          }

          for (v=t; v != s; v=parent[v])
          {
              u = parent[v];
              rGraph[u][v] -= path_flow;
              rGraph[v][u] += path_flow;
          }
          max_flow += path_flow;
      }
      for(int i = 0; i < n; i++){
          for(int j = 0; j < n; j++){
            graph[i][j] = Math.max(graph[i][j]-rGraph[i][j],0);
          }
      }
      //TODO: Is it ok to assume, that there is no path from souce back to source?
      //We save max flow in (0, 0) in the matrix (flow from source to the same source).
      graph[0][0] = max_flow;

      return graph;
  }
}