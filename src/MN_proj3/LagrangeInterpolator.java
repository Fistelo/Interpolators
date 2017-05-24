package MN_proj3;

import java.util.ArrayList;

/**
 * Created by rados on 17.05.2017.
 */
public class LagrangeInterpolator extends Interpolator {
    
    private ArrayList<Double> x;
    private ArrayList<Double> y;
    
    public LagrangeInterpolator(DataKeeper dataKeeper){
       
         x = dataKeeper.getDistances();
         y = dataKeeper.getHeights();
    }
    
    @Override
    public double interpolate(double wantedX) {
        double sum = 0, term;
    
        for (int i = 0; i < x.size(); ++i) {
            term = y.get(i);
            for (int j = 0; j < x.size(); ++j) {
                if (i != j)
                    term *= (wantedX - x.get(j)) / (x.get(i) - x.get(j));
            }
            sum += term;
        }
        return sum;
    }
}
