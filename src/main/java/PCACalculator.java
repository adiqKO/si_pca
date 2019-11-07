import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.*;

public class PCACalculator {

    public static void main(String[] args) {

        DataImporter dataImporter = new DataImporter();
        List<List<String>> data = dataImporter.readFromFile("insurance.csv");
        Map<String, Integer> dictionary = new HashMap<String, Integer>()
        {{
            put("female",0);
            put("male",1);
            put("no",0);
            put("yes",1);
            put("northwest",0);
            put("southeast",1);
            put("southwest",2);
            put("northeast",3);
        }};

        double[][] pointsArray = dataImporter.convertToDoubleArray(data, dictionary);

        RealMatrix realMatrix = MatrixUtils.createRealMatrix(pointsArray);
        System.out.println("Macierz danych: "+realMatrix);

        //create covariance matrix of points, then find eigen vectors
        //see https://stats.stackexchange.com/questions/2691/making-sense-of-principal-component-analysis-eigenvectors-eigenvalues

        Covariance covariance = new Covariance(realMatrix);
        RealMatrix covarianceMatrix = covariance.getCovarianceMatrix();
        System.out.println("Macierz kowariancji: "+covarianceMatrix);
        EigenDecomposition ed = new EigenDecomposition(covarianceMatrix);
        System.out.println("Wartosci wlasne "+ Arrays.toString(ed.getRealEigenvalues()));
        System.out.println("Wektor wlasny[0] "+ ed.getEigenvector(0));
        System.out.println("Wektor wlasny[1] "+ ed.getEigenvector(1));
        System.out.println("Wektor wlasny[2] "+ ed.getEigenvector(2));

        double[][] matrixWdata = new double[][]{
                ed.getEigenvector(0).toArray(),
                ed.getEigenvector(1).toArray(),
                ed.getEigenvector(2).toArray()};

        RealMatrix matrixW = MatrixUtils.createRealMatrix(matrixWdata).transpose();
        System.out.println("Macierz W: "+ matrixW);

        //multiplying
        for(int i=0; i<20;i++){
            double[] vectorResult = matrixW.preMultiply(pointsArray[i]);
            System.out.println("Wektor x["+ i +"]' "+ Arrays.toString(vectorResult));
        }
    }
}