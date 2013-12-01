//import java.util.Arrays;
public class PercolationStats {
    private double[] test; // holds percolation threshold for each trial 
    private int T;
        
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {    
       if (N <= 0 || T <= 0) 
            throw new IllegalArgumentException("arguments out of bounds");
       
       test = new double[T];
       int i; 
       int j;
       // for each iteration/trial
       for (int k = 0; k < T; k++) 
       {
           Percolation perc = new Percolation(N); // create new percolation object
           // shuffle indices for sites to open 
           //shuffledInds = Shuffle(inds);          
           // create an array of indices from 0 to N^2-1
           int[] shuffledInds = new int[N*N];
           for (int z = 0; z < N*N; z++) shuffledInds[z] = z;
           // randomly shuffle a set of values 1:N
           for (int z = 0; z < N*N; z++)
           {
               int r = z + (int) (Math.random() * (N*N - z));
               int swap = shuffledInds[r];
               shuffledInds[r] = shuffledInds[z];
               shuffledInds[z] = swap;
           }
           //StdOut.println(Arrays.toString(shuffledInds));
           
           int p = 0;
           while (!perc.percolates() && p < N*N) 
           {
               // figure out current i,j from p 
               i = shuffledInds[p] / N + 1;
               j = shuffledInds[p] % N + 1;
               //StdOut.println("p = " + p + " " + \
               //shuffledInds[p] + " i,j " + i + " " + j);
               // open site 
               perc.open(i,j);
               p++;
           }
           test[k] = (double)(p+1.0)/(N*N);
       }
       this.T = T;
    }
     
    // sample mean of percolation threshold
    public double mean()                    
    {
        double sum = 0.0;
        for (int k = 0; k < T; k++) sum += test[k];
        return sum/T;
    }
    
    // sample standard deviation of percolation threshold
   public double stddev()                   
   {
       double std = 0.0;
       double m = mean();
       for (int k = 0; k < T; k++) std += Math.pow((test[k] - m),2);
       return Math.sqrt(std/(T-1));
   }
   
   // returns lower bound of the 95% confidence interval
   public double confidenceLo()             
   {
       return mean() - 1.96*stddev()/Math.sqrt(T);
   }
   
   // returns upper bound of the 95% confidence interval
   public double confidenceHi()             
   {
       return mean() + 1.96*stddev()/Math.sqrt(T);
   }
   
   // test client, described below
   public static void main(String[] args)   
   {
       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       PercolationStats ps = new PercolationStats(N,T);
       StdOut.println("N = " + N +"  T = "+T);
       StdOut.println("mean                    = " + ps.mean());
       StdOut.println("stddev                  = " + ps.stddev());
       StdOut.println("95% confidence interval = " + ps.confidenceLo() + " " + ps.confidenceHi());
   }
   
}