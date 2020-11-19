package data;

import java.io.IOException;
import java.util.ArrayList;

public class EstimatorMotionsDetection {
    private double coefficient;
    private double error;
    private final ArrayList<Double> peopleMotions = new ArrayList<>(); 

    public EstimatorMotionsDetection() {
    }

    public double getCoefficient() {
        return coefficient;
    }
    
    // Method to insert the coefficient
    public double setCoefficient(double coefficient) {
        this.coefficient = coefficient;
        return this.coefficient;
    }

    public ArrayList<Double> getPeopleMotions() {
        return peopleMotions;
    }
    
    // Method to return the error
    public double getError(double coefficient, double[] sumLaptopPower) throws IOException {
        DataContainer dataMotion = new DataContainer("office.csv"); 
        this.error = 0;
        Double[] motionsDetected = dataMotion.getData("detected_motions");
        for (int i = 0; i < dataMotion.getNumberOfSamples(); i++) {
            this.error = this.error + Math.abs(coefficient*motionsDetected[i] - sumLaptopPower[i]);
        }
        return this.error;
    }
      
    // Method to use dichotmy (maximum 100 iterations)
    public double[] dichotomy(int iterationMax, double[] sumLaptopPower) throws IOException {
        // According to the data the number of motions > number of people so we know 0 < c < 1
        int minC = 0;
        int maxC = 1;
        double l = minC;
        double u = maxC;
        double c = 0;
        for (int i = 0; i < iterationMax; i++) {
            c = (u + l)/2;    
            if (this.getError(l, sumLaptopPower) < this.getError(u, sumLaptopPower) && this.getError(c, sumLaptopPower) < this.getError(u, sumLaptopPower)) {
                u = c;
            }
            else if (this.getError(c, sumLaptopPower) < this.getError(l, sumLaptopPower) && this.getError(u, sumLaptopPower) < this.getError(l, sumLaptopPower)) {
                l = c;
            }
            else 
                break;
        }
        double[] dichotomyResults = new double[2];
        dichotomyResults[0] = c;
        dichotomyResults[1] = (u - l)/2;
        return dichotomyResults;
    }
    
    // Method to estimate occupancy
    public void estimateMotions() throws IOException {
        // Create a list of motions data
        DataContainer dataEstimateMotion = new DataContainer("office.csv"); 
        Double[] motionsDetected = dataEstimateMotion.getData("detected_motions");
        // Loop through the list and add the number of people to the dictionary
        for (int i = 0; i < dataEstimateMotion.getNumberOfSamples(); i++) {
            this.peopleMotions.add(this.coefficient*motionsDetected[i]);
        }
    }
}
