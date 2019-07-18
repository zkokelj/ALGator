/**
 *
 * @author Ziga Kokelj
 */
//TODO: Import Graph data structure defined in MaximumFlowProblemInput

public class FordFulkersonAlgorithm extends MaximumFlowProblemAbsAlgorithm {

  @Override
  protected MaximumFlowProblemOutput execute(MaximumFlowProblemInput input) {
    
    int n = input.g.getVertexCount();
    Graph g = input.g;
    int a = input.a;
    int b = input.b;
    int maxFlow = 0;

    //TODO: Ford-Fulkerson algorithm that calculates maxFlow
    
    MaximumFlowProblemOutput output = new MaximumFlowProblemOutput(maxFlow);    
    return output;
  }

}