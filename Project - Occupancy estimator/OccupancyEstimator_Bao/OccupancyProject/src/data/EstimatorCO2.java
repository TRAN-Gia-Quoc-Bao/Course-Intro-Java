package data;

import java.io.IOException;
import java.util.ArrayList;

public class EstimatorCO2 {
    private final int S_BREATH = 4;
    private final int T_S = 3600;
    private final int Q_OUT = 395;
    private final int V = 55;
    private double q0Out = (double) 25/3600;
    private double q0Corridor = (double) 25/3600;
    private double qWindowOut = (double) 150/3600;
    private double qDoorCorridor = (double) 150/3600;
    private final ArrayList<Double> qOutT = new ArrayList<>(); 
    private final ArrayList<Double> qCorridorT = new ArrayList<>();
    private final ArrayList<Double> alpha = new ArrayList<>(); 
    private final ArrayList<Double> betaOut = new ArrayList<>(); 
    private final ArrayList<Double> betaCorridor = new ArrayList<>(); 
    private final ArrayList<Double> betaN = new ArrayList<>(); 
    private final ArrayList<Double> peopleCO2 = new ArrayList<>(); 

    public EstimatorCO2() {
    }

    public double getQ0Out() {
        return q0Out;
    }

    public double getQ0Corridor() {
        return q0Corridor;
    }

    public double getQWindowOut() {
        return qWindowOut;
    }

    public double getQDoorCorridor() {
        return qDoorCorridor;
    }

    public ArrayList<Double> getPeopleCO2() {
        return peopleCO2;
    }
    
    // Method to calculate the coefficients alpha, beta, people
    public void calculateCO2(double[] x) throws IOException {
        DataContainer dataCalculateCoefficients = new DataContainer("office.csv");
        Double[] windowOpening = dataCalculateCoefficients.getData("window_opening");
        Double[] doorOpening = dataCalculateCoefficients.getData("door_opening");
        Double[] officeConcentration = dataCalculateCoefficients.getData("office_CO2_concentration");
        Double[] corridorConcentration = dataCalculateCoefficients.getData("corridor_CO2_concentration");
        
        for (int i = 0; i < dataCalculateCoefficients.getNumberOfSamples(); i++) {
            this.qOutT.add(x[0] + x[2]*windowOpening[i]);
            this.qCorridorT.add(x[1] + x[3]*doorOpening[i]);
            this.alpha.add(Math.exp(-(this.qOutT.get(i) + this.qCorridorT.get(i))*this.T_S/this.V));
            this.betaOut.add(((1 - this.alpha.get(i))*this.qOutT.get(i))/(this.qOutT.get(i) + this.qCorridorT.get(i)));
            this.betaCorridor.add(((1 - this.alpha.get(i))*this.qCorridorT.get(i))/(this.qOutT.get(i) + this.qCorridorT.get(i)));
            this.betaN.add(((1 - this.alpha.get(i))*this.S_BREATH)/(this.qOutT.get(i) + this.qCorridorT.get(i)));
            if (i < dataCalculateCoefficients.getNumberOfSamples() - 1)
                this.peopleCO2.add((officeConcentration[i + 1] - this.alpha.get(i)*officeConcentration[i] - this.betaOut.get(i)*this.Q_OUT - this.betaCorridor.get(i)*corridorConcentration[i])/(this.betaN.get(i)));
            else
                this.peopleCO2.add((officeConcentration[i] - this.alpha.get(i)*officeConcentration[i] - this.betaOut.get(i)*this.Q_OUT - this.betaCorridor.get(i)*corridorConcentration[i])/(this.betaN.get(i)));
        }
    }
    
    // Method to return the error (f function)
    public double getError(double[] x, double[] sumLaptopPower) throws IOException {
        double error = 0;
        this.calculateCO2(x);
        for (int i = 0; i < sumLaptopPower.length; i++) {
//            error = error + Math.abs(Math.abs(this.getPeopleCO2().get(i)) - sumLaptopPower[i]);
        error = error + Math.abs(this.getPeopleCO2().get(i) - sumLaptopPower[i]);
        }
        return error;
    }
    
    // Method to jump in neighborhood (J function)
    public double[] jumpFunctionJ(double[] x, double r) {
        double coeff = 1 + (2*Math.random() - 1)*r; 
        for (int i = 0; i < x.length; i++) {
            x[i] = x[i]*coeff;
        }
        return x;
    }
       
    // Method to use simulated annealing
    public void simulatedAnnealing(int interationMax, double r, double beta, double[] sumLaptopPower) throws IOException {
        // Xc = Xr = Xb (X best) = X0
        double Xc[] = {this.q0Out, this.q0Corridor, this.qWindowOut, this.qDoorCorridor};
        double Xr[] = {this.q0Out, this.q0Corridor, this.qWindowOut, this.qDoorCorridor};
        double Xb[] = {this.q0Out, this.q0Corridor, this.qWindowOut, this.qDoorCorridor};
        // Loop
        for (int k = 0; k < interationMax - 1; k++) {
            Xc = jumpFunctionJ(Xr, r);
            if (getError(Xc, sumLaptopPower) < getError(Xb, sumLaptopPower)) {
                Xb = Xc;
            }
            double T = beta*getError(Xr, sumLaptopPower)*(interationMax - k)/interationMax;
            if (Math.random() < Math.exp((getError(Xr, sumLaptopPower) - getError(Xc, sumLaptopPower))/(k*T))) {
                Xr = Xc;
            }
        }
        this.q0Out = Xc[0];
        this.q0Corridor = Xc[1];
        this.qWindowOut = Xc[2];
        this.qDoorCorridor = Xc[3];
    }
}
