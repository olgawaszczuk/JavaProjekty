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
        BufferedReader in =
                new BufferedReader(
                        new FileReader(obiektIn));
        char c;
        StringBuffer strng = new StringBuffer();
        while ((c = (char) in.read()) != (char) -1)//'\n')
            strng.append(c);
        strng.append('\n');
        in.close();
        stringMatrix = strng.toString();
        return stringMatrix;

    }

    public Matrix convertToMatrix(String sep) {
        ArrayList<ArrayList<Double>> resultData = new ArrayList<ArrayList<Double>>();
        String[] lines = stringMatrix.split("\n");
        int i = 0;
        for (String line : lines) {
            String[] fields = line.split(sep);
            resultData.add(new ArrayList<Double>());
            int j = 0;
            for (String field : fields) {
                Double v = Double.parseDouble(field);
                resultData.get(i).add(v);
                j++;
            }
            i++;
        }

        return new Matrix(resultData);
    }

    /*

    public void FileOpenOut() throws IOException {
        obiektOut = new ObjectOutputStream(
                new FileOutputStream("abc.txt"));
    }

    public String getObiektIn() {
        return obiektIn;
    }


    public void FileCloseOut() throws IOException {
        obiektOut.close();
    }

    public ObjectOutputStream getObiektOut() {
        return obiektOut;
    }

*/
}
