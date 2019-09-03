import si.fri.algotest.execute.AbstractInput;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ziga Kokelj
 */
public class MaximumFlowProblemInput extends AbstractInput {

  int n;
  int [][] g; //graph
  int s;      //source
  int t;      //sink
  
  public MaximumFlowProblemInput(int n, int[][] g, int s, int t) {    
    this.g = g;
    this.s = s;
    this.t = t;
    this.n = n;
  }
      
  @Override
  public String toString() {
    System.out.println("Maximum flow between verices:" + this.s + " and " + this. t);
    for (int[] x : g)
        {
            for (int y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    
    return super.toString();
  }
}