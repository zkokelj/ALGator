/**
 *
 * @author Romi Koželj
 */

import static java.lang.Math.min;
import java.util.*;

public class FordFulkersonAlgorithm extends MaximumFlowProblemAbsAlgorithm {

    @Override
    protected MaximumFlowProblemOutput execute(MaximumFlowProblemInput input) {
        
        int numberOfNodes = input.n;
        int[][] adjMatrix = input.g;
        int s = input.s;
        int t = input.t;

        FordFulkersonAlgorithm.NetworkFlowSolverBase solver = new FordFulkersonAlgorithm.FordFulkersonDfsSolver(numberOfNodes, s, t);
        solver.addEdges(adjMatrix, numberOfNodes);
        int flow = solver.getMaxFlow();
        int [][] resultAdjMatrix = solver.getAdjMatrix(numberOfNodes);

        MaximumFlowProblemOutput output = new MaximumFlowProblemOutput(resultAdjMatrix, flow);
        
        return output;
    }

    static class Edge {
        public int from, to;
        public Edge residual;
        public int flow;
        public final int capacity;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public boolean isResidual() {
            return capacity == 0;
        }

        public int remainingCapacity() {
            return capacity - flow;
        }

        public void augment(int bottleNeck) {
            flow += bottleNeck;
            residual.flow -= bottleNeck;
        }

    }

    abstract static class NetworkFlowSolverBase {

        // To avoid overflow, set infinity to a value less than Integer.MAX_VALUE;
        static final int INF = Integer.MAX_VALUE / 2;

        // Inputs: n = number of nodes, s = source, t = sink
        final int n, s, t;

        // 'visited' and 'visitedToken' are variables used in graph sub-routines to
        // track whether a node has been visited or not. In particular, node 'i' was
        // recently visited if visited[i] == visitedToken is true. This is handy
        // because to mark all nodes as unvisited simply increment the visitedToken.
        protected int visitedToken = 1;
        protected int[] visited;

        // Indicates whether the network flow algorithm has ran. The solver only
        // needs to run once because it always yields the same result.
        protected boolean solved;

        // The maximum flow. Calculated by calling the {@link #solve} method.
        protected int maxFlow;

        // The adjacency list representing the flow graph.
        protected List<Edge>[] graph;

        /**
         * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
         * the graph.
         *
         * @param n - The number of nodes in the graph including s and t.
         * @param s - The index of the source node, 0 <= s < n
         * @param t - The index of the sink node, 0 <= t < n and t != s
         */
        public NetworkFlowSolverBase(int n, int s, int t) {
            this.n = n;
            this.s = s;
            this.t = t;
            initializeEmptyFlowGraph();
            visited = new int[n];
        }

        // Constructs an empty graph with n nodes including s and t.
        private void initializeEmptyFlowGraph() {
            graph = new List[n];
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<Edge>();
        }

        /**
         * Adds a directed edge (and its residual edge) to the flow graph.
         *
         * @param from - The index of the node the directed edge starts at.
         * @param to - The index of the node the directed edge ends at.
         * @param capacity - The capacity of the edge
         */
        public void addEdge(int from, int to, int capacity) {
            if (capacity <= 0) throw new IllegalArgumentException("Forward edge capacity <= 0");
            Edge e1 = new Edge(from, to, capacity);
            Edge e2 = new Edge(to, from, 0);
            e1.residual = e2;
            e2.residual = e1;
            graph[from].add(e1);
            graph[to].add(e2);
        }

        public void addEdges(int [][] g, int n) {
            for (int from = 0; from < n; from++){
                for(int to = 0; to < n; to++){
                    if (g[from][to] > 0)
                        this.addEdge(from, to, g[from][to]);
                }
            }
        }

        /**
         * Returns the residual graph after the solver has been executed. This allows you to inspect the
         * {@link Edge#flow} and {@link Edge#capacity} values of each edge. This is useful if you are
         * debugging or want to figure out which edges were used during the max flow.
         */
        public List<Edge>[] getGraph() {
            execute();
            return graph;
        }


        public int[][] getAdjMatrix(int n) {
            execute();
            // Displays all edges part of the resulting residual graph. //TODO iz tega grafa izlušči svoj graf
            int[][] g = new int[n][n];
            //At the beginning set all flows to 0
            for (int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    g[i][j] = 0;
                }
            }

            for (List<FordFulkersonAlgorithm.Edge> edges : graph)
                for (FordFulkersonAlgorithm.Edge e : edges)
                    if (!e.isResidual()) {
                        g[e.from][e.to] = e.flow;
                        //System.out.println(e.toString(s, t));
                    }

            return g;
        }

        // Returns the maximum flow from the source to the sink.
        public int getMaxFlow() {
            execute();
            return maxFlow;
        }

        // Wrapper method that ensures we only call solve() once
        private void execute() {
            if (solved) return;
            solved = true;
            solve();
        }

        // Method to implement which solves the network flow problem.
        public abstract void solve();
    }

    static class FordFulkersonDfsSolver extends NetworkFlowSolverBase {

        /**
         * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
         * the graph.
         *
         * @param n - The number of nodes in the graph including s and t.
         * @param s - The index of the source node, 0 <= s < n
         * @param t - The index of the sink node, 0 <= t < n and t != s
         */
        public FordFulkersonDfsSolver(int n, int s, int t) {
            super(n, s, t);
        }

        // Performs the Ford-Fulkerson method applying a depth first search as
        // a means of finding an augmenting path.
        @Override
        public void solve() {
            // Find max flow by adding all augmenting path flows.
            for (int f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
                visitedToken++;
                maxFlow += f;
            }
        }

        private int dfs(int node, int flow) {
            // At sink node, return augmented path flow.
            if (node == t) return flow;

            // Mark the current node as visited.
            visited[node] = visitedToken;

            List<Edge> edges = graph[node];
            for (Edge edge : edges) {
                if (edge.remainingCapacity() > 0 && visited[edge.to] != visitedToken) {
                    int bottleNeck = dfs(edge.to, min(flow, edge.remainingCapacity()));

                    // If we made it from s -> t (a.k.a bottleNeck > 0) then
                    // augment flow with bottleneck value.
                    if (bottleNeck > 0) {
                        edge.augment(bottleNeck);
                        return bottleNeck;
                    }
                }
            }
            return 0;
        }
    }
}