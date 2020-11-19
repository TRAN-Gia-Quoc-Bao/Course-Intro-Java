package sparseMatrix;

import java.util.Arrays;
import java.util.TreeMap;

    public class SparseMatrix {
        private int numberOfLines;
        private int numberOfColumns;
        private int elementCounter;
        TreeMap<MatrixElementCoordinate, Double> elements;

    // Constructor of the sparse matrix
    public SparseMatrix(int numberOfLines, int numberOfColumns) {
        super();
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        this.elementCounter = 0;
        elements = new TreeMap<MatrixElementCoordinate, Double>();
    }

    // Method to set a non zero value to element [i,j] as we only store non zero values
    // If it's successful it'll let you know
    public void setValue(int i, int j, double value) throws MatrixException{
        if (i < 0 || j < 0 || i >= this.numberOfLines || j >= this.numberOfColumns) {
            throw new MatrixException("You can't enter those coordinates");
        }
    // If we put zero in a position where there's already an element, it means the element is erased
        else if (value == 0 && elements.containsKey(new MatrixElementCoordinate(i,j))) {
            elements.remove(new MatrixElementCoordinate(i,j));
            System.out.println("Value removed");
        }
    // To prevent people from entering zero
        else if (value == 0 && !elements.containsKey(new MatrixElementCoordinate(i,j))) {
            System.out.println("You can't enter zeros");
        }
    // For a matrix to be sparse, we cannot put in too many non zero elements
    // So we check if more than half the matrix is zero, and if yes we do not let people enter more elements
    // Diagonal sparse matrices naturally satisfy this condition, as we have a lot of zeros lying outside the diagonal
        else if (value != 0 && this.elementCounter >= 0.5*(this.numberOfLines*this.numberOfColumns - 1)) {
            System.out.println("You can't have too many non zero elements");
        }
        else if (value != 0) {
            try { 
                this.elementCounter++;
                MatrixElementCoordinate coordinate = new MatrixElementCoordinate(i, j);
                elements.put(coordinate, value);
                System.out.println("You successfully set the element in row " +i+ " & column " +j+ " to be equal to " +this.elements.get(coordinate)+ "\n");
            } catch (MatrixException exception) {
                System.out.println(exception.getMessage());
            }
        }   
    }

    // Method to get a value of an element
    public double getValue(int i, int j) throws MatrixException{
        if (i < 0 || j < 0 || i >= this.numberOfLines || j >= this.numberOfColumns || !elements.containsKey(new MatrixElementCoordinate(i,j)) ) {
                throw new MatrixException("You can't enter those coordinates");
            }
        MatrixElementCoordinate coordinate = new MatrixElementCoordinate(i, j);
        return this.elements.get(coordinate);
    }
    
    // Method to get the matrix' size
    public int[] getSize(){
        return new int[] {this.numberOfLines, this.numberOfColumns};
    }
    
    // This is to print out all elements and their respective positions
    public void displayMatrix() throws MatrixException {
        String output = "Matrix' elements : \n";
        for (MatrixElementCoordinate key: this.elements.keySet()){
            output += "The element in row "+key.getI()+" and column "+key.getJ()+
                    " is : " +this.elements.get(new MatrixElementCoordinate(key.getI(), key.getJ()))+"\n";
        }
        System.out.println(output);
    }
    
    // Method to transpose a matrix without changing the original
    public SparseMatrix transpose() throws MatrixException{
        SparseMatrix transposedMatrix = new SparseMatrix(this.numberOfColumns,this.numberOfLines);
        for(MatrixElementCoordinate key: this.elements.keySet()){
                transposedMatrix.setValue(key.getJ(), key.getI(), this.getValue(key.getI(), key.getJ()));
            }
        return transposedMatrix;
    }
}
