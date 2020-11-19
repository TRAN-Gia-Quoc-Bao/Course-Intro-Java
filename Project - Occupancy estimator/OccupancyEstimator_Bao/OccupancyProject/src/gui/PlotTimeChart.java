package gui;

import data.DataContainer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class PlotTimeChart {
    TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

    public PlotTimeChart(DataContainer data, String[] listvariables, int numberOfPlot)  {
            DateFormat format = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
            Date[] dates = new Date[data.getNumberOfSamples()]; 

            try {
                for(int i = 0; i < data.getNumberOfSamples(); i++)
                        dates[i] = format.parse(data.getTimeStrings()[i]);
                
                for (int i = 0;  i < numberOfPlot; i++) {
                    Double[] mesures = data.getData(listvariables[i]);
                    TimeSeries timeSeries = new TimeSeries(listvariables[i]);
                    for(int j = 0; j < data.getNumberOfSamples(); j++){
                        timeSeries.add(new Hour(dates[j]),mesures[j]);
                    }
                    timeSeriesCollection.addSeries(timeSeries) ; 
                }               

            } catch (ParseException e) {
            }		        
    }

    public JPanel getChartPanel(String nameOfPlot){
        JPanel chartPanel = new ChartPanel(ChartFactory.createTimeSeriesChart("Graphical display of " + nameOfPlot, "Time", nameOfPlot, timeSeriesCollection, true, true, false));
        return chartPanel;
    }
}
