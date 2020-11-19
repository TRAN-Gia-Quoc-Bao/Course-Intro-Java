package data;

import java.io.IOException;
import java.util.ArrayList;

public class EstimatorLaptopConsumption {
    private int laptopNumberX;
    private int zoneNumberY;
    private final ArrayList<Integer> peopleXY = new ArrayList<>(); 

    public EstimatorLaptopConsumption() {
    }
    
    public EstimatorLaptopConsumption(int laptopNumber, int zoneNumber) throws IOException {
        this.laptopNumberX = laptopNumber;
        this.zoneNumberY = zoneNumber;
    }

    public int getLaptopNumber() {
        return this.laptopNumberX;
    }
    
    public int getZoneNumber() {
        return this.zoneNumberY;
    } 
    
    public ArrayList<Integer> getNumberPeople() {
        return this.peopleXY;
    }
    
    // Method to determine if someone is using the laptop
    public void estimateLaptop() throws IOException {
        DataContainer dataContainer = new DataContainer("office.csv");
        // Create a list with the power consumption data
        Double[] powerUsed = dataContainer.getData("power_laptop"+String.valueOf(this.laptopNumberX)+"_zone"+String.valueOf(this.zoneNumberY));
        // Loop through the list and add 1 or 0 to the dictionary
        for (int i = 0; i < dataContainer.getNumberOfSamples(); i++) {
            if (powerUsed[i] >= 15) 
                this.peopleXY.add(1);
            else this.peopleXY.add(0);
        }
    }
}