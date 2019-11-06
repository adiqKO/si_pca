import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DataImporter {

    List<List<String>> readFromFile(String nameOfFile, String separator){
        List<List<String>> listOfData = new ArrayList<List<String>>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(nameOfFile))) {
            String line;

            while ((line = bufferedReader.readLine()) != null && line.indexOf(separator) > 0 ) {
                listOfData.add(Arrays.asList(line.split(separator)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfData;
    }

    double[][] convertToDoubleArray(List<List<String>> listOfData, List<Integer> activeArgument){
        double[][] matrix = new double[listOfData.size()][activeArgument.size()];

        for(int i=1;i<listOfData.size();i++){
            List<String> partOfData = listOfData.get(i);
            for(int j = 0;j<activeArgument.size();j++) {
                matrix[i-1][j] = Double.parseDouble(partOfData.get(activeArgument.get(j)));
            }
        }
        return matrix;
    }
}
