package sparseMatrix;

    public class MatrixElementCoordinate implements Comparable<MatrixElementCoordinate> {
        private int i;
        private int j;

    public MatrixElementCoordinate(int i, int j) throws MatrixException {
        if (i < 0 || j < 0) {
                throw new MatrixException("You can't enter those coordinates");
            }
        this.i = i;
        this.j = j;
    }
    
    public int getI(){
        return this.i;
    }
    
    public int getJ(){
        return this.j;
    }
    
    @Override
    public int compareTo(MatrixElementCoordinate otherMatrixElementCoordinate)
    {
        int ix = otherMatrixElementCoordinate.getI();
        if (otherMatrixElementCoordinate.getI() == this.getI()) {
            int iy = otherMatrixElementCoordinate.getJ();
            if (this.getJ() == otherMatrixElementCoordinate.getJ()) {
                return 0;
            }
            else if (this.getJ() < otherMatrixElementCoordinate.getJ()) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else if (this.getI() < otherMatrixElementCoordinate.getI()) {
            return -1;
        }
        else if (this.getI() > otherMatrixElementCoordinate.getI()) {
            return 1;
        }
        else
            throw new UnsupportedOperationException("Not supported yet");
    }
    
}
