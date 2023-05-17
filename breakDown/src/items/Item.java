package items;

import javax.swing.JLabel;

import player.Player;

/**
 * Diese Klasse dient als Blaupause für weitere Klassen der Kategorie Items.
 * Sie beinhaltet die Bewegung und die Abfrage ob das Item den Spieler trift 
 * oder außerhalb der Map ist.
 * @author floriank
 *
 */
public abstract class Item extends JLabel{
	/**
	 * Bewegunggeschwindigkeit der Items
	 */
	private final int STEP=3;
	
	/**
	 * Funktion für die Bewegung des Items
	 */
	public void move() {
		this.setBounds(this.getX(), getY()+STEP, this.getWidth(), this.getHeight());
	}
	/**
	 * Funktion gibt das Item zurück, wenn diese den Spieler trifft und damit eingesammelt wird.
	 * @param player
	 * @return Item
	 */
	public Item collisionWithPlayer(Player player) {
		/**
		 * Abefrage ob das Item die Oberkannte des Players erreicht
		 */
		if(this.getY()+this.getHeight()-STEP<=player.getY() && this.getY()+this.getHeight()>=player.getY()) {
			/**
			 * Abfrage ob die X Koordinaten kollidieren
			 */
			if(this.getX()<=player.getX()+player.getWidth() && this.getX()+this.getWidth()>player.getX()) {
				return this;
			}
		}
		return null;
	}
	/**
	 * Funktion gibt Item zurück wenn diese außerhalb der Map ist.
	 * @param panelHeight
	 * @return
	 */
	public Item outOfMap(int panelHeight) {
		if(this.getY()>=panelHeight) 
			return this;
		return null;
	}
}
