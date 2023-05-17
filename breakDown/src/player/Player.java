package player;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * Diese Klasse erstellt und bearbeitet den Spieler.
 * @author floriank
 *
 */
public class Player extends JPanel{

	private int width = 50;
	private boolean right;
	private boolean left;
	private int step=5;
	private int panelWidth;
	private boolean startTheBall;
	
	public Player(int panelWidth,int panelHeight) {
		this.setStart(panelWidth, panelHeight);
		this.setBackground(new Color(0, 0, 0));
	}
	//Getter
	/**
	 * Gibt die aktuelle geschwindigkeit zurück
	 * @return
	 */
	public int getStep() {
		return this.step;
	}
	/**
	 * Gibt zurück ob der Ball gestartet wurde.
	 * @return
	 */
	public boolean getStartTheBall() {
		return this.startTheBall;
	}
	/**
	 * setzt die Geschwindigkeit.
	 * @param newStep
	 */
	public void setStep(int newStep) {
		this.step = newStep;
		
	}
	// Setter
	/**
	 * Funktion verlängert die Breite des Spielers.
	 * @param newWidth
	 */
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	/**
	 * Manipuliert das Attribut startTheBall.
	 * @param b
	 */
	public void setStartTheBall(boolean b) {
		this.startTheBall=b;
		
	}
	/**
	 * Setzt die Bewegungsflags anhand der Tastatureingaben auf True.
	 * @param sign
	 */
	public void setMoveBoolean(char sign) {
		if(sign=='a')
			this.left=true;
		else if(sign=='d')
			this.right=true;
	}
	/**
	 * Setzt die Bewegungsflags auf False, wenn die Tasten losgelassen werden
	 * @param sign
	 */
	public void deleteMoveBoolean(char sign) {
		if(sign=='a')
			this.left=false;
		else if(sign=='d')
			this.right=false;
	}
	/**
	 * Funktion die das Bewegen des Players ausführt und darauf achtet,
	 * dass dieser sich innerhalb der Map befindet.
	 */
	public void move() {
		if(right) {
			if(this.getX() + step >this.panelWidth-this.getWidth()) {
				this.setBounds(this.panelWidth-this.width, this.getY(), this.getWidth(), this.getHeight());
			}
			else {
				this.setBounds(this.getX() + step, this.getY(), this.getWidth(), this.getHeight());
			}
		}
		else if(left) {
			if(this.getX() - step < 0) {
				this.setBounds(0, this.getY(), this.getWidth(), this.getHeight());
			}
			else {
				this.setBounds(this.getX() - step, this.getY(), this.getWidth(), this.getHeight());
			}
		}
	}
	/**
	 * Funktion um den Startpunkt zu setzten.
	 * @param panelWidth
	 * @param panelHeight
	 */
	public void setStart(int panelWidth, int panelHeight) {
		this.panelWidth = panelWidth;
		this.setBounds(this.panelWidth/2-this.width/2, panelHeight-50, this.width, 10);
		
	}
}
