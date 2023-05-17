package walls;

import javax.swing.ImageIcon;
/**
 * Klasse für eine starke Mauer, die beim zweiten Treffen zerstört wird.
 * @author floriank
 *
 */
public class HardWall extends DestroyableWall implements CanChangeImage{
	
	private static final ImageIcon[] ICON = {new ImageIcon(HardWall.class.getResource("HardBlock1.png")),
											new ImageIcon(HardWall.class.getResource("HardBlock2.png"))};
	
	public HardWall(int xPos,int yPos) {
		super(xPos,yPos, HardWall.ICON.length);
		this.setIcon(HardWall.ICON[super.imageIndex]);
	}

	@Override
	public void changeImage() {
		this.setIcon(HardWall.ICON[super.imageIndex]);
		
	}

}
