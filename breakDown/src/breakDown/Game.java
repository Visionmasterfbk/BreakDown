 package breakDown;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import items.FastMouse;
import items.GrowItem;
import items.Item;
import items.SlowBall;
import items.UseImmediately;
import load.LoadLevel;
import player.Player;
import walls.Wall;
/**
 * Diese Klasse verwaltet das Game.
 * @author floriank
 *
 */
class Game{
	
	private boolean gameRun=true;
	private LoadLevel level;
	private Window window;
	private Player player;
	private Circle circle;
	private Timer timer;
	private Item item;
	/**
	 * Counter: Wenn der Counter ein bestimmten Wert erreicht, wird ein Item generiert.
	 */
	private int itemCounter;
	
	Game(int levelNumber) throws Exception{
		/**
		 * Instanziierung der Objekte.
		 */
		try {
			level = new LoadLevel(levelNumber);
		} catch (Exception e) {
			throw new Exception("Fehler beim Laden des Levels");
		}
		window = new Window(level.getPanelWidth(), level.getPanelHeight());
		player = new Player(level.getPanelWidth(), level.getPanelHeight());
		circle = new Circle(level.getPanelWidth(), level.getPanelHeight());
		
		/**
		 * Übergabe an das HauptPanel vom Window Objekt.
		 */
		window.addKeyListener(new MyKeyListener());
		window.add(player);
		window.add(circle);
		window.addWalls(level.getWalls());
		/**
		 * Initialisierung und Start des Timers für den Loop innerhalb eines Levels.
		 */
		this.timer = new Timer(20, new loopPerLevel());
		this.getTimer().setRepeats(true);
        this.getTimer().start();
	}
	// Getter
	boolean getGameRun() {
		return this.gameRun;
	}
	// setter
	void setGameRun(boolean b) {
		this.gameRun = b;
	}
	// Funktionen
	
	/**
	 * Funktion um das derzeitig Panel zu verändern.
	 * Diese Funktion wird nach der Instanziierung verwendet.
	 * @param levelNumber
	 * @throws Exception
	 */
	void refresh(int levelNumber) throws Exception{
		/**
		 * Der circle wird aus dem Panel gelöscht, da dieser neu Instanziiert wird.
		 * Dadurch bekommt er wieder seine StandartWerte.
		 */
		window.remove(circle);
		
		/**
		 * Instanzieerung der neuen Objekte.
		 */
		try {
			level = new LoadLevel(levelNumber);
		} catch (Exception e) {
			throw new Exception("Fehler beim Laden des Levels");
		}
		circle = new Circle(level.getPanelWidth(), level.getPanelHeight());
		
		
		/**
		 * Startbedinung für den Spieler werden wieder hergestellt
		 */
		player.setStart(level.getPanelWidth(),level.getPanelHeight());
		player.setStartTheBall(false);
		
		/**
		 * HauptPanel wird an die neue Welt angeglichen.
		 */
		window.setSize(level.getPanelWidth() + 16, level.getPanelHeight() +39);
		
		/**
		 * Übergabe an das HauptPanel vom Window Objekt.
		 */
		window.getContentPane().add(player);
		window.getContentPane().add(circle);
		window.addWalls(level.getWalls());
		
		this.gameRun = true;
		this.getTimer().start();
	}
	/**
	 * Diese Funktion beendet das Spiel.
	 */
	void endOfGame() {
		JLabel label = new JLabel("Herzlichen Glückwunsch Sie haben gewonnen");
		label.setBounds(this.window.getWidth()/2-135,this.window.getHeight()/2,270,20);
		this.window.add(label);
		this.window.repaint();
		
	}
	void die() {
		JLabel label = new JLabel("Du bist gestorben");
		label.setBounds(this.window.getWidth()/2-50,this.window.getHeight()/2,100,20);
		this.window.add(label);
		this.window.repaint();
		
	}
	Timer getTimer() {
		return timer;
	}

