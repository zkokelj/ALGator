/**
 *
 * @author Romi Koželj
 */

import static java.lang.Math.min;
import java.util.*;


public class EdmondsKarpAlgorithm extends MaximumFlowProblemAbsAlgorithm {

    @Override
    protected MaximumFlowProblemOutput execute(MaximumFlowProblemInput input) {

        int numberOfNodes = input.n;
        int[][] adjMatrix = input.g;
        int s = input.s;
        int t = input.t;

        EdmondsKarpAlgorithm.NetworkFlowSolverBase solver = new EdmondsKarpAlgorithm.EdmondsKarpSolver(numberOfNodes, s, t);
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
        private int visitedToken = 1;
        private int[] visited;

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

            for (List<EdmondsKarpAlgorithm.Edge> edges : graph)
                for (EdmondsKarpAlgorithm.Edge e : edges)
                    if (!e.isResidual()) {
                        g[e.from][e.to] = e.flow;
                    }

            return g;

        }

        // Returns the maximum flow from the source to the sink.
        public int getMaxFlow() {
            execute();
            return maxFlow;
        }

        // Marks node 'i' as visited.
        public void visit(int i) {
            visited[i] = visitedToken;
        }

        // Returns true/false depending on whether node 'i' has been visited or not.
        public boolean visited(int i) {
            return visited[i] == visitedToken;
        }

        // Resets all nodes as unvisited. This is especially useful to do
        // between iterations finding augmenting paths, O(1)
        public void markAllNodesAsUnvisited() {
            visitedToken++;
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

    static class EdmondsKarpSolver extends NetworkFlowSolverBase {

        /**
         * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)} method
         * to add edges to the graph.
         *
         * @param n - The number of nodes in the graph including source and sink nodes.
         * @param s - The index of the source node, 0 <= s < n
         * @param t - The index of the sink node, 0 <= t < n, t != s
         */
        public EdmondsKarpSolver(int n, int s, int t) {
            super(n, s, t);
        }

        // Run Edmonds-Karp and compute the max flow from the source to the sink node.
        @Override
        public void solve() {
            int flow;
            do {
                markAllNodesAsUnvisited();
                flow = bfs();
                maxFlow += flow;
            } while (flow != 0);
        }

        private int bfs() {
            // Initialize BFS queue and add starting source node.
            Queue<Integer> q = new ArrayDeque<>(n);
            visit(s);
            q.offer(s);

            // Perform BFS from source to sink
            Edge[] prev = new Edge[n];
            while (!q.isEmpty()) {
                int node = q.poll();
                if (node == t) break;

                for (Edge edge : graph[node]) {
                    int cap = edge.remainingCapacity();
                    if (cap > 0 && !visited(edge.to)) {
                        visit(edge.to);
                        prev[edge.to] = edge;
                        q.offer(edge.to);
                    }
                }
            }

            // Sink not reachable!
            if (prev[t] == null) return 0;

            // Find augmented path and bottle neck
            int bottleNeck = Integer.MAX_VALUE;
            for (Edge edge = prev[t]; edge != null; edge = prev[edge.from])
                bottleNeck = min(bottleNeck, edge.remainingCapacity());

            // Retrace augmented path and update flow values.
            for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) edge.augment(bottleNeck);

            // Return bottleneck flow
            return bottleNeck;
        }
    }

}