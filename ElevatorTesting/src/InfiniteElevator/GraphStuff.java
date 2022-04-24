/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InfiniteElevator;

/**
 *
 * @author Asher
 */
import java.awt.Dimension;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/* Option
    
    1 - Post Elevator Data Set

    2 - Post Elevator Summary

    3 - Graph for (2) Post Elevator Summary

    4 - Passenger Against Time Line Graph

 */
public class GraphStuff extends ApplicationFrame {

    public GraphStuff(int floor, int option, String applicationTitle, String chartTitle, String sideCat, String botCat, LinkedList e1Data, LinkedList e2Data, int walkedAway, LinkedList<GraphPassTimeTest> passTime) {
        super(applicationTitle);
        if (option == 1) {
            JFreeChart barChart = ChartFactory.createBarChart(
                    chartTitle,
                    botCat,
                    sideCat,
                    postElevatorDataSet(floor, e1Data, e2Data),
                    PlotOrientation.VERTICAL,
                    true, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
            setContentPane(chartPanel);
        } else if (option == 2) {

            //Get Total Passengers (Arrived):
            int totalPass = e1Data.size() + e2Data.size();

            //Get total passengers overall
            int totalPassOverall = totalPass + walkedAway;

            //Jlabel var
            JLabel e1Pass, e2Pass, totalPassLbl, totalPassLblAndWA, walkedAwayPassengers;

            JPanel panel = new JPanel();

            panel.add(new JSeparator());

            //Total E1 Passengers
            e1Pass = new JLabel("Elevator 1 Passengers (Arrived):  " + e1Data.size(), SwingConstants.CENTER);
            e1Pass.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panel.add(e1Pass);

            panel.add(new JSeparator());

            //Total E2 Passengers
            e2Pass = new JLabel("Elevator 2 Passengers (Arrived):  " + e2Data.size(), SwingConstants.CENTER);
            e2Pass.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panel.add(e2Pass);

            panel.add(new JSeparator());

            //Passengers who walked away
            walkedAwayPassengers = new JLabel("Passengers who walked away:  " + walkedAway, SwingConstants.CENTER);
            walkedAwayPassengers.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panel.add(walkedAwayPassengers);

            panel.add(new JSeparator());

            //Total Passengers
            totalPassLbl = new JLabel("Total Passengers (Arrived Successfully): " + totalPass, SwingConstants.CENTER);
            totalPassLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panel.add(totalPassLbl);

            panel.add(new JSeparator());

            //Total Passengers + Walked Away Passengers
            totalPassLblAndWA = new JLabel("Total Passengers (Overall): " + totalPassOverall, SwingConstants.CENTER);
            totalPassLblAndWA.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            panel.add(totalPassLblAndWA);

            panel.add(new JSeparator());

            setContentPane(panel);
            setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            calculatePerformance(e1Data, e2Data, applicationTitle);
            setPreferredSize(new Dimension(560, 367));

        } else if (option == 3) {
            //Get Total Passengers (Arrived):
            int totalPass = e1Data.size() + e2Data.size();

            //Get total passengers overall
            int totalPassOverall = totalPass + walkedAway;

            JFreeChart barChart = ChartFactory.createBarChart(
                    chartTitle,
                    botCat,
                    sideCat,
                    postElevatorSummary(walkedAway, e1Data, e2Data),
                    PlotOrientation.VERTICAL,
                    true, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
            setContentPane(chartPanel);
        } else if (option == 4) {
            
               //Get Total Passengers (Arrived):
            int totalPass = e1Data.size() + e2Data.size();
            
            //Get total passengers overall
            int totalPassOverall = totalPass + walkedAway;
            
            JFreeChart lineGraph = ChartFactory.createLineChart(
                    chartTitle,
                    botCat,
                    sideCat,
                    passengerTimeLineGraph(passTime, totalPass),
                    PlotOrientation.VERTICAL,
                    true, true, false);

            ChartPanel chartPanel = new ChartPanel(lineGraph);
            chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
            setContentPane(chartPanel);
        }

    }

    private CategoryDataset passengerTimeLineGraph(LinkedList<GraphPassTimeTest> passTime, int totalPass) {

        //get all timestamps of the passenger/time class array.
        double[] time = new double[totalPass];
        double convert = 0;
        

        
//        //Put passenger/time class array nano time value in the time array so we can sort it
        for(int i = 0; i < totalPass; i++){
            //System.out.println("PassTime: " + passTime.get(i).getTime());
            double getNanoSeconds = (double)  passTime.get(i).getTime() / 1_000_000;
            time[i] = (double)getNanoSeconds;
            //System.out.println("Convert: " + (double)convert);
        }
        
 
        
        //sort the array of timestamps
        Arrays.sort(time);
        
        for (int i = 0; i < totalPass; i++) {
            System.out.println("Time Print All: " + time[i]);
        }
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String lblPassengers = "Passengers";
        
        //Create dataset for the elevators
        for (int i = 0; i < time.length; i++) {
            dataset.addValue((double)time[i], lblPassengers, passTime.get(i).getPassengerCode());
        }



        return dataset;
    }

    private CategoryDataset postElevatorSummary(int walkedAway, LinkedList e1, LinkedList e2) {
        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        //Get Total Passengers (Arrived):
        int totalPass = e1.size() + e2.size();

        //Get total passengers overall
        int totalPassOverall = totalPass + walkedAway;

        String totalPassArrWA = "Total Passengers (Overall)";
        String totalPassArr = "Total Passengers (Arrived Successfully)";
        String walkedAwayPass = "Passengers who walked away";
        String e1Data = "Elevator 1 Passengers";
        String e2Data = "Elevator 2 Passengers";
        //Create dataset for the elevators
        dataset.addValue((int) totalPassOverall, totalPassArrWA, "");
        dataset.addValue((int) totalPass, totalPassArr, "");
        dataset.addValue((int) walkedAway, walkedAwayPass, "");
        dataset.addValue((int) e1.size(), e1Data, "");
        dataset.addValue((int) e2.size(), e2Data, "");

        return dataset;
    }

    private CategoryDataset postElevatorDataSet(int floor, LinkedList e1Data, LinkedList e2Data) {

        //Elevator data
        LinkedList floore1Data = new LinkedList<>();
        LinkedList floore2Data = new LinkedList<>();

        //Initialize Linked List
        for (int i = 0; i < floor; i++) {
            floore1Data.add(0);
            floore2Data.add(0);
        }

        /*
        First loop through each floor.
        For each floor, loop through the number of passengers dropped off by elevator x.
        Set the number of passengers to each floor.
         */
        for (int i = 0; i < floor + 1; i++) {
            /*
          Example:
           - each cell represents a passenger who has landded on a floor.
          - number in the cell rperesents the floor
          if i = 4
          ->[3] == 4? Nope. [4][2][3]
          [3]->[4] == i? Yes! -> Set [2][3]
             */
            int counterForPassengerFloor = 0;
            for (int x = 0; x < e1Data.size(); x++) {
                if ((int) e1Data.get(x) == (int) i) {
                    //System.out.println("e1Data.get(x) " + e1Data.get(x) + " == floor: " + i);
                    counterForPassengerFloor++;
                    floore1Data.set(i - 1, counterForPassengerFloor);
                }
            }
            counterForPassengerFloor = 0;
            for (int x = 0; x < e2Data.size(); x++) {
                if ((int) e2Data.get(x) == (int) i) {
                    counterForPassengerFloor++;
                    floore2Data.set(i - 1, counterForPassengerFloor);
                }
            }

        }

        //Debug
        System.out.println("Elevator 1 Data: " + e1Data);
        System.out.println("Elevator 1 Graph Data: " + floore1Data);
        System.out.println("Elevator 2 Data: " + e2Data);
        System.out.println("Elevator 2 Graph Data: " + floore2Data);

        final String elevator1 = "Elevator 1";
        final String elevator2 = "Elevator 2";
        String floorString = "Floor ";
        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        //Create dataset for the elevators
        for (int i = 0; i < floor; i++) {
            dataset.addValue((int) floore1Data.get(i), elevator1, floorString + ((int) i + 1));
            dataset.addValue((int) floore2Data.get(i), elevator2, floorString + ((int) i + 1));
        }

        return dataset;
    }

    public void calculatePerformance(LinkedList e1Data, LinkedList e2Data, String applicationTitle) {

    }
}
