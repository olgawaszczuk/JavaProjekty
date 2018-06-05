import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Main implements Serializable {


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        CsvFileParser inputFile = new CsvFileParser("SDG.csv");
        System.out.println(inputFile.readString());

        //CsvFileParser inputNames = new CsvFileParser("nazwy.csv");
        //System.out.println(inputNames.readString());


        Matrix X = inputFile.convertToMatrix(";");


        Matrix Xt = X.transpose();
        Matrix XtX = Xt.dotProduct(X);
        X.display();
        Xt.display();
        XtX.display();

        ArrayList<ArrayList<Integer>> connections = AdditionalFunctions.getConnectionLists(XtX, 3.0);
        int projectNum = 1;
        for (ArrayList<Integer> row : connections) {
            System.out.print("Projekt nr " + projectNum + ": ");
            for (Integer cell : row) {
                System.out.print(cell + 1+ ", ");
            }
            System.out.print("\n");
            projectNum++;
        }
        System.out.print("\n");


        ArrayList<ArrayList<Integer>> allGraphs = AdditionalFunctions.listAllFullGraphs(connections);
        for (ArrayList<Integer> row : allGraphs) {
            System.out.println(row);
        }



 /*       System.out.println(XtX.getClass());
        System.out.println(XtX.ncol());
        System.out.println(XtX.nrow());
*/
    /*    int[][] connectionsMatrix = AdditionalFunctions.getConnections(XtX, 0.4);

        //System.out.println("stworzyłem macierz powiązań:");

        int numOfProjects = connectionsMatrix.length;
        //String[] projectNames = inputNames.readString().split("\n");


        System.out.println("Powiązane projekty:");

        for (int i = 0; i < numOfProjects; i++) {
            ArrayList<Integer> connections = AdditionalFunctions.getStringForRow(connectionsMatrix, i);
            System.out.print((i + 1) + " : ");
            for (Integer connection : connections) {
                System.out.printf(String.format("SDG%d,", connection + 1));
            }
            System.out.print("\n");

        }*/
    }
}
