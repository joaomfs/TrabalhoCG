package cubo;

/**
 *
 * @author marcoslage
 */
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector3f;
import util.projection.Projection;

public class Main {

    // Creates a new cube
    private final CubeGL cube = new CubeGL();

    // Animation:
    private float  currentAngleX = 0.0f;
    private float  currentAngleY = 0.0f;
    
    // Projection Matrix
    private final Projection proj = new Projection(45, 1.333f, 0.0F, 100.0F, 1.0f, -1.0f ,1.0f, -1.0f);
    private boolean projectionLocked = false;
    // View Matrix
    private final Vector3f eye = new Vector3f( 0.0f, 2.0f, 2.0f);
    private final Vector3f at  = new Vector3f( 0.0f, 0.0f, 0.0f);
    private final Vector3f up  = new Vector3f( 0.0f, 1.0f, 2.0f);
    
    // Camera
    private final Camera cam = new Camera(eye, at, up);

    // Light
    private final Vector3f lightPos     = new Vector3f(0.0f, 2.0f,-2.0f);
    private final Vector3f ambientColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f diffuseColor = new Vector3f(1.0f, 1.0f, 1.0f);
    private final Vector3f speclarColor = new Vector3f(1.0f, 1.0f, 1.0f);
    
    private final float kA =  0.3f;
    private final float kD =  1.5f;
    private final float kS =  60.0f;
    private final float sN = 6.0f;
    
    // Model Matrix:
    private final Matrix4f rotationMatrixY = new Matrix4f();
    private final Matrix4f rotationMatrixX = new Matrix4f();
    private final Matrix4f scaleMatrix = new Matrix4f();
    
    // Final Matrix
    private final Matrix4f modelMatrix = new Matrix4f();
    private final Matrix4f viewMatrix  = new Matrix4f();      
    private final Matrix4f projMatrix  = new Matrix4f();
    
    /**
     * General initialization stuff for OpenGL
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        
        // width and height of window and view port
        int width = 640;
        int height = 480;

        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
                ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.create(pixelFormat, contextAtrributes);
        
        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: "   + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
         
        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        cube.fillVAOs();
        cube.loadShaders();
       
        // Model Matrix setup
        scaleMatrix.m11 = 0.5f;
        scaleMatrix.m22 = 0.5f;
        scaleMatrix.m33 = 0.5f;
        
        // light setup
        cube.setVector("lightPos"    , lightPos);
        cube.setVector("ambientColor", ambientColor);
        cube.setVector("diffuseColor", diffuseColor);
        cube.setVector("speclarColor", speclarColor);
        
        cube.setFloat("kA", kA);
        cube.setFloat("kD", kD);
        cube.setFloat("kS", kS);
        cube.setFloat("sN", sN);
        
        while (Display.isCloseRequested() == false) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);
            
            
            // Projection and View Matrix Setup
            project();            
            viewMatrix.setTo(cam.viewMatrix());
            
            rotate();

            modelMatrix.setToIdentity();
            modelMatrix.multiply(rotationMatrixY);
            modelMatrix.multiply(rotationMatrixX);
            modelMatrix.multiply(scaleMatrix);
                        
            cube.setMatrix("modelmatrix", modelMatrix);
            cube.setMatrix("viewmatrix" , viewMatrix);
            cube.setMatrix("projection" , projMatrix);
            cube.render();

            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    public void project() {
        if( Keyboard.isKeyDown(Keyboard.KEY_O)){
            if(!projectionLocked){
                projectionLocked = true;
                projMatrix.setTo(proj.changeProjection());
            }
        } else {
            projectionLocked = false;
            projMatrix.setTo(proj.getProjection());
        }      
    }
    public void rotate(){
        if(Keyboard.isKeyDown(Keyboard.KEY_B)){
            rotateB();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_C)){
            rotateC();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_R)){
            rotateR();
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_L)){
            rotateL();
        }
    }
    
    public void rotateB(){
        currentAngleX += 0.01f;
                float c = FastMath.cos(currentAngleX);
                float s = FastMath.sin(currentAngleX);

                rotationMatrixX.m22 = c; rotationMatrixX.m32 = -s;
                rotationMatrixX.m23 =s; rotationMatrixX.m33 = c;
    }
    
    public void rotateC(){
        currentAngleX -= 0.01f;
                float c = FastMath.cos(currentAngleX);
                float s = FastMath.sin(currentAngleX);

                rotationMatrixX.m22 = c; rotationMatrixX.m32 = -s;
                rotationMatrixX.m23 =s; rotationMatrixX.m33 = c;
    }
    public void rotateR(){
        currentAngleY += 0.01f;
                float c = FastMath.cos(currentAngleY);
                float s = FastMath.sin(currentAngleY);

                rotationMatrixY.m11 = c; rotationMatrixY.m31 = s;
                rotationMatrixY.m13 =-s; rotationMatrixY.m33 = c;
    }
        public void rotateL(){
        currentAngleY -= 0.01f;
                float c = FastMath.cos(currentAngleY);
                float s = FastMath.sin(currentAngleY);

                rotationMatrixY.m11 = c; rotationMatrixY.m31 = s;
                rotationMatrixY.m13 =-s; rotationMatrixY.m33 = c;
    }

    /**
     * main method to run the example
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        Main example = new Main();
        example.initGl();
        example.run();
    }
}
