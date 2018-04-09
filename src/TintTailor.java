import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	
	/** CAR COLORS **/
	private int currentExtColor = 0;
	private int currentIntColor = 0;
	private Map<Integer, Integer> colors;
	
	/** CONSTRUCTOR **/
	private TintTailor(){
		colors = new HashMap<Integer, Integer>();
		colors.put(0, 0xFFFF1111);
		colors.put(1, 0xFF838996);
		colors.put(2, 0xFF0000FF);
		colors.put(3, 0xFF006060);
		colors.put(4, 0xFF000000);
		colors.put(5, 0xFFFFA500);
		openControlPanel();
	}
	
	/** SINGLETON CONTRUCTOR **/
	public static TintTailor getInstance(){
		if(instance == null){
			instance = new TintTailor();
		}
		return instance;
	}
	
	/** GET THE CURRENTLY SELECTED COLOR **/
	public int getExteriorColor(){
		return colors.get(currentExtColor);
	}
	
	/** GET THE CURRENTLY SELECTED COLOR **/
	public int getInteriorColor(){
		return colors.get(currentIntColor);
	}
	
	/** MAKE THE CONTROL PANEL FOR THE VISUALIZER **/
	private void openControlPanel(){
		/** VISUALIZATION CONTROLS **/
		JFrame controlWindow = new JFrame("Tint Tailor");
		JPanel controlPanel = new JPanel();
		Font font = new Font("Serif", Font.BOLD, 20);
		
		// Exterior Color Menu
		JLabel extTitle = new JLabel("Body Color");
		extTitle.setFont(font);
		controlPanel.add(extTitle);
		for(int i = 0; i < 5; i++)
			controlPanel.add(createColorButton(true, i));
		
		// Interior Color Menu
		JLabel intTitle = new JLabel("Interior Color");
		intTitle.setFont(font);
		controlPanel.add(intTitle);
		for(int i = 0; i < 3; i++)
			controlPanel.add(createColorButton(false, i));
		
		// Add Seasonal Color
		JLabel seaTitle = new JLabel("Seasonal Color");
		seaTitle.setFont(font);
		controlPanel.add(seaTitle);
		controlPanel.add(createColorButton(false, 5));
		
		controlWindow.add(controlPanel);
		controlWindow.setSize(100, 475);
		
		controlWindow.setVisible(true);
	}
	
	/** CREATE BUTTONS **/
	private JButton createColorButton(boolean isExterior, int color){
		JButton colorButton = new JButton();
		colorButton = new JButton();
		colorButton.setPreferredSize(new Dimension(150, 30));
		
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
