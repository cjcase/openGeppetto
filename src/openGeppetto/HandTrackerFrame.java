package openGeppetto;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Code by cjcase based on OpenNI examples
public class HandTrackerFrame implements Runnable{
    public HandTracker viewer;
    public controlAdapter bot;
	private boolean shouldRun = true;
	private JFrame frame;
    
    
    public HandTrackerFrame (){
    	
        frame = new JFrame("openGeppetto Hand Tracking NUI");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        
        viewer = new HandTracker();
        frame.add("Center", viewer);
                        
    	frame.addKeyListener(new KeyListener() {
            @Override
			public void keyTyped(KeyEvent arg0){}
            @Override
			public void keyReleased(KeyEvent arg0){}
            @Override
			public void keyPressed(KeyEvent arg0){
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					shouldRun = false;
				}
			}
		});
        
        frame.pack();
        frame.setVisible(true);
    }

    public void buildNUI(){
        if (viewer == null)
        {
            viewer = new HandTracker();
        }
        viewer.updateDepth();
        viewer.repaint();
    }

    @Override
    public void run() {
        viewer.bot = this.bot;
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        while(shouldRun) {
            viewer.updateDepth();
            viewer.repaint();
        }
        frame.dispose();
    }
}
