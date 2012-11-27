/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package openGeppetto;

// Code by cjcase

import AIBO.visionAIBO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import openGeppetto.VisionListener.VisionUpdatedListener;

public class VisionFrame extends JPanel implements VisionUpdatedListener, Runnable{

    private String host;
    
    protected boolean windowHasFocus;
    protected boolean crosshairsEnabled=false;
    protected boolean idEnabled=false;
    
    protected BufferedImage _image;
    protected VisionListener _listener;
    boolean lockAspect=false;
    float tgtAspect=-1;
    
    private JFrame frame;

    int drawX, drawY, drawHeight, drawWidth;

    protected int mouseX=-1, mouseY=-1;
    
    public VisionFrame(String host){
		super();
        this.host = host;
		init(new visionAIBO(this.host,VisionListener.defRawPort));
		
	}
    public VisionFrame(String host, int port) {
		super();
        this.host = host;
        init(new visionAIBO(this.host,port));
	}
    
    @Override
	public void run(){
		frame = new JFrame("Robot @"+ this.host +" RAW Vision");
		frame.setBackground(Color.black);
		frame.setSize(new Dimension(VisionListener.DEFAULT_WIDTH*2, VisionListener.DEFAULT_HEIGHT*2)); 
		frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               kill();                
            }
        });
		frame.setVisible(true);
        
        //
	}

	public void setConvertRGB(boolean b) { _listener.setConvertRGB(b); }
    
	public boolean getConvertRGB() { return _listener.getConvertRGB(); }

	private void init(VisionListener listener) {
	    setBackground(Color.BLACK);
	    setForeground(Color.WHITE);
	    setOpaque(!lockAspect);
	    windowHasFocus = true;
	    crosshairsEnabled = false;
	    _listener=listener;
		_listener.addListener(this);
	}

   
	public VisionListener getListener() { return _listener; }
	public void close() { _listener.close();	}
	public void kill() { _listener.kill(); }
	public void open() { _listener.needConnection(); }
    @Override
	public void visionUpdated(VisionListener l) { 
        //System.out.println("receiving image!");
        repaint(); 
    }
    @Override
	public void sensorsUpdated(VisionListener l) {}
	
	public void setLockAspectRatio(boolean b) {
		if(b!=lockAspect) {
			lockAspect=b;
			setOpaque(!lockAspect);
			repaint();
		}
	}
	
	public boolean getLockAspectRatio() { return lockAspect; }

	public void setAspectRatio(float asp) {
		if(asp<=0)
			tgtAspect=-1;
		else
			tgtAspect=asp;
		if(getLockAspectRatio())
			repaint();
	}
	
	public float getAspectRatio() { return tgtAspect; }

	public void setLockAspectRatio(boolean b, float asp) {
		setLockAspectRatio(b);
		setAspectRatio(asp);
	}
	
    @Override
	public void paint(Graphics graphics) {
		_image=_listener.getImage();
        
        if(_image == null){ System.out.println("Houston, we have a problem..."); }
        
		super.paint(graphics);
		Dimension sz=getSize();
		drawX = 0;
		drawY = 0;
		drawWidth = sz.width;
		drawHeight = sz.height;
		// Scale image to fit the window size while maintaining aspect ratio.
		// Center the image in the unused space if window width or height too large.
		// Note: this only makes sense for cam space.
		if (_image != null && getLockAspectRatio()) {
			float curasp=sz.width/(float)sz.height;
			float tgtasp=getAspectRatio();
			if(tgtasp<0)
				tgtasp=_image.getWidth()/(float)_image.getHeight();
			if(curasp>tgtasp) {
				drawWidth = (int)(sz.height*tgtasp);
				drawX = (sz.width-drawWidth)/2;
			} else if(curasp<tgtasp) {
				drawHeight = (int)(sz.width/tgtasp);
				drawY = (sz.height-drawHeight)/2;
			} else {
			}
		}
		drawImage(graphics, _image, drawX, drawY, drawWidth, drawHeight);
		
		// If requested, draw crosshairs for RawCam, SegCam.
		// Crosshairs for SketchGUI are handled in SketchPanel.java.
		if (crosshairsEnabled){
			graphics.setXORMode(Color.GRAY);
			graphics.setColor(Color.WHITE);
			((Graphics2D)graphics).setStroke(new BasicStroke(1.0f));
			graphics.drawLine(drawX+drawWidth/2,drawY, drawX+drawWidth/2, drawY+drawHeight);
			graphics.drawLine(drawX, drawY+drawHeight/2, drawX+drawWidth, drawY+drawHeight/2);
			graphics.setPaintMode();
		}
	}
	
	protected void drawImage(Graphics g, BufferedImage img, int x, int y, int w, int h) {
		if(img!=null) {
			synchronized(img) {
				g.drawImage(img,x,y,w,h,null);
			}
		} else {
			g.setColor(getBackground());
			g.fillRect(x,y,w,h);
			FontMetrics tmp=g.getFontMetrics();
			String msg="No image";
			int strw=tmp.stringWidth(msg);
			int strh=tmp.getHeight();
			g.setColor(getForeground());
			g.drawString(msg,(getSize().width-strw)/2,(getSize().height-strh)/2+tmp.getAscent());
		}
	}
	

}

