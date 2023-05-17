package breakDown;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import load.LoadLevel;
import player.Player;
import walls.CanChangeImage;
import walls.DestroyableWall;
import walls.Wall;

/**
 * Diese Klasse erstellt den Ball für das Spiel.
 * @author floriank
 *
 */
public class Circle extends JLabel{
	/**
	 * Lädt das Bild, welches als Ball genutzt wird.
	 */
	private final ImageIcon ICON = new ImageIcon(Circle.class.getResource("ball.png"));
	/**
	 * Konstante zur Addition der halben Bildbreite um den Mittelpunkt zu errechnen.
	 * Da das Bild Quadratisch ist, gilt die Konstante auch für die Höhe
	 */
	private final int TARA = this.ICON.getIconWidth() % 2 == 1 ? (this.ICON.getIconWidth()+1)/2 : this.ICON.getIconWidth()/2;
	/**
	 * Boolean in welche Richtung sich der Ball bewegt.
	 * Bemerkung wenn down=false bedeutet dies, dass der Ball sich nach oben bewegt
	 */
	private boolean down, left;
	/**
	 * Schrittweite der Bewegung per Frame.
	 * Start bei 1,41
	 */
	private double step=Math.sqrt(2);
	/**
	 * Counter der sich bei jedem Abprall erhöht und ab dem Wert 10 die geschwindigkeit step erhöht.
	 */
	private int stepCounter;
	/**
	 * Winkel den der Ball gerade hat in Bogenmaß.
	 * Standart 45°
	 */
	private double angle = Math.PI/180*45;
	/**
	 * Zur Berechnung der Position wird der Mittelpunkt genutzt.
	 */
	private int middlePosX, middlePosY;
	
