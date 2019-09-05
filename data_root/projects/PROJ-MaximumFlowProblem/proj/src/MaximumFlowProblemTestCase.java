import si.fri.algotest.entities.Variables;
import si.fri.algotest.execute.AbstractTestCase;

/**
 *
 * @author Ziga Kokelj
 */

// dodala sem spodnji razred, da ni potrebno napisati več funkcij za branje ampak le no
// funkcija za branje vrne instanco tega razreda, ki vsebuje vse potrebne podatke
// (trenutno ne vem ali mora bit ta razred definiran v obeh datotekah: v tej in v datoteki tools.java ali je dovolj le v eni od njiju?)
class StoreFromFile {
    int s;
    int t;
    int flow;
    //double flow;
    int n;
    int [][] g;
    //double [][] g;
    StoreFromFile(int n_param, int [][] g_param, int s_param, int t_param, int flow_param)
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
    
    int[][] g = null;
    int numOfNodes = 0;
    int source = 0;
    int sink = 0;
    int result = 0;

    // dodala primer branja datoteke:
    //String path je absolutna pot
    StoreFromFile reads = MaximumFlowProblemTools.readFile(path, fileName);
    //System.out.println("n " + reads.n);
    //System.out.println("g " + Arrays.deepToString(reads.g));
    //System.out.println("s " + reads.s);
    //System.out.println("t " + reads.t);
    //System.out.println("flow " + reads.flow);
    n = reads.n;
    g = reads.g;
    s = reads.s;
    t = reads.t;
    flow = reads.flow;
    
    // tole spodnje gre potem ven
    if (group.equals("FILE")){
      numOfNodes = MaximumFlowProblemTools.readNumOfNodes(path, filename);
      source = MaximumFlowProblemTools.readSource(path, filename);
      sink = MaximumFlowProblemTools.readSink(path, filename);
      result = MaximumFlowProblemTools.readResult(path, filename);
      g = MaximumFlowProblemTools.readGraph(path, filename, numOfNodes);
    }else{
      System.out.println("ERROR IN TEST FILE!");
    }

    // TODO: create a test case 
    MaximumFlowProblemTestCase maximumFlowProblemTestCase = new MaximumFlowProblemTestCase();                
    maximumFlowProblemTestCase.setInput(new MaximumFlowProblemInput(g, source, sink));    
    maximumFlowProblemTestCase.getInput().setParameters(inputParameters);    
    maximumFlowProblemTestCase.setExpectedOutput(new MaximumFlowProblemOutput(result));
    
    return maximumFlowProblemTestCase;
    
  }

}