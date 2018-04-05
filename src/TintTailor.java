import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TintTailor {
	
	private static TintTailor instance;
	
	// Car Colors
	private int currentExtColor = 0;
	private int currentIntColor = 0;
	private Map<Integer, Integer> colors;
	
	// Contructor
	private TintTailor(){
		colors = new HashMap<Integer, Integer>();
		colors.put(0, 0xFFFF0000);
		colors.put(1, 0xFF00FF00);
		colors.put(2, 0xFF0000FF);
		openControlPanel();
	}
	
	// Singleton Contructor
	public static TintTailor getInstance(){
		if(instance == null){
			instance = new TintTailor();
		}
		return instance;
	}
	
	// Get the currently selected color
	public int getExteriorColor(){
		return colors.get(currentExtColor);
	}
	
	// Get the currently selected color
	public int getInteriorColor(){
		return colors.get(currentIntColor);
	}
	
	/** MAKE THE CONTROL PANEL FOR THE VISUALIZER **/
	private void openControlPanel(){
		/** VISUALIZATION CONTROLS **/
		JFrame controlWindow = new JFrame("Tint Tailor");
		JPanel controlPanel = new JPanel();
		
		// Exterior Color Menu
		JLabel extTitle = new JLabel("Exterior Colors");
		controlPanel.add(extTitle);
		for(int i = 0; i < 3; i++)
			controlPanel.add(createColorButton("Color " + i, true, i));
		
		// Interior Color Menu
		JLabel intTitle = new JLabel("Interior Colors");
		controlPanel.add(intTitle);
		for(int i = 0; i < 3; i++)
			controlPanel.add(createColorButton("Color " + i, false, i));
		
		controlWindow.add(controlPanel);
		controlWindow.setSize(100, 250);
		controlWindow.setVisible(true);
	}
	
	/** CREATE BUTTONS **/
	private JButton createColorButton(String title, boolean isExterior, int color){
		JButton colorButton = new JButton(title);
		colorButton = new JButton("Color " + (color + 1) );
		
		// Parse Color for Background Color (convert to hex and remove first two values)
		String c1 = Integer.toHexString(colors.get(color));
		c1 = "#" + c1.substring(2);
		colorButton.setBackground(Color.decode(c1));
		
		// Connect to Click Listener
		colorButton.addActionListener(createColorListener(color, isExterior));
		return colorButton;
	}
	
	/** CONNECT BUTTON TO 3D MODEL COLOR **/
	private ActionListener createColorListener(int number, boolean isExterior){
		ActionListener colorButtonPressedAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isExterior)
					currentExtColor = number;
				else
					currentIntColor = number;
			}
			
		};
		return colorButtonPressedAction;
	}
}
