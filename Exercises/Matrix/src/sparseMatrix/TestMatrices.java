package sparseMatrix;

import java.util.Arrays;

public class TestMatrices {
    
    public static void main(String[] args) throws MatrixException {
        // Create the matrix of size 3 x 4
        SparseMatrix sparseMatrix = new SparseMatrix(3, 4);
        
        // Print the (address of) the newly created matrix
        System.out.println(sparseMatrix+ "\n");
        
        // Printing the size of the matrix
        System.out.println(Arrays.toString(sparseMatrix.getSize())+ "\n");
                
        // Now we give some (non zero) values to the matrix
        sparseMatrix.setValue(0, 2, 1);
        sparseMatrix.setValue(0, 3, 4); 
        sparseMatrix.setValue(1, 1, 5);
        sparseMatrix.setValue(1, 2, 6);
        sparseMatrix.setValue(0, 1, 1);
        sparseMatrix.setValue(1, 3, 4); 
        sparseMatrix.setValue(2, 1, 5);
        sparseMatrix.setValue(2, 2, 6);
        
        // Check by printing out the values
        System.out.println(sparseMatrix.getValue(0, 2));
        System.out.println(sparseMatrix.getValue(0, 3));
        System.out.println(sparseMatrix.getValue(1, 1));
        System.out.println(sparseMatrix.getValue(1, 2));
        
        // Display all elements in the matrix
        sparseMatrix.displayMatrix();
        
        // Transpose the matrix
        SparseMatrix transposedMatrix = sparseMatrix.transpose();
        System.out.println(transposedMatrix.getValue(2, 0));
        
        // Display all elements in the transposed matrix
        transposedMatrix.displayMatrix();        
        
       // By printing the sparse matrix again, we see that it's not altered
        sparseMatrix.displayMatrix();
        
       // Create the matrix of size 3 x 3
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix(3);
        
       // Print the (address of) the newly created matrix
        System.out.println(diagonalMatrix+ "\n");
        
        // Printing the size of the matrix
        System.out.println(Arrays.toString(diagonalMatrix.getSize())+ "\n");
                
        // Now we give some (non zero) values to the matrix
        diagonalMatrix.setValue(0, 0, 1);
        diagonalMatrix.setValue(1, 1, 4); 
        diagonalMatrix.setValue(2, 2, 5);
        
        // We print out 1 of the elements of the diagonal matrix
        System.out.println(diagonalMatrix.getValue(0,0));
        
        // We display the diagonal matrix
        diagonalMatrix.displayMatrix();
        
        // Transpose the diagonal matrix, create a SparseMatrix
       SparseMatrix transposedDiagonalMatrix = diagonalMatrix.transpose();
        
        // Transpose the diagonal matrix, create a DigonalMatrix 
//        DiagonalMatrix transposedDiagonalMatrix = diagonalMatrix.transpose();
        
        // We display the diagonal matrix
        transposedDiagonalMatrix.displayMatrix();
    }
}
