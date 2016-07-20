package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Maleeha
 * Date: June 14th, 2016
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	//Maleeha - so far, there are some thresholds made for I guess differentiating the earthquakes 
	// + a map has been made and the earthquakes url has been made which contains the feed
	public void setup() {
		size(950, 600, OPENGL);
		//Maleeha- setting up the display 
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			//Mal-map is made, you  can zoom in
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();
         //Mal-markers list
	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    //pointfeature list
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    int red = color(255,0,0);
	    int orange = color(255,165,0);
	    
	    //TODO: Add code here as appropriate
	    
	    
	    for(int i = 0; i < earthquakes.size(); i++){
	        	
	       SimplePointMarker p =  new SimplePointMarker(earthquakes.get(i).getLocation());
	       p.setRadius(10);
	      
	       map.addMarker(p);


		   /*Object mag = p.getProperty("magnitude");
		    float magnitude = Float.parseFloat(mag.toString());
		   	if(magnitude > THRESHOLD_MODERATE){
		   		p.setColor(red);
		   	}
		   	else if(magnitude > THRESHOLD_LIGHT && magnitude < THRESHOLD_MODERATE){
		   		p.setColor(orange);
		   	}
		   	else{
		   		p.setColor(yellow);
		   		
		   	}*/
		   	   }
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		
		
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}

    //i think program is going to make a map --> i was right
	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{
	    int yellow = color(255, 255, 0);
		int red = color(255,0,0);
		int orange = color(255,165,0);
		int pink = color(255,160,5);
		
		String key = "Legend :Intensity";
		
		text(key, 50,90);
		
		String o = "Medium";
		String r = "High";
		String y = "Low";
		rect(50,100,130,250);
        fill(orange);
       
		ellipse(70,150,10,10);
	      text(o,90,160);
		fill(yellow);
	 
		ellipse(70,220,10,10);
	      text(y,90,230);
		fill(red);
	     
		ellipse(70,290,10,10);
	      text(r,90,310);
		fill(167);
		
		// Remember you can use Processing's graphics methods here
	
	}
}
