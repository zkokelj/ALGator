import com.romi.StoreFromFile;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class for common static methods (tools) of the project
 *
 * @author Romi Koželj
 */

// dodala sem spodnji razred, da ni potrebno napisati več funkcij za branje ampak le no
// funkcija za branje vrne instanco tega razreda, ki vsebuje vse potrebne podatke
// (ne vem ali mora bit ta razred definiran v obeh datotekah: v tej in v datoteki testcase.java ali je dovolj le v eni od njiju?)
class StoreFromFile {
    int s;
    int t;
    double flow;
    int n;
    //int [][] g;
    double [][] g;

    StoreFromFile(int n_param, double [][] g_param, int s_param, int t_param, double flow_param)
    {
        s = s_param;
        t = t_param;
        n = n_param;
        flow = flow_param;
        g = g_param;
    }
}

class MaximumFlowProblemTools{

    public static StoreFromFile readFile(String path, String fileName) {
        try {
            System.out.println(path + File.separator + fileName);
            Scanner sc = new Scanner(new File(path + File.separator + fileName));
            
            //source and sink node
            int s = Integer.parseInt(sc.nextLine().trim().split(" ")[1]);
            int t = Integer.parseInt(sc.nextLine().trim().split(" ")[1]);
            
            // max flow value
            //int flow = Integer.parseInt(sc.nextLine().trim().split(" ")[2]);
            double flow = Double.parseDouble(sc.nextLine().trim().split(" ")[2]);
            
            //number of nodes
            int n = Integer.parseInt(sc.nextLine().trim().split(" ")[1]);

            for (int i = 0; i < n; i++)
                sc.nextLine(); //all the node lines //

            sc.nextLine(); //*arcs line
            
            /*
            int[][] g = new int[n][n];
            //At the beginning set all flows to 0
            for (int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    g[i][j] = 0;
                }
            }
            */
            double[][] g = new double[n][n];
            //At the beginning set all flows to 0
            for (int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    g[i][j] = 0.0;
                }
            }

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                //System.out.println("line " + line);
                if (line.equals(""))
                    break;

                int from = Integer.parseInt(line.split(" ")[0]);
                int to = Integer.parseInt(line.split(" ")[1]);
                //int capacity = Integer.parseInt(line.split(" ")[2]);
                double capacity = Double.parseDouble(sc.nextLine().trim().split(" ")[2]);
                g[from][to] = capacity;

            }
            sc.close();
            //System.out.println(Arrays.deepToString(g));
            return new StoreFromFile(n, g, s, t, flow);
        } catch (Exception e) {
            return null; // morda bi bilo kle bolje vrnt kaj drugega?
        }

    }
}