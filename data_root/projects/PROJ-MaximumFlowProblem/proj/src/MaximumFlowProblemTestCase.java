import si.fri.algotest.entities.Variables;
import si.fri.algotest.execute.AbstractTestCase;
import si.fri.algotest.global.ErrorStatus;

/**
 *
 * @author Ziga Kokelj
 */

// spodnji razred se uporablja pri branju, ne vem ali je ok, da je tu
// ta razred je kopija razrda iz tools.java, ker ne vem, kako ga importat sem notr
class StoreDataFromFile {
    int s;
    int t;
    int flow;
    //double flow;
    int n;
    int [][] g;
    //double [][] g;
    StoreDataFromFile(int n_param, int [][] g_param, int s_param, int t_param, int flow_param)
    {
        s = s_param;
        t = t_param;
        n = n_param;
        flow = flow_param;
        g = g_param;
    }
}

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
    
    String[] params = testCaseDescriptionLine.split(":");
    if (params.length < 3) {
      ErrorStatus.setLastErrorMessage(ErrorStatus.ERROR, "Invalid testset file - line ");
      return null;
    }

    //TODO: Preveri parametre, ce so vsi potrebni in ce je treba kakega dodati.
    inputParameters.setVariable("Name",      params[0]);
    inputParameters.setVariable("Group",     params[1]);
    inputParameters.setVariable("N",         params[2]);

    if (params[1].toUpperCase().equals("FILE")) {
      inputParameters.setVariable("Filename", params.length > 3 ? params[3] : "");
    }

    // ... and finally, create a test case determined by these parameters
    return generateTestCase(inputParameters);
  } 

  @Override
  public MaximumFlowProblemTestCase generateTestCase(Variables inputParameters) {
    // Get input parameters from getTestCase method
    String path      = inputParameters.getVariable("Path",    "")   .getStringValue();              
    int N            = inputParameters.getVariable("N",        1000).getIntValue();              
    String group     = inputParameters.getVariable("Group",   "RND").getStringValue().toUpperCase();              
    String filename = inputParameters.getVariable("Filename", "").getStringValue();              
    
    //inicialization if something goes wrong with file
    int[][] g = null;
    int numOfNodes = 0;
    int source = 0;
    int sink = 0;
    int flow = 0;
    
    if (group.equals("FILE")){
      //String path je absolutna pot
      StoreDataFromFile data = MaximumFlowProblemTools.readFile(path, filename);
      //System.out.println("n " + data.n);
      //System.out.println("g " + Arrays.deepToString(data.g));
      //System.out.println("s " + data.s);
      //System.out.println("t " + data.t);
      //System.out.println("flow " + data.flow);
      numOfNodes = data.n;
      g = data.g;
      source = data.s;
      sink = data.t;
      flow = data.flow;
    }else{
      System.out.println("ERROR IN TEST FILE!");
    }
    

    // Create a test case 
    MaximumFlowProblemTestCase maximumFlowProblemTestCase = new MaximumFlowProblemTestCase();                
    maximumFlowProblemTestCase.setInput(new MaximumFlowProblemInput(numOfNodes, g, source, sink));    
    maximumFlowProblemTestCase.getInput().setParameters(inputParameters);    
    maximumFlowProblemTestCase.setExpectedOutput(new MaximumFlowProblemOutput(g, flow));
    
    return maximumFlowProblemTestCase;
    
  }

}
