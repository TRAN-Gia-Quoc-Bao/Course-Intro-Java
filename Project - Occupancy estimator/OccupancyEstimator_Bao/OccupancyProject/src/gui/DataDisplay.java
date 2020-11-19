package gui;

import data.DataContainer;
import data.EstimatorLaptopConsumption;
import data.EstimatorMotionsDetection;
import data.EstimatorCO2;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

    public class DataDisplay extends JFrame implements ActionListener {
    // Variables 
    public JPanel graph = new JPanel();
    public DataContainer data;
    public JFrame frame; 
    public JCheckBox[] myCheckboxes; 
    public String[] listVariablesPlotted; 
    public String[] listVariables; 
    public int plotCounter = 0; 
    
    // Constructor 
    public DataDisplay() throws IOException{
        
        // Get data from Excel      
        data = new DataContainer("office.csv"); 

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Power consumption based method
        EstimatorLaptopConsumption estimation_Laptop1_Zone1 = new EstimatorLaptopConsumption(1,1);
        estimation_Laptop1_Zone1.estimateLaptop();
        EstimatorLaptopConsumption estimation_Laptop1_Zone2 = new EstimatorLaptopConsumption(1,2);
        estimation_Laptop1_Zone2.estimateLaptop();
        EstimatorLaptopConsumption estimation_Laptop2_Zone2 = new EstimatorLaptopConsumption(2,2);
        estimation_Laptop2_Zone2.estimateLaptop();
        EstimatorLaptopConsumption estimation_Laptop3_Zone2 = new EstimatorLaptopConsumption(3,2);
        estimation_Laptop3_Zone2.estimateLaptop();
        
        double[] sumLaptopPower = new double[data.getNumberOfSamples()];
        for (int i = 0; i < data.getNumberOfSamples(); i++) {
            sumLaptopPower[i] = estimation_Laptop1_Zone1.getNumberPeople().get(i) + estimation_Laptop1_Zone2.getNumberPeople().get(i) + estimation_Laptop2_Zone2.getNumberPeople().get(i) + estimation_Laptop3_Zone2.getNumberPeople().get(i);    
        }
        
        data.addData("LaptopConsumptionEstimator", sumLaptopPower);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Motions detection based method
        EstimatorMotionsDetection estimationMotionsDetection = new EstimatorMotionsDetection();
        estimationMotionsDetection.setCoefficient(estimationMotionsDetection.dichotomy(100, sumLaptopPower)[0]);
        estimationMotionsDetection.estimateMotions();  
        
        double[] numberPeopleMotions = new double[data.getNumberOfSamples()];
        for (int i = 0; i < data.getNumberOfSamples(); i++) {
//            numberPeopleMotions[i] = Math.round(estimationMotionsDetection.getPeopleMotions().get(i)); 
            numberPeopleMotions[i] = estimationMotionsDetection.getPeopleMotions().get(i);
        }
        data.addData("MotionsDetectionEstimator", numberPeopleMotions);
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        // CO2 concentration based method
        EstimatorCO2 estimationCO2Test = new EstimatorCO2();
        estimationCO2Test.simulatedAnnealing(100, 0.01, 1, sumLaptopPower);
        double[] finalCoefficients = {estimationCO2Test.getQ0Out(), estimationCO2Test.getQ0Corridor(), estimationCO2Test.getQWindowOut(), estimationCO2Test.getQDoorCorridor()};
        
        EstimatorCO2 estimationCO2 = new EstimatorCO2();
        estimationCO2.calculateCO2(finalCoefficients);
        
        double[] numberPeopleCO2 = new double[data.getNumberOfSamples()];
        for (int i = 0; i < data.getNumberOfSamples(); i++) {
            numberPeopleCO2[i] = Math.abs(estimationCO2.getPeopleCO2().get(i)); 
        }
        data.addData("CO2ConcentrationEstimator", numberPeopleCO2);
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Create frame
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        // Create plot button
        JPanel plotSelection = new JPanel();
        plotSelection.setLayout(new GridLayout(data.getNumberOfVariables() + 1,1));
        JButton plot = new JButton("Plot");
        plot.addActionListener(this);

        // Add checkboxes and the plot button   
        myCheckboxes = new JCheckBox[data.getNumberOfVariables()]; 
        listVariables = data.getAvailableVariables(); 
                for (int i = 0; i < data.getNumberOfVariables(); i++){
                    JCheckBox g = new JCheckBox(listVariables[i], null, false);
                    myCheckboxes[i] = g; 
                    plotSelection.add(g);
                }
        
        plotSelection.add(plot);
        
        // Plot commands
        listVariablesPlotted = new String [data.getNumberOfVariables()];
        PlotTimeChart newPlot = new PlotTimeChart(data, listVariablesPlotted, plotCounter);
        graph.add(newPlot.getChartPanel("Measurements"));
        BufferedImage myPicture = ImageIO.read(new File("names.jpg"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        // To design the GUI 
        frame.add(plotSelection, BorderLayout.WEST);
        frame.add(graph, BorderLayout.CENTER);  
        frame.add(picLabel, BorderLayout.EAST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);  
        }
     
        // To plot
        @Override
        public void actionPerformed(ActionEvent e) {
            String namesOfPlottedVariables = new String();
            plotCounter = 0;
            for (int i = 0; i < data.getNumberOfVariables(); i++){
                if (myCheckboxes[i].isSelected()){
                    listVariablesPlotted[plotCounter] = listVariables[i];
                    if (plotCounter == 0) {
                    namesOfPlottedVariables = namesOfPlottedVariables + listVariables[i];
                    }
                    else {
                    namesOfPlottedVariables = namesOfPlottedVariables + " & " + listVariables[i];    
                    }
                    plotCounter+=1; 
                }
            }        
            PlotTimeChart newPlot = new PlotTimeChart(data, listVariablesPlotted, plotCounter);
            graph.removeAll();
            graph.add(newPlot.getChartPanel(namesOfPlottedVariables));
            frame.add(graph, BorderLayout.CENTER);
            frame.repaint(); 
            frame.setVisible(true);
            frame.pack();
        }
    }     
