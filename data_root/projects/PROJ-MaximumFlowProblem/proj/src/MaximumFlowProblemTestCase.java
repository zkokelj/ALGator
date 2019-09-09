import si.fri.algotest.entities.Variables;
import si.fri.algotest.execute.AbstractTestCase;

/**
 *
 * @author ...
 */
public class MaximumFlowProblemTestCase extends AbstractTestCase {

  @Override
  public MaximumFlowProblemInput getInput() {
    return (MaximumFlowProblemInput) super.getInput(); 
  } 

  @Override
  public MaximumFlowProblemOutput getExpectedOutput() {
    return (MaximumFlowProblemOutput) super.getExpectedOutput();
  }
  
  

  @Override
  public MaximumFlowProblemTestCase getTestCase(String testCaseDescriptionLine, String path) {    
    // create a set of variables ...
    Variables inputParameters = new Variables();
    inputParameters.setVariable("Path", path);
    

    // TODO:
    // ... using the testCaseDescriptionLine determine the parameters of
    //     the test case and add them to the inputParameters set ...            
    
    // ... Example: if description line contains two parameters (name_of_test and size N) separated with ":"
    // String [] fields = testCaseDescriptionLine.split(":");     
    // inputParameters.setVariable("Test", fields[0]);
    // inputParameters.setVariable("N",    Integer.parseInt(fields[1]));
    inputParameters.setVariable("N",       17);


    // ... and finally, create a test case determined by these parameters
    return generateTestCase(inputParameters);
  } 

  @Override
  public MaximumFlowProblemTestCase generateTestCase(Variables inputParameters) {
    String path       = inputParameters.getVariable("Path",    "").getStringValue();              

    // TODO: 
    // ... read the values of the parameters and create a corresponding test case

    // ... Example: if test case is and array of integers of size "N"
    // int size = inputParameters.getVariable("N", 0).getIntValue();              
    // int [] array = new int[size];

                    
    
    // create a test case 
    MaximumFlowProblemTestCase maximumFlowProblemTestCase = new MaximumFlowProblemTestCase();                
    maximumFlowProblemTestCase.setInput(new MaximumFlowProblemInput(/* TODO: add parameters for constructor */));    
    maximumFlowProblemTestCase.getInput().setParameters(inputParameters);    
    maximumFlowProblemTestCase.setExpectedOutput(new MaximumFlowProblemOutput(/* TODO: add parameters for constructor */));
    
    return maximumFlowProblemTestCase;
    
  }

}