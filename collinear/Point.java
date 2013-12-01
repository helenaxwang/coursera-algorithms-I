import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {
    
    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    
    private final int x; // x coordinate
    private final int y; // y coordinate

   // construct the point (x, y)
   public Point(int x, int y)
   {
       this.x = x;
       this.y = y;
   }
   
   private class SlopeOrder implements Comparator<Point> 
   {
       public int compare (Point a, Point b)
       {
           double s1 = Point.this.slopeTo(a);
           double s2 = Point.this.slopeTo(b);
           if (s1 < s2) return -1;
           if (s1 > s2) return +1;
           return 0;
       }
   }
   
   // draw this point
   public void draw()
   {
       StdDraw.point(x, y);
   }
   
   // draw the line segment from this point to that point
   public void drawTo(Point that)
   {
        StdDraw.line(this.x, this.y, that.x, that.y);
   }
   
   // string representation
   public String toString()
   {
       return "(" + x + ", " + y + ")";
   }
   
   // is this point lexicographically smaller than that point?
   // compare points by their y-coordinates, breaking ties by their x-coordinates
   public int compareTo(Point that)
   {
       if (this.y < that.y) 
           return -1;
       if (this.y > that.y)
           return +1;
       if (this.x < that.x) 
           return -1;
       if (this.x > that.x)
           return +1;
       return 0;
       
   }
   
   // the slope between this point and that point
   public double slopeTo(Point that)
   {
       int dy = that.y - this.y;
       int dx = that.x - this.x;
       if (dy == 0 && dx != 0)
           return +0.0;
       else if (dy != 0 && dx == 0)
           return Double.POSITIVE_INFINITY;
       else if (dy == 0 && dx == 0)
           return Double.NEGATIVE_INFINITY;
       else
           return (double)dy/(double)dx;
   }
   
   // unit test 
   public static void main(String[] args) 
   {
       //create some points 
       Point a = new Point(1,3);
       Point b = new Point(2,3);
       Point c = new Point(2,1);
       
       Point[] arrayPts = {a,b,c};
       /*
       Point[] arrayPts = new Point[5];
       for (int i = 0; i < 5; i ++)
           arrayPts[i] = new Point(2,i);
       */

       // compute slopes 
       StdOut.println("Slope a,b: " + a.slopeTo(b));
       StdOut.println("Slope a,c: " + a.slopeTo(c));
       StdOut.println("Slope b,c: " + b.slopeTo(c));
       StdOut.println("Slope c,c: " + c.slopeTo(c));
       
       // compare points
       StdOut.println("a versus b: " + a.compareTo(b));
       StdOut.println("a versus c: " + a.compareTo(c));
       StdOut.println("a versus a: " + a.compareTo(a));
       
       Arrays.sort(arrayPts, a.SLOPE_ORDER);
       for (int i = 0; i < 3; i++)
           StdOut.println(arrayPts[i].toString());
       
   }
}