package openGeppetto;

import org.OpenNI.*;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.image.*;

//Code by cjcase based on OpenNI examples
public class HandTracker extends Component {
    
    public controlAdapter bot;
    
    //TODO test
    String[] gestures;
    float kinectX = 0f;
    float kinectY = 0f;
    float kinectZ = 0f;
    float initZ = 0f;

    //Callback class for "Gesture Recognized" event
	class MyGestureRecognized implements IObserver<GestureRecognizedEventArgs>
	{

		@Override
		public void update(IObservable<GestureRecognizedEventArgs> observable,
				GestureRecognizedEventArgs args)
		{
			try
			{
				handsGen.StartTracking(args.getEndPosition());
				System.out.println("Recognized: " + args.getGesture());
                if(args.getGesture().equals("Wave")){
                   initZ = args.getIdPosition().getZ();
                   System.out.println("init Z: " + initZ);
                } else if(args.getGesture().equals("Click")){
                    bot.toggleStop();
                }
                
			} catch (StatusException e)
			{
				System.out.println("Gesture Recognition Error: "+e);
			}
		}
	}
	class MyHandCreateEvent implements IObserver<ActiveHandEventArgs>
	{
        @Override
		public void update(IObservable<ActiveHandEventArgs> observable,
				ActiveHandEventArgs args)
		{
			ArrayList<Point3D> newList = new ArrayList<Point3D>();
			newList.add(args.getPosition());
			history.put(new Integer(args.getId()), newList);
            System.out.println("New Hand Detected! (Id: " + args.getId() + ")");
            initZ = args.getPosition().getZ();
		}
	}
	class MyHandUpdateEvent implements IObserver<ActiveHandEventArgs>
	{
        @Override
		public void update(IObservable<ActiveHandEventArgs> observable,
				ActiveHandEventArgs args)
		{
			ArrayList<Point3D> historyList = history.get(args.getId());
			
            historyList.add(args.getPosition());
            //System.out.println("[" + args.getPosition().getX() + ", " + args.getPosition().getY() + "]");
            
            //HeadControl TEST
            if(bot.isConnected()){
                
                kinectX = args.getPosition().getX();
                kinectY = args.getPosition().getY();
                kinectZ = args.getPosition().getZ();
                
                //System.out.println("Z: " + (int)kinectZ);
                
                kinectX = (kinectX * 10000)/(width / 2);
                kinectY = (kinectY * 10000)/(width / 2);
                kinectZ = (kinectZ * 10000)/(initZ + 10000);
                
                if(kinectX > 10000) kinectX = 10000;
                if(kinectX < -10000) kinectX = -10000;
                if(kinectY > 10000) kinectY = 10000;
                if(kinectY < -10000) kinectY = -10000;
                if(kinectZ > 10000) kinectZ = 10000;
                if(kinectZ < -10000) kinectZ = -10000;
                
                kinectX /= 10000;
                kinectY /= 10000;
                kinectZ /= 10000;
                
                bot.panHead(kinectX * -1);
                bot.nodHead(kinectY);
                
                //bot.rotate(kinectX);
                //bot.goFwd(kinectY);
                               
                //System.out.println("Z: " + kinectZ);
            }
            
			while (historyList.size() > historySize)
			{
				historyList.remove(0);
			}

		}
	}
	private int historySize = 10;
	class MyHandDestroyEvent implements IObserver<InactiveHandEventArgs>
	{
        @Override
		public void update(IObservable<InactiveHandEventArgs> observable,
				InactiveHandEventArgs args)
		{
			history.remove(args.getId());
            System.out.println("Hand Lost! (Id: " + args.getId() + ")");
			/*if (history.isEmpty())
			{
				try
				{
					gestureGen.addGesture("Click");
				} catch (StatusException e)
				{
					e.printStackTrace();
				}
			}*/
		}
	}
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private OutArg<ScriptNode> scriptNode;
    private Context context;
    private DepthGenerator depthGen;
    private GestureGenerator gestureGen;
    private HandsGenerator handsGen;
    private HashMap<Integer, ArrayList<Point3D>> history;
    private byte[] imgbytes;
    private float histogram[];

    private BufferedImage bimg;
    int width, height;
    
    //private final String SAMPLE_XML_FILE = "config.xml";
    public HandTracker()
    {

        try {
            //scriptNode = new OutArg<ScriptNode>();
            //context = Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);
            context = new Context();
            context.setGlobalMirror(true);
            
            gestureGen = GestureGenerator.create(context);
            
            //TODO gestures test
            gestures = gestureGen.enumerateAllGestures();
            System.out.print("Available Gestures: ");
            for(int i = 0; i < gestures.length; i++){
                if(i+1 == gestures.length){
                    System.out.println(gestures[i]);
                } else {
                    System.out.print(gestures[i] + ", ");
                }                
            }

            gestureGen.addGesture("Click");
            gestureGen.addGesture("Wave");
            gestureGen.getGestureRecognizedEvent().addObserver(new MyGestureRecognized());
            
            handsGen = HandsGenerator.create(context);
            handsGen.getHandCreateEvent().addObserver(new MyHandCreateEvent());
            handsGen.getHandUpdateEvent().addObserver(new MyHandUpdateEvent());
            handsGen.getHandDestroyEvent().addObserver(new MyHandDestroyEvent());
            
            depthGen = DepthGenerator.create(context);
            DepthMetaData depthMD = depthGen.getMetaData();            

			context.startGeneratingAll();
			
            history = new HashMap<Integer, ArrayList<Point3D>>(); 
            
            histogram = new float[10000];
            width = depthMD.getFullXRes();
            height = depthMD.getFullYRes();
            
            System.out.println("Depth Resolution: "+width+"x"+height);
            
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
    
    private void calcHist(ShortBuffer depth){
        // reset
        for (int i = 0; i < histogram.length; ++i)
            histogram[i] = 0;
        
        depth.rewind();

        int points = 0;
        while(depth.remaining() > 0)
        {
            short depthVal = depth.get();
            if (depthVal != 0)
            {
                histogram[depthVal]++;
                points++;
            }
        }
        
        for (int i = 1; i < histogram.length; i++)
        {
            histogram[i] += histogram[i-1];
        }

        if (points > 0)
        {
            for (int i = 1; i < histogram.length; i++)
            {
                histogram[i] = (int)(256 * (1.0f - (histogram[i] / (float)points)));
            }
        }
    }


    void updateDepth()
    {
        try {
            DepthMetaData depthMD = depthGen.getMetaData();

            context.waitAnyUpdateAll();
            
            ShortBuffer depth = depthMD.getData().createShortBuffer();
            calcHist(depth);
            depth.rewind();
            
            while(depth.remaining() > 0)
            {
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

    Color colors[] = {Color.RED, Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.PINK, Color.YELLOW};
    @Override
    public void paint(Graphics g) {
        DataBufferByte dataBuffer = new DataBufferByte(imgbytes, width*height);
        Raster raster = Raster.createPackedRaster(dataBuffer, width, height, 8, null); //TODO Test de tamaño de ventana!
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

