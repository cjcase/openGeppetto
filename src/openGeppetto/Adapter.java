
package openGeppetto;

// Code by cjcase
public abstract interface Adapter {
    public void startHead();
    public void startWalk();    
    public void startVision();
    public void panHead(double param);
    public void nodHead(double param);   
    public void tiltHead(double param);
    public void forward(double param);
    public void strafe(double param);
    public void rotate(double param);
    public void toggleStop();
    public void stop();
    public void start();
}
