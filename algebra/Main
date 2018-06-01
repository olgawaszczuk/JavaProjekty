import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class main implements Serializable {

    public static int[][] connectionsMatrix;


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        CsvFileParser inputFile = new CsvFileParser("SDG.csv");
        System.out.println(inputFile.readString());

        //CsvFileParser inputNames = new CsvFileParser("nazwy.csv");
        //System.out.println(inputNames.readString());


        Matrix X = inputFile.convertToMatrix();

        X.display();

        Matrix Xt = X.transpose();
        Matrix XtX = Xt.dotProduct(X);
        X.display();
        Xt.display();
        XtX.display();

        System.out.println(XtX.getClass());
        System.out.println(XtX.ncol());
        System.out.println(XtX.nrow());

        connectionsMatrix = AdditionalFunctions.getConnections(XtX);

        //System.out.println("stworzyłem macierz powiązań:");

        int numOfProjects = connectionsMatrix.length;
        //String[] projectNames = inputNames.readString().split("\n");


        System.out.println("Powiązane projekty:");

        for (int i = 0; i < numOfProjects; i++) {
            ArrayList<Integer> connections = AdditionalFunctions.getStringForRow(connectionsMatrix, i);
            System.out.print((i+1) + " : ");
            for (Integer connection : connections) {
                System.out.printf(String.format("SDG%d,", connection + 1));
            }
            System.out.print("\n");

        }
