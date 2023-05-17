package items;

import javax.swing.ImageIcon;

import breakDown.Circle;
import player.Player;
/**
 * Item macht den Spieler schneller.
 * @author floriank
 *
 */
public class FastMouse extends Item implements UseImmediately{
	
	private static final ImageIcon ICON = new ImageIcon(FastMouse.class.getResource("mouse.png"));
	private static final int MAXSPEED = 50;

	public FastMouse(int startX) {
		this.setBounds(startX,-ICON.getIconHeight(),ICON.getIconWidth(),ICON.getIconHeight());
		this.setIcon(FastMouse.ICON);
	}
	
	@Override
	public void doItem(Player player, Circle circle) {
		if(player.getStep() <= FastMouse.MAXSPEED) {
			player.setStep(player.getStep()+5);
		}
		
	}
	/**
	 * Gibt die Länge des Items zurück.
	 * @return
	 */
	public static int getItemSize() {
		return FastMouse.ICON.getIconWidth();
	}

}
