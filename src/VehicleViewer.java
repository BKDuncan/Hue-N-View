import processing.core.PApplet;
import processing.core.PShape;
import processing.event.MouseEvent;

/** 
 * 			CONTROLS
 * 	mouse wheel 	- zoom
 * 	left mouse		- rotate Right/Left
 * 	right mouse		- rotate Up/Down
 * 	menu items		- change int/ext colors
 **/
public class VehicleViewer extends PApplet{
	// 3D Car Model
	PShape carExterior;
	PShape carInterior;
	PShape carWindows;
	PShape carTires;
	
	// Track Last Mouse Positions for Car Rotation
	float lastMouseX = 0;
	float lastMouseY = 0;
	
	// Camera Controls
	float zoomLevel = 0;
	
	// Control Panel
	private TintTailor controlPanel;
	
	// Floor Rotation
	float floorRotX = 0;
	float floorRotY = 0;
	
	public static void main(String args[]){
		/** CAR VISUALIZATION **/
		PApplet.main("VehicleViewer");
	}
	
	/** RUN ONCE BEFORE SETUP **/
	public void settings(){
		size(1000, 800, P3D);
		println("Completed Settings");
	}
	
	/** RUN ONCE BEFORE DRAW **/
	public void setup(){
		carExterior = loadShape("./Models/car_exterior.obj");
		carExterior.scale(100); // Processing Import is 100x Smaller
		carExterior.rotateX(radians(180)); // Processing Axes are opposite
		carExterior.rotateY(radians(90));
		
		carInterior = loadShape("./Models/car_interior.obj");
		carInterior.scale(100); // Processing Import is 100x Smaller
		carInterior.rotateX(radians(180)); // Processing Axes are opposite
		carInterior.rotateY(radians(90));
		
		carWindows = loadShape("./Models/car_windows.obj");
		carWindows.scale(100); // Processing Import is 100x Smaller
		carWindows.rotateX(radians(180)); // Processing Axes are opposite
		carWindows.rotateY(radians(90));
		
		carTires = loadShape("./Models/car_tires.obj");
		carTires.scale(100); // Processing Import is 100x Smaller
		carTires.rotateX(radians(180)); // Processing Axes are opposite
		carTires.rotateY(radians(90));
		
		zoomLevel = (float) (height * 0.8662);
		carWindows.setFill(0x3E00CC99); // Window Transparency + Color
		carTires.setFill(0xFF000000); 	// Make tires black

		controlPanel = TintTailor.getInstance();
	}
	
	/** RUNS MANY TIMES PER SECOND **/
	public void draw(){
		colorMode(HSB, 360, 100, 100);
		background(205,47,93);
		ambientLight(127, 7, 60);
		lightSpecular(127, 7, 60);
		directionalLight(102, 102, 102, 0, 0, -1);
		camera((float)0, (float)0, zoomLevel,
		(float)0, (float)0, (float)0,
		(float)0, (float)1, (float)0);
		
		// Set Car Colors
		carExterior.setFill(controlPanel.getExteriorColor());
		carInterior.setFill(controlPanel.getInteriorColor());
		
		specular(200);
		carExterior.setShininess((float)3.0);
		
		// Render Car Models
		shape(carExterior);
		shape(carInterior);
		shape(carWindows);	
		shape(carTires);
		drawFloor();
	}
	
	/** DRAW FLOOR **/
	public void drawFloor(){
		// Draw Floor
		specular(0);
		fill(90, 100, 99);
		translate(0, 200, 0);
		box(4000, 10, 4000);
	}
	
	/** TO ZOOM **/
	public void mouseWheel(MouseEvent event) {
		zoomLevel += 20 * event.getCount();
	}
	
	/** TO MAKE ROTATIONS SMOOTHER **/
	public void mouseMoved(){
		lastMouseY = mouseY;
		lastMouseX = mouseX;
	}
	
	/** ROTATE CAR IF MOUSE BUTTON HELD **/
	public void mouseDragged(){
		if(mouseButton == RIGHT){
			carExterior.rotateX((float) ((mouseY - lastMouseY) * -0.01));
			carInterior.rotateX((float) ((mouseY - lastMouseY) * -0.01));
			carWindows.rotateX((float) ((mouseY - lastMouseY) * -0.01));
			carTires.rotateX((float) ((mouseY - lastMouseY) * -0.01));
		} else if(mouseButton == LEFT){
			carExterior.rotateY((float) ((mouseX - lastMouseX) * 0.01));
			carInterior.rotateY((float) ((mouseX - lastMouseX) * 0.01));
			carWindows.rotateY((float) ((mouseX - lastMouseX) * 0.01));
			carTires.rotateY((float) ((mouseX - lastMouseX) * 0.01));
		}
		/** TO MAKE ROTATIONS SMOOTHER **/
		lastMouseY = mouseY;
		lastMouseX = mouseX;
	}
	
	
}
