package marching;

public class Tetrahedra {
        public Vertice[] vertices;
    
    public Tetrahedra(Vertice v0, Vertice v1, Vertice v2, Vertice v3){
        
        vertices = new Vertice[4];
        
        vertices[0] = v0;
        vertices[1] = v1;
        vertices[2] = v2;
        vertices[3] = v3;
        
    }

}
