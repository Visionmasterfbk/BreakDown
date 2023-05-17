package walls;

/**
 * Blaupausen für alle Mauern die zerstörbar sind.
 * @author floriank
 *
 */
public abstract class DestroyableWall extends Wall{
	private int hp;
	protected int imageIndex;
	public DestroyableWall(int xPos, int yPos, int hp) {
		super(xPos, yPos);
		this.hp = hp;
		this.imageIndex=0;
	}
	/**
	 * reduziert die HP der Mauer und gibt diese zurück, falls Sie zerstört wurde.
	 * @return
	 */
	public Wall reducedHp() {
		this.hp -=1;
		if(this.hp<=0)
			return this;
		else {
			this.imageIndex+=1;
		}
		return null;
	}

}
