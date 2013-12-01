import java.util.Arrays;
import java.lang.Math;

public class Board {
    
    private final short[][] tiles;
    //private final int[][] goal;
    private int N;
    private int blank_x, blank_y;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        // length of block
        N = blocks.length;
        // copy into instance variable
        tiles = new short[N][N];
        // define goal for size N
        // goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = (short)blocks[i][j];
                if (blocks[i][j] == 0)
                { 
                    blank_x = i;
                    blank_y = j;
                }
                //if (!(i==N-1 && j==N-1)) goal[i][j] = i*N+j+1;
            }
        }
    }
    
    // make a copy of existing array & exchange locations [i1][j1] w/ [i2][j2]
    private int[][] exch(int i1, int j1, int i2, int j2)
    {
        // copy 2 d array 
        int[][] newtiles = new int[N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
                newtiles[i][j] = (int)tiles[i][j];
        // swap keys 
        int swap = newtiles[i1][j1];
        newtiles[i1][j1] = newtiles[i2][j2];
        newtiles[i2][j2] = swap;
        return newtiles;
    }
    
    // board dimension N
    public int dimension()
    {
        return N;
    }
        
    // number of blocks out of place
    public int hamming() 
    {
        int blocksOutOfPlace = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (!(i==N-1 && j==N-1))
                    if (tiles[i][j] != i*N+j+1)
                        blocksOutOfPlace++;
        return blocksOutOfPlace;
    }
            
        
    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        int blocksOutOfPlace = 0;
        int goal_x, goal_y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) { 
                if (tiles[i][j] == 0) 
                    continue;
                else
                {
                    if (tiles[i][j] != i*N+j+1) {
                        goal_x = (tiles[i][j]-1) / N;
                        goal_y = (tiles[i][j]-1) % N;
                        blocksOutOfPlace += Math.abs(i-goal_x)+Math.abs(j-goal_y);
                    }
                }
            }
        }
        return blocksOutOfPlace;
    }
        
    // is this board the goal board?
    public boolean isGoal()
    {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (!(i==N-1 && j==N-1))
                    if (tiles[i][j] != i*N+j+1) 
                        return false;
        return true;
        //return Arrays.deepEquals(tiles, goal);
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin()
    { 
        int[][] newtiles;
        // swap [0][0] with [0][1] or [1][0] with [1][1]
        if (tiles[0][0]!=0 && tiles[0][1]!=0)
            newtiles = exch(0,0,0,1);
        else
            newtiles = exch(1,0,1,1);
        Board tw = new Board(newtiles);
        return tw;
    }
        
    // does this board equal y?    
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles,that.tiles);
    }
    
    // all neighboring boards   
    public Iterable<Board> neighbors()
    {
        Stack<Board> nbs = new Stack<Board>();
        if (blank_x > 0)
        {
            int[][] newtiles = exch(blank_x,blank_y,blank_x-1,blank_y);
            nbs.push(new Board(newtiles));
        }
        if (blank_y > 0)
        {
            int[][] newtiles = exch(blank_x,blank_y,blank_x,blank_y-1);
            nbs.push(new Board(newtiles));
        }
        if (blank_x < N-1)
        {
            int[][] newtiles = exch(blank_x,blank_y,blank_x+1,blank_y);
            nbs.push(new Board(newtiles));
        }
        if (blank_y < N-1)
        {
            int[][] newtiles = exch(blank_x,blank_y,blank_x,blank_y+1);
            nbs.push(new Board(newtiles));
        }
        return nbs;     
    }
        
    // string representation of the board (in the output format specified below)
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
