package load;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import walls.HardWall;
import walls.MetalWall;
import walls.NormalWall;
import walls.Wall;


/**
 * Diese Klasse lädt aus der Leveldatei die Größe der Map und die Anzahl und Typen der Mauern.
 * @author floriank
 *
 */
public class LoadLevel {
	private int panelWidth;
	private int panelHeight;
	/**
	 * Array der Wände wobei gilt walls[Index_der_X_Position] [Index_der_Y_Position]
	 * Es wird von einem Raster 50x50 ausgegangen. Nur Wände mit diesen Maaßen sollten verwendet werden.
	 */
	private Wall[][] walls;
	/**
	 * ließt das File.
	 * 1. Zeile sind Breite und Höhe der map.
	 * weitere Zeilen beschreiben den Aufbau der Wände.
	 */
	private RandomAccessFile levelFile;
	/**
	 * Anzahl der Wände die zerstört werden müssen um das Level zu meistern.
	 */
	private int numberOfWallToDestroy;
	
	public LoadLevel(int level) throws Exception {
		File file = new File("src/load/level" + level + ".txt");
		
		try {
			this.levelFile = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new Exception("Beim Lesen der Datei: " + "src/breakupNeu/level" + level + ".txt\n ist ein Fehler aufgetreten");
		}
		
		int cnt_lines = this.countLines();
		int cnt_rows = this.countRows();
		
		this.setWidthAndHeightOfTheLevel();
		
		/**
		 * Es wird überprüft ob der Inhalt des Files im passenden Format ist.
		 */
		if((this.panelHeight/50)-2>cnt_lines && this.panelWidth/50==cnt_rows) {
			this.setWall(cnt_rows);
		}
		else
			throw new Exception("Die Datei ist nicht genau Formartiert");

		/**
		 * Attribute die nicht mehr benötigt werden, werden gelöscht.
		 */
		this.levelFile.close();
		file = null;
		this.levelFile=null;
		
		
	}
	// Getter
	/**
	 * Gibt die Anzahl der noch zu zerstörenden Wände zurück.
	 * @return
	 */
	public int getNumberOfWallToDestroy() {
		return this.numberOfWallToDestroy;
	}
	/**
	 * Gibt das Array der Wände zurück.
	 * @return
	 */
	public Wall[][] getWalls(){
		return this.walls;
	}
	/**
	 * gibt die Höhe der Map zurück
	 * @return
	 */
	public int getPanelHeight() {
		return this.panelHeight;
	}
	/**
	 * gibt die Breite der Map zurück
	 * @return
	 */
	public int getPanelWidth() {
		return this.panelWidth;
	}
	
	// Funktion zur Manipualation (Setter)
	/**
	 * Setzt an einen bestimmten Index den Inhalt auf null.
	 * @param newMapIndexPosX
	 * @param newMapIndexPosY
	 */
	public void deleteWall(int newMapIndexPosX, int newMapIndexPosY) {
		this.walls[newMapIndexPosX][newMapIndexPosY]=null;
	}
	/**
	 * Reduziert die Anzahl der zu zerstörenden Wände um 1
	 * und gibt die neue Anzahl zurück
	 * @return
	 */
	public void reduceNumberOfWallToDestroy() {
		this.numberOfWallToDestroy -=1;
	}
	
	// private Methoden
	
	
	/**
	 * Ließt das File und erstellt das Wall Array.
	 * @param cnt_rows
	 * @throws Exception
	 */
	private void setWall(int cnt_rows) throws Exception {
		int mapHeight = this.panelHeight/50;
		this.walls = new Wall[cnt_rows][mapHeight];
		int index_line=0;
		
		String[] lineArray;
		String line;
		
		while((line = levelFile.readLine())!=null) {
			lineArray = line.split(",");
			
			if(lineArray.length!=cnt_rows) {
				throw new Exception("Die Ladedatei ist Fehlerhaft");
			}
			else {
				for(int index_row=0;index_row<cnt_rows;index_row++) {
					this.walls[index_row][index_line] = this.checkObject(lineArray[index_row],index_row,index_line);
				}
			}
			index_line+=1;
		}		
		
	}
	/**
	 * Anahnd der Zahl wird eine Wand Instanziiert.
	 * @param string
	 * @param index_row
	 * @param index_line
	 * @return
	 * @throws Exception
	 */
	private Wall checkObject(String string, int index_row, int index_line) throws Exception {
		switch (string) {
		case "0":
			return null;
		case "1":
			this.numberOfWallToDestroy +=1;
			return new NormalWall(index_row,index_line);
		case "2":
			this.numberOfWallToDestroy +=1;
			return new HardWall(index_row,index_line);
		case "3":
			return new MetalWall(index_row,index_line);
		default:
			throw new Exception("Objekt Zahl der Ladedatei existiert nicht");
		}

	}

	/**
	 * Beim Leser der ersten Zeile, wird die Breite und Höhe des Levels gesetzt.
	 * @throws Exception
	 */
	private void setWidthAndHeightOfTheLevel() throws Exception {
		String line = levelFile.readLine();
		String[] lineArray = line.split(","); 
		try {
			this.panelWidth = Integer.parseInt(lineArray[0]);
			this.panelHeight = Integer.parseInt(lineArray[1]);
		} 
		catch (Exception e) {
			throw new Exception("Die Datei ist Fehlerhaft");
		}
	}
	/**
	 * zählt die Anzahl der Zeilen unt gibt diese zurück.
	 * @return
	 * @throws IOException
	 */
	private int countLines() throws IOException {
		int cnt=0;
		while(this.levelFile.readLine()!=null) {
			cnt++;
		}
		this.levelFile.seek(0);
		return cnt-1;
	}
	/**
	 * zählt die Anzahl der Spalten eine Zeile und gibt diese zurück.
	 * @return
	 * @throws Exception
	 */
	private int countRows() throws Exception {
		this.levelFile.readLine();
		String secondLine = this.levelFile.readLine();
		try {
			this.levelFile.seek(0);
			return secondLine.split(",").length;
		}
		catch (Exception e) {
			throw new Exception("Die LadeDatei hat einen Fehler in der Anzahl der Spalten");
		}
	}

}
