package cubo;

import java.util.ArrayList;
import util.math.FastMath;
import util.math.Vector4f;
import marching.*;

class Cube {

    public ArrayList<Vector4f> colors = new ArrayList<>();
    public ArrayList<Vector4f> positions = new ArrayList<>();

    protected int nverts = 0;
    protected int nfaces = 0;
    public float a,b,c,d,e,f;
    
    public void drawVert(Cube cubo, Vertice p1, Vertice p2, float resol ){
        float v1 = p1.value;
        float v2 = p2.value;
        
        float x, y, z;

        if (v2 == v1) {
            x = (p1.x + p2.x) / 2.0f;
            y = (p1.y + p2.y) / 2.0f;
            z = (p1.z + p2.z) / 2.0f;
        } else {
            

        /*
         <----+-----+---+----->
              v1    |   v2
                 isolevel
         <----+-----+---+----->
              0     |   1
                  interp
         */


        // interp == 0: vert should be at p1
        // interp == 1: vert should be at p2
        float interp = (resol - v1) / (v2 - v1);
        float oneMinusInterp = 1 - interp;

        x = p1.x * oneMinusInterp + p2.x * interp;
        y = p1.y * oneMinusInterp + p2.y * interp;
        z = p1.z * oneMinusInterp + p2.z * interp;
    }
        
        cubo.positions.add(new Vector4f(x, y, z, 1));
        cubo.nfaces++;
        cubo.nverts++;
        }
    
    public void drawTetrahedron(Cube surface, Vertice[] p, float isolevel){

        /*
         Tetrahedron layout:
               0
               *
              /|
             / |
          3 *-----* 1
             \ | /
              \|/
               *
               2
         */

        char index = (char)0;
        for (int i = 0; i < 4; ++i)
            if (p[i].value < isolevel)
                index |= (1 << i);

        switch ((int)index) {

            // we don't do anything if everyone is inside or outside
            case 0x00:
            case 0x0F:
                break;

            // only vert 0 is inside
            case 0x01:
                drawVert(surface, p[0], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // only vert 1 is inside
            case 0x02:
                drawVert(surface, p[1], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // only vert 2 is inside
            case 0x04:
                drawVert(surface, p[2], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // only vert 3 is inside
            case 0x08:
                drawVert(surface, p[3], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 0, 1 are inside
            case 0x03:
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;

                drawVert(surface, p[2], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 0, 2 are inside
            case 0x05:
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                drawVert(surface, p[1], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 0, 3 are inside
            case 0x09:
                drawVert(surface, p[0], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;

                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 1, 2 are inside
            case 0x06:
                drawVert(surface, p[0], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;

                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 2, 3 are inside
            case 0x0C:
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;

                drawVert(surface, p[2], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 1, 3 are inside
            case 0x0A:
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;

                drawVert(surface, p[1], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 0, 1, 2 are inside
            case 0x07:
                drawVert(surface, p[3], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[3], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 0, 1, 3 are inside
            case 0x0B:
                drawVert(surface, p[2], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[2], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 0, 2, 3 are inside
            case 0x0D:
                drawVert(surface, p[1], p[0], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[1], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // verts 1, 2, 3 are inside
            case 0x0E:
                drawVert(surface, p[0], p[1], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[2], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                drawVert(surface, p[0], p[3], isolevel);nverts++;
                colors.add(new Vector4f(1.0f, 20/255.0f, 147/255.0f, 1.0f));
                nfaces++;
                break;

            // what is this I don't even
            default:
                assert(false);
        }
    }
    public Cube(float a, float b, float c, float d, float e, float f, float resolucao){
        this.a=a;this.b=b;this.c=c;this.d=d;this.e=e;this.f=f;
        float tam = 2/resolucao;
        Vertice v = new Vertice(-1.0f, -1.0f, -1.0f);
        Cubo marching = new Cubo();
        
        for(int i=0;i<=resolucao;i++){
            float x = i*tam;
            for(int j=0;j<=resolucao;j++){
                float y = j*tam;
                for(int k=0; k<=resolucao;k++){
                    float z = k*tam;
                    
                    Vertice v0 = new Vertice(v.x + x, v.y + y, v.z + z);
                    Vertice v1 = new Vertice(v0.x, v0.y, v0.z + tam);
                    Vertice v2 = new Vertice(v0.x + tam, v0.y, v0.z + tam);
                    Vertice v3 = new Vertice(v0.x + tam, v0.y, v0.z);
                    Vertice v4 = new Vertice(v0.x, v0.y + tam, v0.z);
                    Vertice v5 = new Vertice(v0.x, v0.y + tam, v0.z + tam);
                    Vertice v6 = new Vertice(v0.x + tam, v0.y + tam, v0.z + tam);
                    Vertice v7 = new Vertice(v0.x + tam, v0.y + tam, v0.z);
                    
                    marching.setVertex(v0, v1, v2, v3, v4, v5, v6, v7);
                    
                    for (int l = 0; l < 6; l++) {
                        calculaValor(marching.getTetraedros(l));
                        drawTetrahedron(this, marching.getTetraedros(l).vertices, 0.5f);
                    }
                    
                    
                }
            }
        }
        
    }
    
    public void calculaValor(Tetrahedra t){
        
        for (int i = 0; i < 4; i++) {
            Vertice v = t.vertices[i];
            v.value = (((FastMath.pow(v.x, b)) * a) + ((FastMath.pow(v.y, d)) * c) + ((FastMath.pow(v.z, f)) * e));
            if(v.value<=0.5f){
                v.sinal = true;
            }
        }
    }
}
