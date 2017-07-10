package marching;
import util.math.Vector3f;

public class Cubo {
    Tetrahedra[] tetraedros = new Tetrahedra[6];
    
    
    
    public Cubo(Vertice v0,Vertice v1,Vertice v2,Vertice v3,Vertice v4,Vertice v5,Vertice v6,Vertice v7){
        
        Tetrahedra t1 = new Tetrahedra(v0, v7, v3, v2);
        Tetrahedra t2 = new Tetrahedra(v0, v7, v2, v6);
        Tetrahedra t3 = new Tetrahedra(v0, v4, v7, v6);
        Tetrahedra t4 = new Tetrahedra(v0, v1, v6, v2);
        Tetrahedra t5 = new Tetrahedra(v0, v4, v6, v1);
        Tetrahedra t6 = new Tetrahedra(v5, v1, v6, v4);
        
        tetraedros[0] = t1;
        tetraedros[1] = t2;
        tetraedros[2] = t3;
        tetraedros[3] = t4;
        tetraedros[4] = t5;
        tetraedros[5] = t6;
        
    }
    public Cubo(){
     tetraedros = new Tetrahedra[6];   
    }
    public void setVertex(Vertice v0,Vertice v1,Vertice v2,Vertice v3,Vertice v4,Vertice v5,Vertice v6,Vertice v7){
        Tetrahedra t1 = new Tetrahedra(v0, v7, v3, v2);
        Tetrahedra t2 = new Tetrahedra(v0, v7, v2, v6);
        Tetrahedra t3 = new Tetrahedra(v0, v4, v7, v6);
        Tetrahedra t4 = new Tetrahedra(v0, v1, v6, v2);
        Tetrahedra t5 = new Tetrahedra(v0, v4, v6, v1);
        Tetrahedra t6 = new Tetrahedra(v5, v1, v6, v4);
        
        tetraedros[0] = t1;
        tetraedros[1] = t2;
        tetraedros[2] = t3;
        tetraedros[3] = t4;
        tetraedros[4] = t5;
        tetraedros[5] = t6;
    }
    public Tetrahedra getTetraedros(int i){
        return tetraedros[i];
    }
}
