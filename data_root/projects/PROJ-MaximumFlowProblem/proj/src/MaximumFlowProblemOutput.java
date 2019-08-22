import si.fri.algotest.execute.AbstractOutput;
import si.fri.algotest.execute.AbstractTestCase;

/**
 * 
 * @author ...
 */
public class MaximumFlowProblemOutput extends AbstractOutput {

  double maximimFlow;

  public MaximumFlowProblemOutput(int maximumFlow) {    
    this.maximumFlow = maximumFlow;
  }
  
  
  @Override
  public String toString() {
    System.out.println("Maximum flow is: " + this.maximimFlow);
    return super.toString();
  }
  
  
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