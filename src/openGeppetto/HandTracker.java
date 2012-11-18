package openGeppetto;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.OpenNI.*;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.image.*;

//Code by cjcase based on OpenNI examples
public class HandTracker extends Component {
    
    private NUI nui;
    
    private static final long serialVersionUID = 1L;
	//private OutArg<ScriptNode> scriptNode;
    private Context context;
    private DepthGenerator depthGen;
    private GestureGenerator gestureGen;
    private HandsGenerator handsGen;
    private HashMap<Integer, ArrayList<Point3D>> history;
    private byte[] imgbytes;
    private float histogram[];
    private int historySize = 10;
    Color colors[] = {Color.RED, Color.RED, Color.BLUE, Color.GREEN,
        Color.MAGENTA, Color.PINK, Color.YELLOW};
    
    private BufferedImage bimg;
    int width, height;
    
    //Constructor
    public HandTracker(){
        try {
            context = new Context();
            context.setGlobalMirror(true);
            
            depthGen = DepthGenerator.create(context);
            DepthMetaData depthMD = depthGen.getMetaData();
            width = depthMD.getFullXRes();
            height = depthMD.getFullYRes();

            System.out.println("Depth Resolution: "+width+"x"+height);
            
            nui = NUI.getInstance();
            
            
            histogram = new float[10000];
            
            gestureGen = GestureGenerator.create(context);
            String aGestures[] = gestureGen.enumerateAllGestures();
            System.out.print("Available Gestures: ");
            for(int i = 0; i < aGestures.length; i++){
                if(i+1 == aGestures.length){
                    System.out.println(aGestures[i]);
                } else {
                    System.out.print(aGestures[i] + ", ");
                }                
            }
            
            String rGestures[] = nui.getGestures();
            System.out.print("Recognizing Gestures: ");
            for(int i = 0; i < rGestures.length; i++){
                if(i+1 == rGestures.length){
                    System.out.println(rGestures[i]);
                } else {
                    System.out.print(rGestures[i] + ", ");
                }
                gestureGen.addGesture(rGestures[i]);
            }
            
            gestureGen.getGestureRecognizedEvent().addObserver(new MyGestureRecognized());
            
            handsGen = HandsGenerator.create(context);
            handsGen.getHandCreateEvent().addObserver(new MyHandCreateEvent());
            handsGen.getHandUpdateEvent().addObserver(new MyHandUpdateEvent());
            handsGen.getHandDestroyEvent().addObserver(new MyHandDestroyEvent());         

			context.startGeneratingAll();
			
            history = new HashMap<Integer, ArrayList<Point3D>>();
            imgbytes = new byte[width * height];
            
            DataBufferByte dataBuffer = new DataBufferByte(imgbytes, width * height);
            Raster raster = Raster.createPackedRaster(dataBuffer, width, height, 8, null);
            bimg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            bimg.setData(raster);

        } catch (StatusException e){
            System.out.println("Xbox Kinect not found: " + e.getMessage());
            System.exit(1);
        } catch (GeneralException e) {
            System.out.println("Tracker Error: " + e.getMessage());
            System.exit(1);
        }
    }

    //Observer class for gesture recognition
	class MyGestureRecognized implements IObserver<GestureRecognizedEventArgs>{

		@Override
		public void update(IObservable<GestureRecognizedEventArgs> observable,
				GestureRecognizedEventArgs args){
			try	{
				handsGen.StartTracking(args.getEndPosition());
				System.out.println("Recognized: " + args.getGesture());
                
                nui.gestureRecognized(args.getGesture());
                
			} catch (StatusException e)	{
				System.out.println("Gesture Recognition Error: "+e);
			} catch (Exception e) {
                System.out.println("Gesture Recognition Error: " + e.getMessage());
            }
		}
	}
    
