import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TintTailor {
	
	private static TintTailor instance;
	
	/** PALETTE JPANELS **/
	private JPanel palette1_panel;
	private JPanel palette2_panel;
	private JFrame controlWindow;
	
	/** SELECTED OPTIONS **/
	private int currentExtColor = 0;
	private int currentIntColor = 0;
	private int currentPalette = 0;
	
	/** CAR COLORS **/
	private Palette palette1;
	private Palette palette2;
	
	/** ENUM FOR PAINT COLORS **/
	private enum Type {EXTERIOR, INTERIOR, SEASONAL};
	
	/** CONSTRUCTOR **/
	private TintTailor(){
		/** PALETTE 1 COLORS **/
		palette1 = new Palette();
		int[] extColors = {0xFFFF1111, 0xFF838996,  0xFF0000FF, 0xFF006060, 0xFF000000};
		int[] intColors = {0xFFFF1111, 0xFF838996, 0xFF0000FF};
		int seasonalColor = 0xFFFFA500;
		palette1.setPalette(extColors, intColors, seasonalColor);
		
		/** PALETTE 2 COLORS **/
		palette2 = new Palette();
		int[] extColors2 = {0xFFDAA520, 0xFFA0522D,  0xFF811B33, 0xFF800080, 0xFF808000};
		int[] intColors2 = {0xFFA0522D, 0xFFDAA520, 0xFF999999};
		int seasonalColor2 = 0xFF416ca6;
		palette2.setPalette(extColors2, intColors2, seasonalColor2);
		
		/** SET DEFAULT COLORS **/
		currentExtColor = palette1.getExtColor(0);
		currentIntColor = palette1.getIntColor(0);
		
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
		return (currentPalette == 0) ? currentExtColor: currentExtColor;
	}
	
	/** GET THE CURRENTLY SELECTED COLOR **/
	public int getInteriorColor(){
		return (currentPalette == 0) ? currentIntColor: currentIntColor;
	}
	
	/** MAKE THE CONTROL PANEL FOR THE VISUALIZER **/
	private void openControlPanel(){
		/** VISUALIZATION CONTROLS **/
		controlWindow = new JFrame("Tint Tailor");
		palette1_panel = createColorMenu(palette1);
		palette2_panel = createColorMenu(palette2);
		
		JPanel palette_picker = new JPanel();
		JButton changePalette = createColorPaletteButton();
		palette_picker.add(changePalette);
		palette_picker.setBackground(new Color(102, 96, 96));
		
		controlWindow.add(palette_picker, BorderLayout.NORTH);
		controlWindow.add(palette1_panel, BorderLayout.CENTER);
		
		controlWindow.setVisible(true);
		controlWindow.pack();
	}
	
	/** CREATE CHANGE COLOR PALETTE BUTTON **/
	private JButton createColorPaletteButton(){
		Font font = new Font("Serif", Font.BOLD, 20);
		JButton changePalette = new JButton("Swap Color Palette");
		changePalette.setFont(font);
		changePalette.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(currentPalette == 0){
				controlWindow.remove(palette1_panel);
				controlWindow.add(palette2_panel, BorderLayout.CENTER);
				
				// Set Defaults
				currentExtColor = palette2.getExtColor(0);
				currentIntColor = palette2.getIntColor(0);	
				currentPalette++;
			} else {
				controlWindow.remove(palette2_panel);
				controlWindow.add(palette1_panel, BorderLayout.CENTER);
				
				// Set Defaults
				currentExtColor = palette1.getExtColor(0);
				currentIntColor = palette1.getIntColor(0);
				currentPalette--;
			}
			
			controlWindow.repaint();
			controlWindow.revalidate();
		}
		
	});
		return changePalette;
	}
	
	/** CREATE MENU PANEL FOR A COLOR PALETTE **/
	private JPanel createColorMenu(Palette palette){
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		Font font = new Font("Serif", Font.BOLD, 20);
		
		// Exterior Color Menu
		gbc.gridy++;
		JLabel extTitle = new JLabel("Body Color");
		extTitle.setFont(font);
		extTitle.setForeground(Color.WHITE);
		controlPanel.add(extTitle, gbc);
		for(int i = 0; i < 5; i++){
			gbc.gridy++;
			controlPanel.add(createColorButton(i, palette, Type.EXTERIOR), gbc);
		}
		
		// Interior Color Menu
		gbc.gridy++;
		JLabel intTitle = new JLabel("Interior Color");
		intTitle.setFont(font);
		intTitle.setForeground(Color.WHITE);
		controlPanel.add(intTitle, gbc);
		for(int i = 0; i < 3; i++){
			gbc.gridy++;
			controlPanel.add(createColorButton(i, palette, Type.INTERIOR), gbc);
		}
		
		// Add Seasonal Color
		gbc.gridy++;
		JLabel seaTitle = new JLabel("Seasonal Color");
		seaTitle.setFont(font);
		seaTitle.setForeground(Color.WHITE);
		controlPanel.add(seaTitle, gbc);
		
		gbc.gridy++;
		controlPanel.add(createColorButton(-1, palette,  Type.SEASONAL), gbc);
		
		gbc.gridy++;
		controlPanel.add(Box.createVerticalStrut(20), gbc);
		controlPanel.setBackground(new Color(102, 96, 96));
		return controlPanel;
	}
	
	/** CREATE BUTTONS **/
	private JButton createColorButton(int colorIndex, Palette palette, Type type){
		JButton colorButton = new JButton();
		colorButton = new JButton();
		colorButton.setPreferredSize(new Dimension(300, 30));
		
		// Parse Color for Button Background Color (convert to hex and remove first two values)
		Integer colorCode = null;
		switch(type){
			case EXTERIOR:
				colorCode = palette.getExtColor(colorIndex);
				break;
				
			case INTERIOR:
				colorCode = palette.getIntColor(colorIndex);
				break;
				
			case SEASONAL:
				colorCode = palette.getSeasonalColor();
				break;
				
			default:
				break;
		}
		String c1 = Integer.toHexString(colorCode);
		c1 = "#" + c1.substring(2);
		colorButton.setBackground(Color.decode(c1));
		
		// Connect to Click Listener
		colorButton.addActionListener(createColorListener(colorCode, type));
		return colorButton;
	}
	
	/** CONNECT BUTTON TO 3D MODEL COLOR **/
	private ActionListener createColorListener(int color, Type type){
		ActionListener colorButtonPressedAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				switch(type){
					case EXTERIOR:
						currentExtColor = color;
						break;
						
					case INTERIOR:
						currentIntColor = color;
						break;
						
					case SEASONAL:
						currentIntColor = color;
						break;
						
					default:
						break;
				}
			}
		};
		return colorButtonPressedAction;
	}
}
