package marching;

public class Vertice {
    public float x;
    public float y;
    public float z;
    public boolean sinal = false;
    public float value;

    public Vertice(float x,float y,float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        sinal = false;
        
    }
    public void setPos(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

