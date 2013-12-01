import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot
    
    // resize the underlying array
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }
    
    // construct an empty randomized queue
    public RandomizedQueue()           
    {
        q = (Item[]) new Object[2];
    }
    
    // is the queue empty?
   public boolean isEmpty()       
   {
       return N == 0;
   }
   
   // return the number of items on the queue
   public int size()                  
   {
       return N;
   }
   
   // add the item
   public void enqueue(Item item)     
   {   
       if (item == null)
           throw new NullPointerException("cannot add null item"); 
       // double size of array if necessary and recopy to front of array
       if (N == q.length) resize(2*q.length);   // double size of array if necessary
       q[last++] = item;                        // add item
       if (last == q.length) last = 0;          // wrap-around
       N++;
       
   }
   
   // delete and return a random item
   public Item dequeue()          
   {
       if (isEmpty()) 
           throw new NoSuchElementException("RandomizedQueue underflow");
       
       // random between 0 and N-1
       int r2 = (first + StdRandom.uniform(N)) % q.length;
       Item item = q[r2];
       q[r2] = q[first]; // swap with item in the beginning 
       q[first] = null; // to avoid loitering
       N--;
       first++;
       if (first == q.length) first = 0; // wrap-around
       // shrink size of array if necessary
       if (N > 0 && N == q.length/4) resize(q.length/2); 
       return item;
   }
   
   // return (but do not delete) a random item
   public Item sample()               
   {
       if (isEmpty()) 
           throw new NoSuchElementException("RandomizedQueue underflow");
       int r = StdRandom.uniform(N); // random between 0 and N-1
       return q[(first + r) % q.length];
   }
   
   // knuth shuffle
   private void shuffle()
   {
        for (int i = 0; i < N; i++)
        {
            int r = i + (int) (Math.random() * (N - i));
            int r2 = (r + first) % q.length;
            int i2 = (i + first) % q.length;
            Item swap = q[r2];
            q[r2] = q[i2];
            q[i2] = swap;
            //StdOut.println(i2 + " " + r2);
        }
   }
   
   // return an independent iterator over items in random order
   public Iterator<Item> iterator()   
   {
       shuffle();
       return new ArrayIterator();
   }
   
   // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> 
    {
        private int i = 0;
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }
   
    
    /*
   public String toString() {
       StringBuilder s = new StringBuilder();
       for (Item item : this)
            s.append(item + " ");
       return s.toString();
   }
   */
   
}