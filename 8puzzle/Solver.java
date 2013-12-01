import java.util.Comparator;

public class Solver {
    private MinPQ<searchNode> pq;
    private MinPQ<searchNode> pqTwin;
    //private int minMoves;
    private searchNode node;
    private searchNode nodeTwin;
    private boolean isSolved;
    
    // helper search node class 
    private class searchNode implements Comparable<searchNode>
    {
        private int moves;
        private Board board;
        private searchNode prevnode;
        
        public searchNode (int m, Board b, searchNode s) 
        { 
            moves = m;
            board = b;
            prevnode = s;
        }
        
        public int compareTo (searchNode that)
        {    
            return (this.moves + this.board.manhattan()) - (that.moves + that.board.manhattan());
        }
        
    }
    
    private void printStep(searchNode node)
    {
        int priority = node.moves + node.board.manhattan();
        StdOut.println("priority  = " + priority);
        StdOut.println("moves     = " + node.moves);
        StdOut.println("manhattan = " + node.board.manhattan());
        StdOut.println(node.board);
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) 
    {
        // initialize priority queue
        pq = new MinPQ<searchNode>();
        // initial first search node and insert into priority queue
        pq.insert(new searchNode(0, initial, null));
        
        // initialize priority queue with twin board
        pqTwin = new MinPQ<searchNode>();
        pqTwin.insert(new searchNode(0, initial.twin(), null));
        
        while(!pq.isEmpty())
        {
            //delete from PQ the search node with the minimum priority
            node = pq.delMin();
            //StdOut.println("Step " + node.moves);
            //printStep(node);
            Board b = node.board;
            if (b.isGoal()) 
            { 
                isSolved = true;
                break;
            }
            
            // do the same with twin board 
            nodeTwin = pqTwin.delMin();
            //StdOut.println("twin");
            //printStep(nodeTwin);
            Board b2 = nodeTwin.board;
            if (b2.isGoal())
            {
                isSolved = false;
                break;
            }
                 
            //insert all neighboring search nodes
            for (Board board : b.neighbors())
            {
                if (node.prevnode==null || !board.equals(node.prevnode.board))
                {
                    //StdOut.println("neighbor " + node.moves + " " + board);
                    pq.insert(new searchNode(node.moves+1, board, node));
                }
            }
            
            // do the same for twin board
            for (Board board : b2.neighbors())
            {
                if (node.prevnode==null || !board.equals(nodeTwin.prevnode.board))
                    pqTwin.insert(new searchNode(nodeTwin.moves+1, board, nodeTwin));
            }
            //minMoves++;
        } 
    }
    
    // is the initial board solvable?
    public boolean isSolvable()
    {
        return isSolved;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() 
    {
        if (isSolvable())
            return node.moves;
        else
            return -1;
    }
        
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() 
    {
        if (isSolvable())
        {
            Stack<Board> sol = new Stack<Board>();
            searchNode currnode = node;
            while(currnode != null)
            {
                //StdOut.println(currnode.moves);
                sol.push(currnode.board);
                currnode = currnode.prevnode;
            }
            return sol;
        }
        else
            return null;
    }
        
        
    // solve a slider puzzle (given below)    
    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}