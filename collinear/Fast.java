/*
Think of p as the origin.
For each other point q, determine the slope it makes with p.
Sort the points according to the slopes they makes with p.
Check if any 3 (or more) adjacent points in the sorted order have equal 
slopes with respect to p. If so, these points, together with p, are collinear.
*/
import java.util.Arrays;

public class Fast {
    
   public static void main(String[] args)
   {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // number of points
        Point[] ap = new Point[N];
        double[] apOnscreen = new double[N];
        for (int i = 0; i < N; i++) apOnscreen[i] = Double.NEGATIVE_INFINITY; 
        //Point[] ap2 = new Point[N];
        
        // turn on animation mode and scale
        StdDraw.clear();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        //StdDraw.setPenColor(StdDraw.RED);
        
        // load into array 
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            //StdOut.println(p.toString());
            ap[i] = p;
        }
        // sort by natural order -- merge sort (stable)
        Arrays.sort(ap);
        
        double initslope;
        double currslope;
        double keepslope;
        
        // for each point q, determine the slope it makes with p
        // sort by slopes
        for (int p = 0; p < N-1; p++) 
        {
            // copy onto second array (keeps natural order)
            Point[] ap2 = new Point[N];
            for (int j = p+1; j < N; j++) ap2[j] = ap[j];
            
            // sort relative to ap[p], the reference point, by slope order 
            Arrays.sort(ap2, p+1, N, ap[p].SLOPE_ORDER);
            
            // for debugging, checking array is sorted by slope
            /*
            for (int j = p+1; j < N; j++)
                StdOut.println(p + " " +  j + " " + ap2[j] + " slope " + ap[p].slopeTo(ap2[j]) + "--");
            StdOut.println();
            */
            
            // find continguous points with the same slope 
            int j = p+1; // index for for first point 
            initslope = ap[p].slopeTo(ap2[j]); // slope to first point 
            int count = 1;
            while (j < N-1)
            {   // slope to next point, increment pointer
                currslope = ap[p].slopeTo(ap2[1 + j++]);
                if (currslope == initslope)
                    count++;
                else
                {
                    initslope = currslope;
                    if (count >= 3)
                    {   
                        j--;
                        break;
                    }
                    else count = 1;
                }
                //StdOut.println("while loop " + p + " " + ap[p] + " j" +  j + " c" + count + " " + initslope + " " + currslope);
            }
            //StdOut.println("exited loop " + count + " " + j);
            
            // check collinearity
            if (count >= 3) 
            {
                // save slope in last point 
                currslope = ap[p].slopeTo(ap2[j]);
                //StdOut.println(apOnscreen[j]);
                
                if (currslope != apOnscreen[j])
                {
                    // print to standard output
                    StdOut.print(ap[p]);
                    for (int q = j-count+1; q <= j; q ++)
                        StdOut.print(" " + q + " -> " + ap2[q]);
                    StdOut.println();
                    // draw line
                    ap[p].drawTo(ap2[j]);
                    // save current slope
                    apOnscreen[j] = currslope;
                }
            }
        }       
        StdDraw.show(0);
   }
}