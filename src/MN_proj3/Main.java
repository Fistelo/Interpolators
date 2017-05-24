package MN_proj3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    final static int DELETING_STEP = 2;
    final static int MAP_NUMBER = 4;
    final static String MAP_PATH = "maps/1.csv";
    
    public static void main (String args[])
    {
        DataKeeper inputData = translateCSV(MAP_PATH);
        DataKeeper interpolationData = inputData.divide(DELETING_STEP);
        interpolationData.showData();
        System.out.println();
        findPoints(new LagrangeInterpolator(interpolationData), inputData, interpolationData);
        findPoints(new SplineInterpolator(interpolationData),  inputData, interpolationData);
    }
    
    private static void findPoints(Interpolator interpolator, DataKeeper data, DataKeeper interpolationData){
    
        DataKeeper dk = new DataKeeper();
        data.showData();
        for(int i =0;i<data.getHeights().size()-1;i++){
            dk.addParams(data.getDistances().get(i), interpolator.interpolate(data.getDistances().get(i)));
        }
        System.out.println();
        dk.showData();
        DataKeeper fullSolution = DataKeeper.join(interpolationData, dk);
        fullSolution.saveDataToFile(interpolator.getClass().getSimpleName() + MAP_NUMBER + "fullsolution");
        
        dk.findDisparity(data);
        dk.saveDataToFile(interpolator.getClass().getSimpleName() + MAP_NUMBER);
    }
    

    
    private static DataKeeper translateCSV(String path){
    
        String splitSign = ",";
        String text;
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
        
            while ((text = br.readLine()) != null) {
                x.add(text.split(splitSign)[0]);
                y.add(text.split(splitSign)[1]);
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new DataKeeper(x,y);
    }
}