	Circle(int panelWidth,int panelHeight) {
		/**
		 * Setzt den Ball auf den Player in der Mitte
		 */
		this.setBounds(panelWidth/2, panelHeight-55, 5, 5);
		/**
		 * Übergibt dem Label das gealdene Image.
		 */
		this.setIcon(this.ICON);
	}
	/**
	 * Setzt den Mittelpunkt des Balles anhand der jetzigen Position.
	 */
	private void setMiddleOfTheBall() {
		this.middlePosX = this.getX()+this.TARA;
		this.middlePosY = this.getY()+this.TARA;
	}
	/**
	 * Funktion addiert einen Winkel (Bogenmaß) an den vorhandenen Winkel und erhöht die Geschwindigkeit.
	 * @param d
	 */
	private void addAngle(double d) {
		this.angle += d;
		if(this.angle<=Math.PI/180*10) {
			this.angle = Math.PI/180*10;

		}
		else if(this.angle > Math.PI/180*80) {
			this.angle = Math.PI/180*80;

		}
		if(this.step<8) {
			this.stepCounter += 1;
			if(this.stepCounter>10) {
				this.stepCounter=0;
				this.step +=1;
			}
		}
	}
	/**
	 * Funktion zur Bewegung des Balls. Überprüfung von Kollision mit Wänden oder ob der Ball außerhalb der Map ist.
	 * In dieser Funktion wird überprüft ob der Ball einen Bereich von 50 Pixeln überschreitet.
	 * Wenn dies der Fall ist, können Wände mit fester Größe 50x50 getroffen werden,
	 * sofern diese an der Stelle existieren.
	 * @param level
	 * @param window
	 * @param player
	 */
	void move(LoadLevel level, Window window, Player player) {
		/**
		 * neue Position nach der Bewegung. Abhänig vom Aprall von Objekten
		 */
		double newMiddlePosX, newMiddlePosY;
		/**
		 * Berechnung der Bewegung auf der X-Achse. Abhänig vom Winkel.
		 */
		double stepPosX = this.step/Math.cos(this.angle);
		/**
		 * Berechnung der Bewegung auf der Y-Achse. Abhänig vom Winkel.
		 */
		double stepPosY = this.step/Math.sin(this.angle);
		/**
		 * initialisiert den Mittelpunkt
		 */
		this.setMiddleOfTheBall();

		/**
		 * Berechung der neuen Position.
		 */
		if(this.down)
			newMiddlePosY = this.middlePosY + stepPosY;
		else
			newMiddlePosY = this.middlePosY - stepPosY;
		if(this.left)
			newMiddlePosX = this.middlePosX - stepPosX;
		else
			newMiddlePosX = this.middlePosX + stepPosX;
		
		/**
		 * Berechnung der Indexe vom walls_Array. Bei der derzeitigen Position.
		 * Die Map besteht aus 50x50 Raster.
		 */
		int currentMapIndexPosX = (this.middlePosX) / 50;
		int currentMapIndexPosY = (this.middlePosY) / 50;

		/**
		 * Berechnung der Indezes vom walls_Array. Bei der neuen Position.
		 * Wenn kleiner 0 muss auf -1 abgerundet werden. Im Positiven Bereich geschieht das abrunden durch das TypeCasting zum integer.
		 */
		int newMapIndexPosX = newMiddlePosX<0 ? -1 : (int) ((newMiddlePosX)/50);
		int newMapIndexPosY = newMiddlePosY<0 ? -1 : (int) ((newMiddlePosY)/50);
		/**
		 * Sind beide Abfragen True, so wird sowohl in horizontaler als auch in vertikaler Ebene ein Raster von 50px durchbrochen.
		 * Es werden beide Wände zerstört.
		 */
		if(currentMapIndexPosX !=  newMapIndexPosX
				&& currentMapIndexPosY !=  newMapIndexPosY) {
			/**
			 * Länge vom derzeitigen X-wert bis zum Schnittpunkt des nächsten Blocks
			 */
			int lengthToBlock;
			/**
			 * Zur überprüfung ob der Wert MiddlePosX bzw. MiddlePosY sich verändert hat.
			 */
			double copyOfNewMiddlePosBefor;
			/**
			 * überprüfung welche Achse als erstes getroffen wird.
			 * Wenn Ball nach links fliegt muss die Breite eines Blocks berücksichtigt werden,
			 * da der Block von Rechts getroffen wird.
			 */
			if(this.left) {
				lengthToBlock = ((this.middlePosX - (currentMapIndexPosX) * 50));
			}
			else {
				lengthToBlock = (newMapIndexPosX * 50) - (this.middlePosX);
				
			}
			/**
			 * Berechnung des MapIndexY, wenn der Ball mit dem nächsten Block kollidiert.
			 * xxxxxxxxxxxxxxxxxx
			 * x		x		x	
			 * x	**	x		x
			 * x	*  *x 		x
			 * x	*	x*		x
			 * xxxxxxxxxxxx*xxxxx		← Schnittpunkt Y-Achse durchbricht in diesem Beispiel vorher ein Raster
			 * 		*   |    *  
			 * 		*	|      *
			 * 		*************
			 * 		| stepPosX	|		
			 * 			|		|
			 * 			    ↑ lengthToBlock	
			 */
			double mapIndexPosYOnDelta = (((lengthToBlock/stepPosX) * stepPosY) + this.middlePosY)/50;
			/**
			 * Es wird überprüft ob der Wert kleiner 0 ist, da dann abgerundet werden muss.
			 * Bei positiven Werten wird dies duch das TypeCasting in integer getätigt.
			 */
			mapIndexPosYOnDelta = mapIndexPosYOnDelta<0 ? -1 : mapIndexPosYOnDelta;
			/**
			 * Block wird horizontal als erstes getroffen.
			 */
			if((int) mapIndexPosYOnDelta == currentMapIndexPosY) {
				copyOfNewMiddlePosBefor = newMiddlePosX;
				/**
				 * Bewgung der X Achse wird ausgeführt und neuer X_Wert zurückgegeben.
				 * Da als erstes Horizontal getroffen wir, ist der MapIndex von Y der derzeitige Index
				 */
					newMiddlePosX = this.getPosXCollision(newMiddlePosX, newMapIndexPosX, currentMapIndexPosY, level, window);
					
				/**
				 * Abfrage ob newMiddlePosX sich verändert hat. Wenn dies der Fall ist, ist der Ball nicht abgeprallt.
				 */
				if(copyOfNewMiddlePosBefor == newMiddlePosX) {
					newMiddlePosY = this.getPosYCollision( newMiddlePosY, newMiddlePosX, newMapIndexPosX, newMapIndexPosY, level,window,player);;
				}
				// Ball an einer Horizontalen Achse abgeprallt. Somit ist neuer MapIndex von X gleich dem alten MapIndex
				else {
					newMiddlePosY = this.getPosYCollision( newMiddlePosY, newMiddlePosX, currentMapIndexPosX, newMapIndexPosY, level,window,player);;
				}
			}
			/**
			 * Block wird Vertikal als erstes getroffen. Analag zur vorherigen if Abfrage.
			 */
			else {
				copyOfNewMiddlePosBefor = newMiddlePosY;
					newMiddlePosY = this.getPosYCollision( newMiddlePosY, newMiddlePosX, currentMapIndexPosX, newMapIndexPosY, level,window,player);;
				if(copyOfNewMiddlePosBefor==newMiddlePosY) 
					newMiddlePosX = this.getPosXCollision(newMiddlePosX, newMapIndexPosX, newMapIndexPosY, level, window);	
				else
					newMiddlePosX = this.getPosXCollision(newMiddlePosX, newMapIndexPosX, currentMapIndexPosY, level, window);	
			
			}
		}
		/**
		 * Es wird nur in der Horizontalen ein Raster von 50px*50px durchbrochen.
		 */
		else if(currentMapIndexPosX != newMapIndexPosX) {
			newMiddlePosX = this.getPosXCollision(newMiddlePosX, newMapIndexPosX, newMapIndexPosY, level,window);
		}
		/**
		 * Es wird nur in der Vertikal ein Raster von 50px*50px durchbrochen.
		 */
		else if(currentMapIndexPosY != newMapIndexPosY) {
			newMiddlePosY = this.getPosYCollision( newMiddlePosY, newMiddlePosX, newMapIndexPosX, newMapIndexPosY, level,window,player);
		}
		// Bemerkung zur fehlenden else Bedingung
		//Die neue Position wurde nicht verändert da kein Raster durchstoßen wurde.
		
		/**
		 * Die neue Position wird am Objekt gesetzt.
		 */
		this.setBounds((int) newMiddlePosX-this.TARA, (int) newMiddlePosY-this.TARA, this.getWidth(), this.getHeight());
	}
	/**
	 * Die Funktion überprüft ob sich auf der horizontalen Strecke ein Objekt befindet und gibt die Neue X-Position zurück.
	 * Diese wird verändert, wenn der Ball an einem Objekt abprallt.
	 * @param newMiddlePosX
	 * @param newMapIndexPosX
	 * @param newMapIndexPosY
	 * @param level
	 * @param window
	 * @return
	 */
	private double getPosXCollision(double newMiddlePosX, int newMapIndexPosX, int newMapIndexPosY, LoadLevel level, Window window) {
		/**
		 * überprüft ob der Ball mit der linken Levelwand kollidiert.
		 */
		if(newMapIndexPosX<0) {	
			this.addAngle(0.017);
			this.left=false;
			return newMiddlePosX =(-1)*newMiddlePosX;
		}
		/**
		 * überprüft ob der Ball mit der rechten Levelwand kollidiert.
		 */
		else if(newMapIndexPosX >= (int) (level.getPanelWidth()/50)) {
			this.addAngle(0.017);
			this.left=true;
			return newMiddlePosX = 2*level.getPanelWidth() - (newMiddlePosX);
		}
		/**
		 * überprüft ob eine Wand getroffen wird.
		 */
		else if(level.getWalls()[newMapIndexPosX][newMapIndexPosY]!=null) {
			this.addAngle(0.017);
			/**
			 * wird eine Wand getroffen, wird ihre hp ggf. reduziert.
			 */
			this.destroyWall(newMapIndexPosX, newMapIndexPosY, level, window);
			/**
			 * errechnet neue X_Positon abhänig von der horizontalen Richtung des Balles.
			 */
			if(this.left) {
				this.left=false;
				return newMiddlePosX = (newMapIndexPosX+1) * 50 + (newMapIndexPosX+1) * 50 - (newMiddlePosX);
			}
			else {
				this.left=true;
				return newMiddlePosX = 2 * newMapIndexPosX * 50 - (newMiddlePosX);
			}
		}
		return newMiddlePosX;
	}
	/**
	 * Die Funktion überprüft ob sich auf der vertikalen Strecke ein Objekt befindet und gibt die Neue Y-Position zurück.
	 * Diese wird verändert, wenn der Ball an einem Objekt abprallt.
	 * @param newMiddlePosY
	 * @param newMapIndexPosX
	 * @param newMapIndexPosY
	 * @param level
	 * @param window
	 * @return
	 */
	private double getPosYCollision(double newMiddlePosY, double newMiddlePosX, int newMapIndexPosX, int newMapIndexPosY, LoadLevel level, Window window, Player player) {
		/**
		 * Erklärung Analog zu getNewPositionPosXAfterCollision
		 * Die Funktion wurde erneut geschrieben, da andere Faktoren abgefragt werden.
		 * Außerdem wird überprüft ob eine Kollesion mit dem Spieler erfolgt
		 * Oder ob der Ball die untere Kante berürt, dann ist das Spiel vorbei.
		 */
		if(newMapIndexPosY<0) {
			this.addAngle(0.017);
			this.down=true;
			return newMiddlePosY =(-1)*newMiddlePosY;
		}
		/**
		 * Wenn Y größer als die Map stirbt man.
		 */
		else if(newMapIndexPosY>=(int) (level.getPanelHeight()/50)) {
			this.down=false;
			Main.getGame().getTimer().stop();
			Main.getGame().setGameRun(false);
			Main.getGame().die();
			return newMiddlePosY = 2*level.getPanelHeight() - (newMiddlePosY);
		}
		else if(level.getWalls()[newMapIndexPosX][newMapIndexPosY]!=null) {
			this.destroyWall(newMapIndexPosX, newMapIndexPosY, level, window);
			this.addAngle(0.017);
			if(this.down) {
				this.down=false;
				return newMiddlePosY = 2 * newMapIndexPosY * 50 - (newMiddlePosY);
				
			}
			else {
				this.down=true;
				return newMiddlePosY = (newMapIndexPosY+1) * 50 + (newMapIndexPosY+1) * 50 - (newMiddlePosY);
			}
		}
		else if(this.down && newMapIndexPosY==(int) level.getPanelHeight()/50 -1) {
			return newMiddlePosY = this.collisionWithPlayer(player, newMiddlePosX, newMiddlePosY);
		}
		return newMiddlePosY;
	}
	
