import java.util.Iterator;

public class TestRandomizedQueue
{
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        
        for(int i = 1; i<=8; i++)
            d.enqueue(i);
        for (Integer s : d) StdOut.println(s);
        StdOut.println("\n");
        for (Integer s : d) StdOut.println(s);
        
        //StdOut.println("queue: "+ d.toString());
        //StdOut.println("size " + d.size());
        
        //StdOut.println("dequeue " + d.dequeue());
        //StdOut.println("deque: "+ d.toString());
    }
}