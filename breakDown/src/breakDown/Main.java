package breakDown;
/**
 * Diese Klasse dient als Startpunkt für das Programm
 * @author floriank
 *
 */
class Main {
	private static Game game;
	private static int levelNumber=1;
	private static final int MAXLEVEL=3;
	private static SystemGame systemGame;
	
	public static void main(String[] args) throws Exception{
		
		try {
			Main.game = new Game(Main.levelNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("Fehler beim Laden des Levels.");
		}
		Main.systemGame = new SystemGame();
		

	}
	static SystemGame getSystemGame() {
		return systemGame;
	}
	static int getLevelNumber() {
		return Main.levelNumber;
	}
	static int getMAXLEVEL() {
		return Main.MAXLEVEL;
	}
	static void setLevelNumber(int number) {
		Main.levelNumber += number;
	}
	static Game getGame() {
		return Main.game;
	}

}
