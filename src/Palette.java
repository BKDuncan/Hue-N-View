
/** COLOR PALETTE OBJECT **/
public class Palette {
	private int exteriorColors [];
	private int interiorColors [];
	private int seasonalColor;
	
	public void setPalette(int[] extColors, int[] intColors, int seaColor){
		exteriorColors = extColors;
		interiorColors = intColors;
		seasonalColor = seaColor;
	}
	
	public int getIntColor(int index){
		return interiorColors[index];
	}
	
	public int getExtColor(int index){
		return exteriorColors[index];
	}
	
	public int getSeasonalColor(){
		return seasonalColor;
	}
}
