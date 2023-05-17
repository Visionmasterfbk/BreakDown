package walls;

import javax.swing.ImageIcon;

public class MetalWall extends Wall{

	private static final ImageIcon ICON = new ImageIcon(MetalWall.class.getResource("metalWall.png"));
	
	public MetalWall(int xPos, int yPos) {
		super(xPos, yPos);
		this.setIcon(ICON);
	}

}
