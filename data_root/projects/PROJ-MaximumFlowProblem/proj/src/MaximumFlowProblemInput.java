import si.fri.algotest.execute.AbstractInput;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ziga Kokelj
 */
public class MaximumFlowProblemInput extends AbstractInput {

  Graph g;
  int a;
  int b;
  
  public MaximumFlowProblemInput(Graph g, int a, int b) {    
    this.g = g;
    this.a = a;
    this.b = b;
  }
      
  @Override
  public String toString() {
    // TODO: provide a handy MaximumFlowProblemInput string representation (include only important data)
    System.out.println("Maximum flow between verices:" + this.a + " and " + this. b);
    this.g.printNeighbours();
    
    return super.toString();
  }
}

public class Graph {
  
  private int vertexCount;
  private double[][] ajdMatrix;

  public Graph(int vertexCount){
    this.vertexCount = vertexCount;
    ajdMatrix = new double[vertexCount][vertexCount];

    //At the beginning set all flows to 0
    for (int i = 0; i < vertexCount; i++){
      for(int j = 0; j < vertexCount; j++){
        ajdMatrix[i][j] = 0;
      }
    }
  }

  // Getters
  public int getVertexCount(){
    return vertexCount;
  }

  public dobule[][] getAdjMatrix(){
    return ajdMatrix;
  }

  //Functions to manipulate graph (edges in the graph)

  public void addEdge(int a, int b, double w){
    // It adds or changes edge - depending on its previous existance
    this.ajdMatrix[a][b] = w;
  }
  
  public void removeEdge(int a, int b){
    this.ajdMatrix[a][b] = 0;
  }

  // Functions to get info from the graph

  public boolean areConnected(int a, int b){
    return (this.ajdMatrix[a][b] == 0) ? false : true;
  }

  public List<Integer> vertexNeighbours(int a){
    List<Integer> edges = new ArrayList<>();
    for(int i = 0; i < vertexCount; i++){
      if(areConnected(a, i)){
        edges.add(i);
      }
    }

    return edges;
  }


  public void printNeighbours(){
    System.out.println("NEIGHBOURS IN GRAPH: ");
    for(int i=0; i < vertexCount; i++){
      List<Integer> currentNeighbours = vertexNeighbours(i);
      System.out.print(i + "-> ");
      int currentNeighboursSize = currentNeighbours.size();
      for(int j=0; j < currentNeighboursSize; j++){
        System.out.print(currentNeighbours.get(j) + ", ");
      }
      System.out.println();
    }
    System.out.println("---------------------");
  }

}