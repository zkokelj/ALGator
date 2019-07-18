import si.fri.algotest.execute.AbstractAlgorithm;

/**
 *
 * @author ...
 */
public abstract class MaximumFlowProblemAbsAlgorithm extends AbstractAlgorithm {
 
  @Override
  public MaximumFlowProblemTestCase getCurrentTestCase() {
    return (MaximumFlowProblemTestCase) super.getCurrentTestCase(); 
  }

  protected abstract MaximumFlowProblemOutput execute(MaximumFlowProblemInput maximumFlowProblemInput);

  @Override
  public void run() {    
    algorithmOutput = execute(getCurrentTestCase().getInput());
  }
}