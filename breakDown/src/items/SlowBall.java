package items;

import javax.swing.ImageIcon;

import breakDown.Circle;
import player.Player;

/**
 * Item setzt den Ball auf seine StandartWerte.
 * @author floriank
 *
 */
public class SlowBall extends Item implements UseImmediately{

	private static final ImageIcon ICON = new ImageIcon(SlowBall.class.getResource("slowBall.png"));
	
	public SlowBall(int startX) {
		this.setBounds(startX,-ICON.getIconHeight(),ICON.getIconWidth(),ICON.getIconHeight());
		this.setIcon(SlowBall.ICON);
	}
	
	@Override
	public void doItem(Player player, Circle circle) {
		circle.setStandart();
		
	}
	/**
	 * Gibt die Länge des Items zurück.
	 * @return
	 */
	public static int getItemSize() {
		return SlowBall.ICON.getIconWidth();
	}

}
