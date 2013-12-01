/*
examines 4 points at a time and checks whether they all lie on the same line 
segment, printing out any such line segments to standard output and drawing 
them using standard drawing. To check whether the 4 points p, q, r, and s are 
collinear, check whether the slopes between p and q, between p and r, and 
between p and s are all equal.
*/
import java.util.Arrays;

public class Brute {
    
    private static boolean collinear(Point p, Point q, Point r, Point s)
    {
        return (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s));
    }
        
    
   public static void main(String[] args)
   {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // number of points
        Point[] ap = new Point[N];
        
        // turn on animation mode
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
            ap[i] = p; // add to array
        }
        // sort by natural order 
        Arrays.sort(ap);

        // brute force method, do all combinations 
        for (int p = 0; p < N-3; p++) {
            for (int q = p+1; q < N-2; q++) {
                for (int r = q+1; r < N-1; r++) {
                    for (int s = r+1; s < N; s++) {
                        if (collinear(ap[p],ap[q],ap[r],ap[s]))
                        {
                            ap[p].drawTo(ap[s]);
                            StdOut.println(ap[p] + " -> " + ap[q] + " -> " + ap[r] + " -> " + ap[s]);
                        }
                    }
                }
            }
        }
        
        StdDraw.show(0);
   }
}