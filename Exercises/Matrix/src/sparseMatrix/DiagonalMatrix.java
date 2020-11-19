package sparseMatrix;

import java.util.TreeMap;

public class DiagonalMatrix extends SparseMatrix {
    private int size;

    public DiagonalMatrix(int size) {
        super(size, size);
        this.size = size;
        elements = new TreeMap<MatrixElementCoordinate, Double>();
    }

    @Override
    public void setValue(int i, int j, double value) throws MatrixException {
        if (i != j ) {
                throw new MatrixException("You can't enter different coordinates as the matrix is diagonal");
            }
        else
        super.setValue(i, j, value); 
    }

    @Override
    public double getValue(int i, int j) throws MatrixException {
        if (i != j ) {
                throw new MatrixException("You can't enter different coordinates as the matrix is diagonal");
            }
        else
        return super.getValue(i, j); 
    }
    
    @Override
    public int[] getSize() {
        return new int[] {this.size};
    }

//    @Override
//    public DiagonalMatrix transpose() throws MatrixException {       
//        DiagonalMatrix transposedMatrix = new DiagonalMatrix(this.size);
//        transposedMatrix.elements = this.elements;
//        return transposedMatrix; 
//    }
}
