public class Percolation {
   
    private boolean[] site;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uff;
    private int n;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)              
    {
        // bottom and top virtual sites + 2
        site = new boolean[N*N+2];
        for (int k = 0; k < N*N+2; k++) site[k] = false; 
        
        // initialize unionfind
        uf = new WeightedQuickUnionUF(N*N+2);
        // connect to virtual top
        for (int k = 1; k <= N; k++) uf.union(0,k); 
        // connect to virtual bottom 
        for (int k = (N-1)*N+1; k <= N*N; k++) uf.union(N*N+1,k);
        
        // used for checking whether full, only connects to top
        uff = new WeightedQuickUnionUF(N*N+1); 
        // connect to virtual top
        for (int k = 1; k <= N; k++) uff.union(0,k); 
        n = N;
    }
    
    // converts 2D index to 1D index: (row i, col j)
    private int Inds2Dto1D(int i, int j)   
    {   // i,j are index 1
        return (i-1)*this.n+j; 
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)    
    {
        if (i <= 0 || i > this.n || j <= 0 || j > this.n) 
            throw new IndexOutOfBoundsException("index out of bounds");
        return site[Inds2Dto1D(i,j)];
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j)         
    {    
        int k = Inds2Dto1D(i,j);
        if (!isOpen(i,j))
        {
            site[k] = true; // mark site as open 
            // connect it to adjacent open sites 
            if (i>1 && isOpen(i-1,j)){
                uf.union(Inds2Dto1D(i-1,j),k);
                uff.union(Inds2Dto1D(i-1,j),k);
            }
            if (j>1 && isOpen(i,j-1)){
                uf.union(Inds2Dto1D(i,j-1),k);
                uff.union(Inds2Dto1D(i,j-1),k);
            }
            if (i<this.n && isOpen(i+1,j)){
                uf.union(Inds2Dto1D(i+1,j),k);
                uff.union(Inds2Dto1D(i+1,j),k);
            }
            if (j<this.n && isOpen(i,j+1)){
                uf.union(Inds2Dto1D(i,j+1),k);
                uff.union(Inds2Dto1D(i,j+1),k);
            }
        }
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)    
    {
        //StdOut.println(Inds2Dto1D(i,j) + " "+uf.connected(Inds2Dto1D(i,j),0));
        if (isOpen(i,j)) return uff.connected(Inds2Dto1D(i,j),0); // connected to top?
        else return false;
    }
    
    public boolean percolates()  // does the system percolate?
    {
        if (n==1)
            return isOpen(1,1);
        else
            return uf.connected(0,this.n*this.n+1); // top connected to bottom?
    }
}