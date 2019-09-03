import si.fri.algotest.execute.AbstractOutput;
import si.fri.algotest.execute.AbstractTestCase;

/**
 * 
 * @author ...
 */
public class MaximumFlowProblemOutput extends AbstractOutput {

  int[][] g;
  int flow;

  public MaximumFlowProblemOutput(int[][] g, int flow) {    
    this.g = g;
    this.flow = flow;
  }
  
  
  @Override
  public String toString() {
    System.out.println("Maximum flow is: " + this.flow);
    for (int[] x : this.g)
    {
        for (int y : x)
        {
            System.out.print(y + " ");
        }
        System.out.println();
    }
    return super.toString();
  }
  
  //TODO: Popravi to metodo, da bo upostevala vse indikatorje
  @Override
  protected Object getIndicatorValue(AbstractTestCase testCase, AbstractOutput algorithmOutput, String indicatorName) {
    MaximumFlowProblemTestCase maximumFlowProblemTestCase        = (MaximumFlowProblemTestCase) testCase;
    MaximumFlowProblemOutput   maximumFlowProblemAlgorithmOutput = (MaximumFlowProblemOutput) algorithmOutput;

    switch (indicatorName) {
      // TODO: for each indicator defined in the atrd file provide a "case" to determnine its value
      //case "indicator_name" :
      //  using the given test case maximumFlowProblemTestCase (which includes the input and the expected output)
      //    and the given maximumFlowProblemAlgorithmOutput (the actual output of the algorithm) calculate indicator_value
      //  return indicator_value;
      case "Check":
        return "nOK";
    }
    
    return null;
  }
}