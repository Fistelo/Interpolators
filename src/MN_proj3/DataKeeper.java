package MN_proj3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by rados on 17.05.2017.
 */
public class DataKeeper {
    private ArrayList<Double> distances;
    private ArrayList<Double> heights;
    
    public DataKeeper(ArrayList<String> distances, ArrayList<String>  heights){
        this.distances = new ArrayList<>();
        this.heights = new ArrayList<>();
        
        for(int i=0;i<distances.size();i++){
            this.distances.add(Double.parseDouble(distances.get(i)));
            this.heights.add(Double.parseDouble(heights.get(i)));
        }
    }
    
    public DataKeeper(){
        this.distances = new ArrayList<>();
        this.heights = new ArrayList<>();
    }
    
    public DataKeeper divide(int k){
    
        ArrayList<String> dist = new ArrayList<>();
        ArrayList<String> heig = new ArrayList<>();
        
        ArrayList<Double> newdist = new ArrayList<>();
        ArrayList<Double> newhei = new ArrayList<>();
        
        for(int i =0; i<distances.size(); i++){
            if(i%k!=0){
                newdist.add(distances.get(i));
                newhei.add(heights.get(i));
            }else{
                dist.add(Double.toString(distances.get(i)));
                heig.add(Double.toString(heights.get(i)));
            }
        }
        distances = newdist;
        heights = newhei;
        
        return new DataKeeper(dist,heig);
    }
    public static DataKeeper join(DataKeeper a, DataKeeper b){
        DataKeeper dk = new DataKeeper();
        for(int i=0;i<a.getHeights().size()-1;i++){
            dk.addParams(b.getDistances().get(i),b.getHeights().get(i));
            dk.addParams(a.getDistances().get(i),a.getHeights().get(i));
        }
        return dk;
    }
    
    public void showData(){
        for(int i =0;i<distances.size();i++){
            System.out.println("X: " + distances.get(i) + "\t Y: " + heights.get(i));
        }
    }
    
    public void saveDataToFile(String name){
        if(distances!= null && heights != null){
            
            Path path = Paths.get("output/" + name + ".txt");
            
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                for(int i = 0;i<distances.size();i++){
                    writer.write(Double.toString(distances.get(i)) +
                            " " + Double.toString(heights.get(i)) + "\n");
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
    
        }else{
            System.out.println("There is no data in DataKeeper!");
        }
        
    }
    public void findDisparity(DataKeeper model){
        for(int i =0;i<distances.size();i++){
            heights.set(i, Math.abs(heights.get(i) - model.getHeights().get(i)));
        }
    }
    
    public void addParams(double d, double h){
        this.heights.add(h);
        this.distances.add(d);
    }
    public ArrayList<Double> getHeights() {return heights;}
    public ArrayList<Double> getDistances() { return distances;}
    
}
