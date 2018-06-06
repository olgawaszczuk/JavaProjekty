import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;

public class Main implements Serializable {

    public static int[][] connectionsMatrix;


    public static void main(String[] args) throws IOException, ClassNotFoundException {



        String inputName = JOptionPane.showInputDialog("Podaj nazwę pliku csv zawierającą macierz danych:",
                "FileName.csv");
        /*String threshold = JOptionPane.showInputDialog("Podaj graniczną wartość wspólnych projektów.",
                "liczba całkowita dodatnia");*/



        CsvFileParser inputFile = new CsvFileParser(inputName);
        if (inputFile.readString() != null) System.out.println(inputFile.readString());
        else System.out.print("Nie udało się załadować pliku");




        Matrix X = inputFile.convertToMatrix();

        if (X.getData() != null){

            Matrix Xt = X.transpose();
            Matrix XtX = Xt.dotProduct(X);
            System.out.println("Macierz wejściowa");
            X.display();
            System.out.println("Macierz przetransponowana");
            Xt.display();
            System.out.println("Macierz XtX");
            XtX.display();

            //System.out.println(XtX.getClass());
            System.out.print("Macierz XtX ma wymiary " +XtX.nrow());
            System.out.print(" na " + XtX.ncol() + ".\n");

            connectionsMatrix = AdditionalFunctions.getConnections(XtX);

            //System.out.println("stworzyłem macierz powiązań:");

            int numOfProjects = connectionsMatrix.length;
            //String[] projectNames = inputNames.readString().split("\n");


            System.out.println("Powiązane projekty:");

            for (int i = 0; i < numOfProjects; i++) {
                ArrayList<Integer> connections = AdditionalFunctions.getStringForRow(connectionsMatrix, i);
                System.out.print((i + 1) + " : ");
                for (Integer connection : connections) {
                    System.out.print(String.format("SDG%d, ", connection + 1));
                }
                System.out.print("\b\b\n");
            }
            System.out.print("Lista pełnych grafów: \n\n");
            ArrayList<ArrayList<Integer>> fullGraphsList = AdditionalFunctions.listAllFullGraphs(X);
            int fullGraphsListSize = fullGraphsList.size();
            for (int i=0; i<fullGraphsListSize; i++) {
                System.out.print("Węzły grafu " + (i + 1) + ": ");
                for (Integer cell : fullGraphsList.get(i)) {
                    System.out.print(String.format("SDG%d, ", cell + 1));
                }
                System.out.print("\b\b\n");
            }
        }





    }


}