	/**
	 * Funktion überprüft ob der Ball mit dem Spieler kollidiert
	 * und gibt die neue Y_Position nach dem Aufprall zurück.
	 * Sollte der Ball im ersten oder letzten viertel des Players getroffen werden. Wird der Winkel verändert.
	 * @param player
	 * @param newMiddlePosX
	 * @param newMiddlePosY
	 * @return
	 */
	private double collisionWithPlayer(Player player, double newMiddlePosX, double newMiddlePosY) {
		if(newMiddlePosX>=player.getX() && newMiddlePosX <= player.getX()+player.getWidth()) {
			this.down=false;
			if( ( this.left && newMiddlePosX <= player.getX()+( player.getWidth()/4 ))
					|| (this.left==false && newMiddlePosX >= player.getX()+( player.getWidth()/4*3)  ) ) {
				this.addAngle(0.017);
			}
			else if( ( this.left==false && newMiddlePosX <= player.getX()+( player.getWidth()/4 ))
					|| (this.left && newMiddlePosX >= player.getX()+( player.getWidth()/4*3)  ) ) {
				this.addAngle(-0.017*6);
			}
			
			
			
			return 2*player.getY()-newMiddlePosY;
		}
		return newMiddlePosY;	
	}
	/**
	 * Funktion überprüft ob die getroffene Mauer zerstörbar ist und reduziert Ihre HP um ein.
	 * Ist die HP 0 wird das Objekt zerstört und aus dem WindowPanel und dem level wallsArray gelöscht.
	 * @param newMapIndexPosX
	 * @param newMapIndexPosY
	 * @param level
	 * @param window
	 */
	void destroyWall(int newMapIndexPosX, int newMapIndexPosY, LoadLevel level, Window window) {

		if(level.getWalls()[newMapIndexPosX][newMapIndexPosY] instanceof DestroyableWall) {
			
			Wall wall = ((DestroyableWall) level.getWalls()[newMapIndexPosX][newMapIndexPosY]).reducedHp();
			/**
			 * Wenn ungleich null wurde die Mauer zerstört.
			 */
			if(wall != null) {
				level.reduceNumberOfWallToDestroy();
				window.remove(wall);
				level.deleteWall(newMapIndexPosX,newMapIndexPosY);
			}
			/**
			 * Andernfalls wird noch überprüft ob sich bei einer Mauer das Bild ändert.
			 */
			else {
				if(level.getWalls()[newMapIndexPosX][newMapIndexPosY] instanceof CanChangeImage) {
					((CanChangeImage) level.getWalls()[newMapIndexPosX][newMapIndexPosY]).changeImage();
				}	
			}
		}
	}
	/**
	 * Funktion damit der Ball solange er noch nicht abgefeuert wurde aud dem Spieler bleibt.
	 * @param xPosPlayer
	 * @param widthPlayer
	 */
	void setBoundsToPlayer(int xPosPlayer, int widthPlayer) {
		this.setBounds(((widthPlayer/2) + xPosPlayer), this.getY(), this.getWidth(), this.getHeight());
		
	}
	public void setStandart() {
		this.step = Math.sqrt(2);
		this.angle=Math.PI/180*45;
		
	}
}
