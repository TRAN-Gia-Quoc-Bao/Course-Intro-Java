package canvas;

public class Rectangle extends Shape {
    private double width, height, x, y;

    // Constructor 1: point + width + height
    public Rectangle(double[] corner, double width, double height) {
        this.x = corner[0];
        this.y = corner[1];
        this.width = width;
        this.height = height;
    }
    
    // Constructor 2: 2 points diagonal
    public Rectangle(double x1, double y1, double x2, double y2) {
        this.x = x1;
        this.y = y1;
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
    }
    
    // Method to calculate perimeter
    @Override
    public double getPerimeter() {
        return 2*(width + height); 
    }
    
    // Method to calculate surface
    @Override
    public double getSurface() {
        return width*height; 
    }
    
    // Method to check if point lies inside
    @Override
    public boolean contains(double x, double y) {
        boolean check = false;
        if ((0 < (x - this.x)) && ((x - this.x) < this.width) && (0 < (y - this.y)) && ((y - this.y) < this.height))
            check = true;
        return check;
    }
}