	/**
	 * Diese Klasse verwaletet den KeyListener.
	 * @author floriank
	 *
	 */
	private class MyKeyListener implements KeyListener {
		/**
		 * Wenn zu Beginn die Leertaste gedrückt wird startet das Spiel.
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar()==' ') {
				if(!player.getStartTheBall())
					player.setStartTheBall(true);
			}
		}

		/**
		 * Wenn "a" oder "b" gehalten wird, bewegt sich der Player.
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()=='a' || e.getKeyChar()=='d') {
				player.setMoveBoolean(e.getKeyChar());
			}
		}
		/**
		 * Beim Loslassen der Tasten, wird die Bewegung beendet.
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyChar()=='a' || e.getKeyChar()=='d') {
				player.deleteMoveBoolean(e.getKeyChar());
			}
		}
	}
	/**
	 * Diese Klasse generiert ActionListener für loop innerhalb eines Levels.
	 * @author floriank
	 *
	 */
	private class loopPerLevel implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			player.move();
			
			/**
			 * Sobald der Ball vom Spieler gestartet wird, bewegt sich dieser und der TimeCounter wird hochgezählt.
			 */
			if(player.getStartTheBall()) {
				itemCounter +=1;
				circle.move(level,window,player);	
			}
			/**
			 * Ansonsten wird der Ball auf den Spieler gesetzt.
			 */
			else {
				circle.setBoundsToPlayer(player.getX(), player.getWidth());
			}
			/**
			 * Wenn der Counter einen bestimmten Wert erreicht, wird ein Item generiert.
			 */
			if(itemCounter>400) {
				itemCounter=0;
				
				item = createItem();
				// zweites Argument setzt das Item in den Vordergrund.
				window.add(item,2);
			}
			/**
			 * Wenn ein Item instanziert wurde, bewegt sich dieser nach unten.
			 * Wenn Kollesion mit dem Spieler, wird das Item ausgelöst.
			 */
			if(item != null) {
				item.move();
				/**
				 * Ist das Item nicht null, so wurde das Item getroffen oder befindet sich außerhalb der Map
				 */
				Item itemCheck;
				/**
				 * Item wurde vom Spieler getroffen
				 */
				if((itemCheck = item.collisionWithPlayer(player)) !=null) {
					// UseImmediately wird abegfragt.
					if(itemCheck instanceof UseImmediately) {
						((UseImmediately) itemCheck).doItem(player,circle);
					}
					item=null;
					window.remove(itemCheck);
				}
				/**
				 * Item ist außerhalb der Map und wird gelöscht.
				 */
				else if( (itemCheck = item.outOfMap(level.getPanelHeight()) ) != null) {
					item=null;
					window.remove(itemCheck);
				}
			}
			/**
			 * Wenn alle Wände zerstört wurden, wurde das Level beendet.
			 * Loop des Levels wird beendet und die des Systems gestartet.
			 */
			if(level.getNumberOfWallToDestroy()<=0) {
				/**
				 * Unzerstörbare Mauern werden gelöscht.
				 */
				for(Wall[] wallArray : level.getWalls()) {
					for(Wall com : wallArray) {
						if(com!=null)
							window.remove(com);
					}
					
				}
				gameRun=false;
				getTimer().stop();
				try {
					Main.getSystemGame().toNextLevel();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			window.repaint();
		}
	}
	private Item createItem() {
		int value;
		if(Main.getLevelNumber()==1) {
			value = 2;
		}
		else
			value = 3;
		int random = (int) (Math.random()*value);
		switch (random) {
		case 0:
			return new GrowItem(  (int) (Math.random()*(level.getPanelWidth()-GrowItem.getItemSize())  ) );
		case 1:
			return new FastMouse(  (int) (Math.random()*(level.getPanelWidth()-FastMouse.getItemSize())  ) );
		case 2:
			return new SlowBall(  (int) (Math.random()*(level.getPanelWidth()-SlowBall.getItemSize())  ) );
		}
		return null;
	}
}
