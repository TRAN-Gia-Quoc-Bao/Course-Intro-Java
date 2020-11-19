package canvas;

import java.util.TreeMap;

public class Canvas {
    TreeMap<Integer, Shape> shapes;
    static int shapeCounter = 0;

    public Canvas() {
        shapes = new TreeMap<Integer, Shape>();
    }

    // Method to add shape
    public int addShape(Shape s) {
        shapes.put(shapeCounter, s);
        shapeCounter++;
        return shapeCounter;
    }
    
    // Method to remove shape
    public void removeShape(int key) {
        shapeCounter--;
        shapes.remove(key);
    }
    
    // Method to print the shapes I want, specified by indentifier
    public void getShape(String[] identifier) {
        String output = "Shapes you want : \n";
        for (int i = 0; i < identifier.length; i++) {
            for(int key: this.shapes.keySet()){
                String id = this.shapes.get(key).getIdentifier();
                if (identifier[i] == id) {
                    output += this.shapes.get(key)+ "\n";
                    break;
                }
            }
        }
        System.out.println(output);
    }
      
    // Method to display Canvas
    public void display() {
        String output = "Canvas's characteristics : \n";
        for(int key: this.shapes.keySet()){
            output += "Value of key is : "+key+"; the surface of '"+shapes.get(key).getIdentifier()+
                    "' is : " + shapes.get(key).getSurface() + "\n";
        }
        System.out.println(output);
    }
    
    // Method to calculate total filled surface
    public double totalFilledSurface() {
        double sum = 0;
        for(int key: this.shapes.keySet()){
            boolean fill = this.shapes.get(key).getFilling();
            if (fill == true)
                sum += shapes.get(key).getSurface();
            System.out.println(key);
        }
        return sum;
    }
}   


