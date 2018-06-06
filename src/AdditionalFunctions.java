import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public abstract class AdditionalFunctions implements Serializable {


    //Zwraca listę list połączeń z macierzy XtX, dla każdego kolejnego projektu, przy progu wspólnych projektów równym threshold
    private static ArrayList<ArrayList<Integer>> getConnectionLists(Matrix inputMatrix, double threshold) {
        ArrayList<ArrayList<Double>> data = inputMatrix.getData();
        ArrayList<ArrayList<Integer>> outputList = new ArrayList<ArrayList<Integer>>();
        int nrow = inputMatrix.nrow();
        int ncol = inputMatrix.ncol();
        for (int i=0; i<nrow; i++) {
            outputList.add(new ArrayList<Integer>());
            for (int j=0; j<ncol; j++) {
                if (data.get(i).get(j)>threshold && i!=j) {
                    outputList.get(i).add(j);
                }
            }
        }
        return outputList;
    }

    //Buduje listę node'ow powiązanych z konkretnym nodem i ich wewnętrzne powiązania
    private static ArrayList<ArrayList<Integer>> getCommonNodes(ArrayList<ArrayList<Integer>> connectionList, int initialNodeInd) {

        ArrayList<ArrayList<Integer>> myList = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> mySecondList = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> initialNode = connectionList.get(initialNodeInd);

        //Wyciągamy listę list node'ow powiązanych z initialNodeInd
        myList.add(new ArrayList<Integer>());
        int lastRow = 1;
        myList.get(0).add(initialNodeInd);
        for (int nodeInd : initialNode) {
            myList.get(0).add(nodeInd);
            myList.add(new ArrayList<Integer>());
            myList.get(lastRow).add(nodeInd);
            for (int j : connectionList.get(nodeInd)) {
                myList.get(lastRow).add(j);
            }
            lastRow++;
        }

        //Usuwamy zewnetrzne nody
        int lastSecondRow = 0;
        for (ArrayList<Integer> row : myList) {
            mySecondList.add(new ArrayList<Integer>());
            for (int cell : row) {
                if (myList.get(0).contains(cell)) {
                    mySecondList.get(lastSecondRow).add(cell);
                }
            }
            lastSecondRow++;
        }
        return mySecondList;
    }

    private static ArrayList<ArrayList<Integer>> buildFullGraph (ArrayList<ArrayList<Integer>> input) {
        ArrayList<ArrayList<Integer>> myList = input;
        int narrowestThroat = narrowestThroatNum(myList);
        int narrowestThroatInd = projectNumToIndice(myList, narrowestThroat);
        while (narrowestThroatInd != 0) {
            for (ArrayList<Integer> row : myList) {
                if (row.contains(narrowestThroat)) {
                    row.remove(new Integer(narrowestThroat));
                }
            }
            myList.remove(narrowestThroatInd);

            narrowestThroat = narrowestThroatNum(myList);
            narrowestThroatInd = projectNumToIndice(myList, narrowestThroat);
        }
        return myList;
    }

    private static ArrayList<Integer> fullGraph (ArrayList<ArrayList<Integer>> connectionList, int initialNodeInd) {
        ArrayList<ArrayList<Integer>> commonNodes = getCommonNodes(connectionList, initialNodeInd);
        ArrayList<ArrayList<Integer>> allNodes = buildFullGraph(commonNodes);
        return  allNodes.get(0);
    }

    //Docelowa funkcja
    public static ArrayList<ArrayList<Integer>> listAllFullGraphs (Matrix inputMatrix) {
        Matrix XtX = inputMatrix.transpose().dotProduct(inputMatrix);
        ArrayList<ArrayList<Integer>> connectionList = getConnectionLists(XtX, 6);
        ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();
        int conListSize = connectionList.size();
        for (int i=0; i<conListSize; i++) {
            ArrayList<Integer> tempList = fullGraph(connectionList, i);
            Collections.sort(tempList);
            if (!output.contains(tempList) && tempList.size()>1) output.add(tempList);
        }
        return output;
    }

    //Zwraca indeks projektu o oznaczeniu num
    private static int projectNumToIndice (ArrayList<ArrayList<Integer>> input, int num) {
        int output = 0;
        int inpSize = input.size();
        for (int i=0; i<inpSize; i++) {
            if (input.get(i).get(0)==num) {
                output = i;
                break;
            }
        }
        return output;
    }

    //Wyszukuje podlisty o najkrótszej długości i zwraca jej numer
    private static int narrowestThroatNum (ArrayList<ArrayList<Integer>> input) {
        int minimum = input.get(0).size();
        int output = 0;
        int rowSize;
        for (ArrayList<Integer> row : input) {
            rowSize = row.size();
            if (rowSize < minimum) minimum = rowSize;
        }
        for (ArrayList<Integer> row : input) {
            rowSize = row.size();
            if (rowSize == minimum) {
                output = row.get(0);
                break;
            }
        }
        return output;
    }



    public static int[][] getConnections(Matrix inputMatrix) {
        int irow = 0;
        int icol = 0;
        int iproject = 0;
        int mSizeRow = inputMatrix.nrow();
        int mSizeCol = inputMatrix.ncol();
        int[][] connectionsMatrix = new int[mSizeRow][mSizeCol];
        double factor = 6;
        ArrayList<ArrayList<Double>> data = inputMatrix.getData();
        for (ArrayList<Double> row : data) {
            icol = 0;
            iproject = irow;
            for (Double element : row) {
                if (element >= factor) {
                    if (icol != iproject) {
                        connectionsMatrix[irow][icol] = icol;
                    } else {
                        connectionsMatrix[irow][icol] = -1;
                    }
                } else {
                    connectionsMatrix[irow][icol] = -1;
                }
                icol++;
            }
            irow++;
        }
        return connectionsMatrix;
    }


    public static ArrayList<Integer> getStringForRow(int[][] connectionsMatrix, int project) {
        int numOfProjects = connectionsMatrix.length;
        ArrayList<Integer> connections = new ArrayList<>();
        ArrayList<Integer> searched = new ArrayList<>();

        getConnections(connectionsMatrix, project, connections, searched);

        return connections;
    }

    private static void getConnections(int[][] connectionsMatrix, int project, ArrayList<Integer> connections, ArrayList<Integer> searched) {
        for (int connection : connectionsMatrix[project]) {
            if (searched.indexOf(connection) ==-1 && connection != -1) {
                searched.add(connection);
                addProjectToConnections(connections, connection);
                getConnections(connectionsMatrix, connection, connections, searched);
            }
        }
    }

    private static void addProjectToConnections(ArrayList<Integer> connections, int project) {
        if (connections.indexOf(project) == -1) {
            connections.add(project);
        }
    }


}
