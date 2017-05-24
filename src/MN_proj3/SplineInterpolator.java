package MN_proj3;


import java.util.ArrayList;

public class SplineInterpolator extends Interpolator{
    
    private ArrayList<Double> distances;
    private ArrayList<Double> heights;
    private double[] m;
    
    public SplineInterpolator(DataKeeper dataKeeper) {
        distances = dataKeeper.getDistances();
        heights = dataKeeper.getHeights();
        setup(distances, heights);
    }
    
    public void setup(ArrayList<Double> x, ArrayList<Double> y) {
        
        final int size = x.size();
        double[] slopes = new double[size - 1];
        m = new double[size];
        
        for (int i = 0; i < size - 1; i++) {
            double dx = x.get(i+1) - x.get(i);
            slopes[i] = (y.get(i+1) - y.get(i)) / dx;
        }
        
        
        m[0] = slopes[0];
        for (int i = 1; i < size - 1; i++) {
            m[i] = (slopes[i - 1] + slopes[i]) * 0.5f;
        }
        m[size - 1] = slopes[size - 2];
        
        for (int i = 0; i < size - 1; i++) {
            if (slopes[i] == 0f) {
                m[i] = 0f;
                m[i + 1] = 0f;
            } else {
                double alpha = m[i] / slopes[i];
                double beta = m[i + 1] / slopes[i];
                double h = Math.hypot(alpha, beta);
                if (h > 9f) {
                    double t = 3f / h;
                    m[i] = t * alpha * slopes[i];
                    m[i + 1] = t * beta * slopes[i];
                }
            }
        }
    }
    
    @Override
    public double interpolate(double x) {
        
        int i = 0;
        while (x >= distances.get(i+1)) {
            i += 1;
            if (x == distances.get(i))
                return heights.get(i);
        }
        
        double xupper = distances.get(i+1);
        double xlower = distances.get(i);
        double h = xupper - xlower;
        double t = (x - xlower) / h;
        return (heights.get(i) * (1 + 2 * t) + h * m[i] * t) * (1 - t) * (1 - t)
                + (heights.get(i+1) * (3 - 2 * t) + h * m[i + 1] * (t - 1))* t * t;
   
    }
}