package breakDown;

import javax.swing.JFrame;
import javax.swing.JLabel;

import walls.Wall;
/**
 * Diese Klasse verwaltet das Window und dessen HauptPanel.
 * @author floriank
 *
 */
class Window extends JFrame{
	/**
	 * Label für Legende der Bewegungstasten.
	 */
	private JLabel label;
	
	Window(int width,int height) {
		
		this.setSize(width+16, height+39);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		this.label = new JLabel("Bewegen mit 'a' und 'd': Start mit Space");
		this.label.setBounds(0, 2, 300,20);
		this.add(this.label);
		
		this.setVisible(true);
	}
	/**
	 * Funktion übergibt die Wände eines Levels an das HauptPanel.
	 * Diese werden von der Klasse LoadLevel geladen
	 */
	void addWalls(Wall[][] walls) {
		for(Wall[] wallArray : walls) {
			for(Wall wall : wallArray) {
				if(wall != null) {
					this.getContentPane().add(wall);

				}
			}
		}
	}

}