    //Observer class for new detected hands
	class MyHandCreateEvent implements IObserver<ActiveHandEventArgs>{
        @Override
		public void update(IObservable<ActiveHandEventArgs> observable,
				ActiveHandEventArgs args){
			ArrayList<Point3D> newList = new ArrayList<Point3D>();
			newList.add(args.getPosition());
			history.put(new Integer(args.getId()), newList);
            System.out.println("New Hand Detected! (Id: " + args.getId() + ")");
            try {
                nui.handCreated(args.getId());
            } catch (Exception e) {
                System.out.println("Hand Create Error: " + e.getMessage());
            }
		}
	}
    
    //Observer class for hand movement
	class MyHandUpdateEvent implements IObserver<ActiveHandEventArgs>{
        @Override
		public void update(IObservable<ActiveHandEventArgs> observable,
				ActiveHandEventArgs args){
			ArrayList<Point3D> historyList = history.get(args.getId());
			
            historyList.add(args.getPosition());
            try {
                nui.handMoved(args.getId(), args.getPosition());
            } catch (Exception e) {
                System.out.println("Hand Update Error: " + e.getMessage());
            }            
			while (historyList.size() > historySize){
				historyList.remove(0);
			}
		}
	}
	
	class MyHandDestroyEvent implements IObserver<InactiveHandEventArgs> {
        @Override
		public void update(IObservable<InactiveHandEventArgs> observable,
				InactiveHandEventArgs args){
			history.remove(args.getId());
            System.out.println("Hand Lost! (Id: " + args.getId() + ")");
            try {
                nui.handLost(args.getId());
            } catch (Exception e) {
                System.out.println("Hand Destroy Error: " + e.getMessage());
            }
		}
	}
	
    private void calcHist(ShortBuffer depth){
        // reset
        for (int i = 0; i < histogram.length; ++i)
            histogram[i] = 0;
        
        depth.rewind();

        int points = 0;
        while(depth.remaining() > 0){
            short depthVal = depth.get();
            if (depthVal != 0){
                histogram[depthVal]++;
                points++;
            }
        }
        
        for (int i = 1; i < histogram.length; i++){
            histogram[i] += histogram[i-1];
        }

        if (points > 0){
            for (int i = 1; i < histogram.length; i++){
                histogram[i] = (int)(256 * (1.0f - (histogram[i] / (float)points)));
            }
        }
    }


    void updateDepth(){
        try {
            DepthMetaData depthMD = depthGen.getMetaData();

            context.waitAnyUpdateAll();
            
            ShortBuffer depth = depthMD.getData().createShortBuffer();
            calcHist(depth);
            depth.rewind();
            
            while(depth.remaining() > 0){
                int pos = depth.position();
                short pixel = depth.get();
                imgbytes[pos] = (byte)histogram[pixel];
            }
        } catch (GeneralException e) {
            System.out.println("Depth Update Error: "+e);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void paint(Graphics g) {
        DataBufferByte dataBuffer = new DataBufferByte(imgbytes, width*height);
        Raster raster = Raster.createPackedRaster(dataBuffer, width, height, 8, null);
        bimg.setData(raster);

        g.drawImage(bimg, 0, 0, null);
        
        g.setColor(Color.RED);
        //g.drawLine(width / 2, height, width / 2, height * -1);

        for (Integer id : history.keySet())
        {
        	try
        	{
        	ArrayList<Point3D> points = history.get(id);
        	g.setColor(colors[id % colors.length]);
        	int[] xPoints = new int[points.size()];
        	int[] yPoints = new int[points.size()];
        	for (int i = 0; i < points.size(); ++i)
        	{
        		Point3D proj = depthGen.convertRealWorldToProjective(points.get(i));
        		xPoints[i] = (int) proj.getX();
        		yPoints[i] = (int) proj.getY();
        	}
            g.drawPolyline(xPoints, yPoints, points.size());
    		Point3D proj = depthGen.convertRealWorldToProjective(points.get(points.size()-1));
            g.drawArc((int)proj.getX(), (int)proj.getY(), 5, 5, 0, 360);
        	} catch (Exception e)
        	{
        		System.out.println(e);
        	}
        }
        
    }
}

