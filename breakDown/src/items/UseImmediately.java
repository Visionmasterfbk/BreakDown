package items;

import breakDown.Circle;
import player.Player;
/**
 * Interface ob ein Item sofort eingesetzt wird.
 *
 * @author floriank
 *
 */
public interface UseImmediately {
	/**
	 * Funktion die den Effekt des Items ausführt.
	 * @param player
	 */
	void doItem(Player player, Circle circle);
}
