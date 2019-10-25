import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.Arrays;

public class PCACalculator {

    public static void main(String[] args) {

        double[][] pointsArray = new double[][]{
                new double[]{57.5, 0.525, 57, 51, -47},
                new double[]{7.5, 0.505, 49, 47, -69},
                new double[]{-67.5, 0.545, 49, 43, -75},
                new double[]{-77.5, 0.515, 49, 33, -71},
                new double[]{-57.5, 0.535, 49, 29, -75},
                new double[]{-357.5, 0.325, 25, 19, -87}};

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
    }
}
