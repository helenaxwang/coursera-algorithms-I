public class TestBoard
{
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
        
        StdOut.println(initial);
        StdOut.println("dimension: " + initial.dimension());
        StdOut.println("goal?: " + initial.isGoal());
        StdOut.println("hamming score: " + initial.hamming());
        StdOut.println("manhattan score: " + initial.manhattan());
        
        Board twin = initial.twin();
        StdOut.println("twin ");
        StdOut.println(twin);
        //StdOut.println(initial);
        StdOut.println("neighbor ");
        for (Board board : initial.neighbors())
            StdOut.println(board);
        
        Stack<Board> stNb = (Stack<Board>) initial.neighbors();
        Board nb1 = stNb.pop();
        StdOut.println("neightbor 1 " + nb1);
        StdOut.println("same as neighbor? " + initial.equals(nb1));
        Board copy = new Board(blocks);
        StdOut.println("same as copy? " + initial.equals(copy));
    }
}