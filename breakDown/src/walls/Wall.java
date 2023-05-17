package walls;

import javax.swing.JLabel;
/**
 * Blaupause für Mauern.
 * @author floriank
 *
 */
public abstract class Wall extends JLabel{
	private int xPos;
	private int yPos;

	public Wall(int xPos,int yPos) {
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.setBounds(this.xPos*50, this.yPos*50, 50, 50);
	
}
	
	public int getXPos() {
		return this.xPos;
	}
	public int getYPos() {
		return this.yPos;
	}

}
