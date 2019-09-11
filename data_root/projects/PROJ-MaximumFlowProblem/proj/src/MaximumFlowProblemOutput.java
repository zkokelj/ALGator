import si.fri.algotest.execute.AbstractOutput;
import si.fri.algotest.execute.AbstractTestCase;

/**
 * 
 * @author Ziga Kokelj
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
      case "Check":
        // Tu moramo klicati checkValidity in podati vse zahtevane parametre (originalen graf, graf rešitve, # vozlisc, izvor, ponor)
        // funkcija nato vrne true//false => OK/nOK
        if (checkValidity(maximumFlowProblemTestCase.getInput().g, maximumFlowProblemAlgorithmOutput.g , maximumFlowProblemTestCase.getInput().n, maximumFlowProblemTestCase.getInput().s , maximumFlowProblemTestCase.getInput().t)){
          return "OK";
        }else{
          return "nOK";
        }

      case "QualityOfResult":
        // Tu moramo na podlagi flow-a izracunati vrednost. Po kaksni formuli naj jo iztacunava? (Je spodnja vrstica pravilna?)
        return Math.abs(maximumFlowProblemAlgorithmOutput.flow - maximumFlowProblemTestCase.flow);
    }
    
    return null;
  }

  //Funckija, ki preverja ali vrnjem graf ustreza pogojem originalnega grafa.
  private static boolean checkValidity(int[][] g1, int[][] g2, int n, int s, int t){
    //g1 je original graf
    //g2 je graf pretokov

    //Preverimo, ce so vsi pretoki manjsi ali enaki, kot so mozni pretoki v grafu
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            if (g2[i][j] > g1[i][j]){
                return false;
            }
        }
    }

    //Preverimo, ce je vsota tokov v graf enaka vsoti tokov iz grafa
    for(int i = 0; i < n; i++){
        //Za izvor in ponor to ne velja
        if(i == s || i == t)
            continue;
        //Za ostale mora pa to ustrezati v grafu resitve
        int out = 0;
        int in = 0;
        for (int j = 0; j < n; j++){
            out += g2[i][j];
        }

        for (int j = 0; j < n; j++){
            in += g2[j][i];
        }

        if (in != out)
            return false;

    }

    return true;
}
}