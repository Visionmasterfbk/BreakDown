package walls;

import javax.swing.ImageIcon;
/**
 * Klasse für eine normale Mauer, die beim ersten Treffen zerstört wird.
 * @author floriank
 *
 */
public class NormalWall extends DestroyableWall{
	
	private static final ImageIcon[] ICON = {new ImageIcon(NormalWall.class.getResource("normalBlock.png"))};
	
	public NormalWall(int xPos,int yPos) {
		super(xPos,yPos, NormalWall.ICON.length);
		this.setIcon(NormalWall.ICON[super.imageIndex]);
	}


}
