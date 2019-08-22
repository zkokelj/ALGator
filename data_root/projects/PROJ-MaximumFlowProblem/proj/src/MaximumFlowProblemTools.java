/**
 * A class for common static methods (tools) of the project
 *
 * @author Ziga Kokelj
 */
public class MaximumFlowProblemTools {
    /*public static int[][] readMatrix(String path, String fileName) {
    try {
      Scanner sc = new Scanner(new File(path + File.separator + fileName));
      int nSq = sc.nextInt();
      int n = (int) Math.round(Math.sqrt(nSq));
      int[][] result = new int[n][n];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          result[i][j] = sc.nextInt();
        }
      }
      return result;
    } catch (Exception e) {
      return null;
    }
  }
  */
  
    public static int readNumOfNodes(String path, String fileName) {
        try{
            Scanner sc = new Scanner(new File(path + File.separator + fileName));
            int numOfNodes = sc.nextInt();
            return numOfNodes;
        } catch (Exception e ) {
            return -1;
        }
    }

    public static int readSource(String path, String fileName) {
        try{
            Scanner sc = new Scanner(new File(path + File.separator + fileName));
            sc.nextLine();
            int source = sc.nextInt();
            return source;
        } catch (Exception e ) {
            return -1;
        }
    }

    public static int readSink(String path, String fileName) {
        try{
            Scanner sc = new Scanner(new File(path + File.separator + fileName));
            sc.nextLine();
            sc.nextLine();
            int sink = sc.nextInt();
            return sink;
        } catch (Exception e ) {
            return -1;
        }
    }

    public static int readResult(String path, String fileName) {
        try{
            Scanner sc = new Scanner(new File(path + File.separator + fileName));
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            int result = sc.nextInt();
            return result;
        } catch (Exception e ) {
            return -1;
        }
    }

    public static Graph readGraph(String path, String fileName) {
        try{
            Scanner sc = new Scanner(new File(path + File.separator + fileName));
            int n = sc.nextInt();
            Graph g = new Graph(n);
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            // System.out.println(n);
            for(int i = 0; i < n; i++){
                int nodeNumber = 0;
                String line = sc.nextLine();
                String[] split1 = line.split(":");
                nodeNumber = Integer.parseInt(split1[0]);
                System.out.println("Node number is: " + nodeNumber);
                if (split1.length >= 2){
                    // System.out.println(split1[1]);
                    String[] split2 = split1[1].split(";");
                    for(int j = 0; j < split2.length; j++) {
                        String[] split3 = split2[j].split(",");
                        // System.out.println(split3[0] + " and " + split3[1]);
                        g.addEdge(nodeNumber, Integer.parseInt(split3[0]), Double.parseDouble(split3[1]));
                    }    
                }
            }
            return g;
        } catch (Exception e ) {
            System.out.println(e);
            return null;
        }
    }



  

}