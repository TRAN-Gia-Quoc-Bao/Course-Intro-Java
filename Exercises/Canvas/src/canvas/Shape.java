package canvas;

public abstract class Shape {
    private String identifier;
    private boolean fill;

    
    public void setIndentifier (String identifier) {
        this.identifier = identifier;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public void setFilling (boolean fill) {
        this.fill = fill;
    }
    
    public boolean getFilling() {
        return this.fill; 
    }
    
    public abstract double getPerimeter();
    
    public abstract double getSurface();
    
    public abstract boolean contains(double x, double y);
    
}
