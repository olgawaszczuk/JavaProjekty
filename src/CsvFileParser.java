import java.io.*;
import java.util.ArrayList;


public class CsvFileParser implements Serializable {

    private String obiektIn;
    private ObjectOutputStream obiektOut;
    private String stringMatrix;

    CsvFileParser(String csvFileInput) {
        obiektIn = csvFileInput;
        obiektOut = null;
    }

    public String readString() throws IOException {
        try{
            BufferedReader in =
                    new BufferedReader(
                            new FileReader(obiektIn));

            char c;
            StringBuffer strng = new StringBuffer();
            while ((c = (char) in.read()) != (char) -1)
                strng.append(c);
            strng.append('\n');
            in.close();
            stringMatrix = strng.toString();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Nie można znaleźć pliku. " +
                    "Została podana błędna nazwa lub plik nie znajduje się w folderze projektu.");

        }


        return stringMatrix;

    }

    public Matrix convertToMatrix() {
        ArrayList<ArrayList<Double>> resultData = new ArrayList<ArrayList<Double>>();
        try {
            String[] lines = stringMatrix.split("\n");
            int i = 0;
            for (String line : lines) {
                String[] fields = line.split(";");
                resultData.add(new ArrayList<Double>());
                int j = 0;
                for (String field : fields) {
                    Double v = Double.parseDouble(field);
                    resultData.get(i).add(v);
                    j++;
                }
                i++;
            }
        }
        catch (NullPointerException e){
            return new Matrix(null);
        }

        return new Matrix(resultData);
    }

}
