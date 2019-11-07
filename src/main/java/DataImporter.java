import org.apache.commons.math3.analysis.function.Divide;
import org.apache.commons.math3.analysis.function.Subtract;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class DataImporter {

    List<List<String>> readFromFile(String nameOfFile){
        List<List<String>> listOfData = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(nameOfFile))) {
            String line;

            while ((line = bufferedReader.readLine()) != null && line.indexOf(",") > 0 ) {
                listOfData.add(Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfData;
    }

    double[][] convertToDoubleArray(List<List<String>> listOfData, Map<String, Integer> dictionary){
        int amountOfArgument = listOfData.get(0).size();
        double[][] matrix = new double[listOfData.size()-1][amountOfArgument];
        Set<Integer> standarizationMode = new HashSet<>();

        for(int i=1;i<listOfData.size();i++){
            List<String> objectFromList = listOfData.get(i);
            for(int j = 0;j<amountOfArgument;j++) {
                String object =  objectFromList.get(j);
                double value = 0.0;
                try {
                    value  = Double.parseDouble(object);
                    if(value >1000){
                        standarizationMode.add(j);
                    }
                }
                catch (NumberFormatException exception){
                    value  = Double.valueOf(dictionary.get(object));
                }
                finally {
                    matrix[i - 1][j] = value;
                }
            }
        }
        if(!standarizationMode.isEmpty()) {
            return standarizationData(matrix,standarizationMode);
        }
        return matrix;
    }

    private double[][] standarizationData(double[][] matrix, Set<Integer> standarizationMode){
        double[] tableToEvaluation = new double[matrix.length];

        for (int index : standarizationMode){
            for (int i = 0; i < matrix.length; i++) {
                tableToEvaluation[i] = matrix[i][index];
            }
            StandardDeviation standardDeviation = new StandardDeviation();
            double deviationResult = standardDeviation.evaluate(tableToEvaluation);
            Mean mean = new Mean();
            double meanResult = mean.evaluate(tableToEvaluation);
            Subtract subtract = new Subtract();
            Divide divide = new Divide();
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][index] = divide.value((subtract.value(matrix[i][index],meanResult)),deviationResult);
            }
        }
        return matrix;
    }
}