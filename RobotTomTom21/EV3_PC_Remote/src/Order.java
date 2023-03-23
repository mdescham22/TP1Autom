/* classe Order
 * Ordre (angle, distance, vitesse)
 */
public class Order {
	private int angle;
	private int distance;
	private int speed;

	public Order(int angle, int distance, int speed) {
		super();
		this.angle = angle;
		this.distance = distance;
		this.speed = speed;
	}

	public int getAngle() {
		return angle;
	}

	public int getDistance() {
		return distance;
	}
	
	public int getSpeed() {
		return speed;
	}

	@Override
	public String toString() {
		return "Movement [angle=" + angle + ", distance=" + distance + ", vitesse=" + speed + "]";
	}
}