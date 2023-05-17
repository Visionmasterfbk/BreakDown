package breakDown;

/**
 * Diese Klasse ist für die Systemverwaltung.
 * In Bearbeitung. Derzeit nur für die das Laden eines neuen Levels
 * @author floriank
 *
 */
class SystemGame {
	/**
	 * Funktion startet das neue Level oder beendet das Spiel.
	 * @throws Exception
	 */
	void toNextLevel() throws Exception{

		if(Main.getGame().getGameRun()==false) {
			Main.setLevelNumber(1);
			if(Main.getLevelNumber()<=Main.getMAXLEVEL()) {

				try {
					Main.getGame().refresh(Main.getLevelNumber());
				} catch (Exception e) {
					throw new Exception("Fehler beim Laden des Levels");
				}
			}
			else {
				Main.getGame().endOfGame();
			}
			
	
		}
	}
		
}
