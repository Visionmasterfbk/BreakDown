package items;

import javax.swing.ImageIcon;

import breakDown.Circle;
import player.Player;
/**
 * Diese Klasse erstellt ein Item, welches die Länge des Spielers vergrößert.
 * @author floriank
 *
 */
public class GrowItem extends Item implements UseImmediately{
	/**
	 * Bild wird geladen.
	 */
	private static final ImageIcon ICON = new ImageIcon(GrowItem.class.getResource("pilzKlein.png"));
	/**
	 * Initialisiert die maximale Länge des Spielers.
	 */
	private static final int MAXLENGTH=200;
	
	/**
	 * Objekt wird oberhalb des Spielfelds instanziiert.
	 * @param startX
	 */
	public GrowItem(int startX) {
		this.setBounds(startX,-ICON.getIconHeight(),ICON.getIconWidth(),ICON.getIconHeight());
		this.setIcon(GrowItem.ICON);
	}
	@Override
	public void doItem(Player player,Circle circle) {
		if(player.getWidth()<GrowItem.MAXLENGTH) {
			player.setWidth(player.getWidth()+10);
			player.setSize(player.getWidth()+10, player.getHeight());
		}
		
	}
	/**
	 * Gibt die Länge des Items zurück.
	 * @return
	 */
	public static int getItemSize() {
		return GrowItem.ICON.getIconWidth();
	}

}
