package canvas;

public class Disk extends Shape{
    public static final double pi = 3.14159;
    private double radius, x, y ;

    // Constructor 1: center + radius
    public Disk(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    
    // Constructor 2: 2 points
    public Disk(double x1, double y1, double x2, double y2) {
        this.radius = 0.5*Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
        this.x = 0.5*(x2 + x1); 
        this.y = 0.5*(y2 + y1);
    }
    
    // Method to calculate perimeter
    @Override
    public double getPerimeter() {
        return 2*pi*radius; 
    }
    
    // Method to calculate surface
    @Override
    public double getSurface() {
        return pi*Math.pow(radius,2); 
    }
    
    // Method to check if point lies inside 
    @Override
    public boolean contains(double x, double y) {
        boolean check = false;
        if (Math.sqrt(Math.pow((x - this.x),2) + Math.pow((y - this.y),2)) < this.radius)
            check = true;
        return check;
    } 
}

