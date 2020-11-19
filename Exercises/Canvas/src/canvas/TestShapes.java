package canvas;

public class TestShapes {
    public static void main(String[] args) {
        Canvas myCanvas = new Canvas();
        
        //Now I create the shapes (objects)
        // Rect1 is created with the 1st constructor (1st way)
        Shape myRect1 = new Rectangle(0, 0, 100, 60);
        myRect1.setIndentifier("1");
        myCanvas.addShape(myRect1);
        // Rect2 is created with the 2nd constructor (2nd way)
        double[] corner = {2.5, 6.0};
        Shape myRect2 = new Rectangle(corner, 100, 60);
        myRect2.setIndentifier("2");
        myCanvas.addShape(myRect2);
        // Disk1 is created with the 1st constructor (1st way)
        Shape myDisk1 = new Disk(0, 0, 100);
        myDisk1.setIndentifier("3");
        myCanvas.addShape(myDisk1);
        // Disk2 is created with the 2nd constructor (2nd way)
        Shape myDisk2 = new Disk(0, 0, 60, 60);
        myDisk2.setIndentifier("4");
        myCanvas.addShape(myDisk2);
        
        // First I display all the shapes
        myCanvas.display();
        
        // If we want to print only certain shapes, just specify their indentifier using the shapesIWant
        String[] shapesIWant = {"1", "2"};
        myCanvas.getShape(shapesIWant);
        
        // I want to fill just the 2nd and the 3rd shape so I input the fill vector
        myRect2.setFilling(true);
        myDisk1.setFilling(true); 
        
        System.out.println("The total surface of the filled shapes is : " +myCanvas.totalFilledSurface()+ "\n");
        
        // For example if I want to check if the point (1,1) lies inside Rect1 or not (clearly it does)
        System.out.println("Is (1,1) inside Rect1 ? : " +myRect1.contains(1, 1)+ "\n");
        
        // For example if I want to check if the point (101,0) lies inside Disk1 or not (clearly it does not)
        System.out.println("Is (101,1) inside Disk1 ? : " +myDisk1.contains(101, 1)+ "\n");
        
        // Now I try removing the 2nd rectangle
        myCanvas.removeShape(1);
        
        // Again I tested by printing the shapes and the total surface
        myCanvas.display();
        System.out.println("Total surface of the filled shapes after deletion is : " +myCanvas.totalFilledSurface()+ "\n");
        // You'll see that the total surface actually changed
    }
}
