import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
   private int N;            // size of the deque
   private Node first, last; // first and last of the deque
      
   // helper linked list class
   private class Node
   {
       private Item item;
       private Node next;
       private Node previous;
   }
   
   // construct an empty deque
   public Deque()
   {
       first = null;
       last = null;
       N = 0;
   }
   
    // is the deque empty?
   public boolean isEmpty()
   {
       return first == null;
   }
   
   // return the number of items on the deque
   public int size()                  
   {
       return N;
   }
    
   // insert the item at the front (same as push)
   public void addFirst(Item item) 
   {
       if (item == null)
           throw new NullPointerException("cannot add null item");
       
       Node oldfirst = first;
       //if (isEmpty()) oldfirst = last;
       //else oldfirst = first;
       // create new first node
       first = new Node();
       first.item = item;
       first.previous = null;
       
       // if just one item, then point last to first 
       if (last == null)
           last = first;
       else
           oldfirst.previous = first;
       
       first.next = oldfirst;       
       N++;
   }
   
   // insert the item at the end (same as enqueue)
   public void addLast(Item item)
   {
       if (item == null)
           throw new NullPointerException("cannot add null item"); 
       
       Node oldlast = last;
       // create new last;
       last = new Node();
       last.item = item;
       last.next = null;
       
       if (isEmpty())
           first = last;
       else
           oldlast.next = last;
           
       last.previous = oldlast;
       N++;
   }
   
   // delete and return the item at the front (same as pop/dequeue)
   public Item removeFirst()          
   {
       if (isEmpty()) 
           throw new NoSuchElementException("Deque underflow");
       
       Item item = first.item;
       first = first.next;
       if (isEmpty()) // if first points to null
           last = null;
       else
       {
           first.previous = null;
           if (first.next == null)
               last = first;
       }
       N--;
       return item;
   }
   
   // delete and return the item at the end 
   public Item removeLast()           
   {
       if (last == null) 
           throw new NoSuchElementException("Deque underflow");
       
       Item item = last.item;
       last = last.previous;
       
       if (last == null)
           first = null;
       else
       {
           last.next = null;
           if (last.previous == null)
               first = last;
       }
       N--;
       return item;
   }
   
   // return an iterator over items in order from front to end
   public Iterator<Item> iterator()  
   {
       return new ListIterator();
   }
  
   // an iterator, doesn't implement remove() since it's optional
   private class ListIterator implements Iterator<Item> 
   {
       private Node current = first;
       public boolean hasNext()  { return current != null;                     }
       public void remove()      { throw new UnsupportedOperationException();  }
       
       public Item next() 
       {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
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
   
    // A test client.
    public static void main(String[] args) 
    {
        Deque<String> s = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) s.addFirst(item);
            else if (!s.isEmpty()) StdOut.print(s.removeFirst() + " ");
        }
        StdOut.println("(" + s.size() + " left on deque)");
    }
   
}