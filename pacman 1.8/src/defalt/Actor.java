package defalt;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Actor {

	private double speed = 0.1;
	private double xPos = 0;
	private double yPos = 0;
	protected int direction = EAST;
	protected int nextDirection = EAST;
	
	public static final int NORTH = 90;
	public static final int EAST = 0;
	public static final int SOUTH = 270;
	public static final int WEST = 180;
	
	private PacmanMain map;
	private Color colour = Color.BLACK;
	
	public Actor(PacmanMain pacmanMain) {
		map = pacmanMain;
	}
	
	public PacmanMain getMap() {
		return map;
	}
	
	public void setSpeed(double s) {
		speed = s;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setXpos(double x) {
		xPos = x;
	}
	
	public void setYpos(double y) {
		yPos = y;
	}
	
	public void setPos(double x, double y) {
		xPos = x;
		yPos = y;
	}
	
	public double getXPos(){
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	public void setColour(Color setColour) {
		colour = setColour;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int d) {
		direction = d;
	}
	
	public void setNextDirection(int d) {
		nextDirection = d;
	}
	
	public int getNextDirection() {
		return nextDirection;
	}
	
	public abstract void drawSelf(Graphics2D g);
	
	public Coordinate getMyCord() {
		Coordinate ret = new Coordinate();
		ret.x = getXPos();
		ret.y = getYPos();
		
		return ret;
	}
	
	public Coordinate getNextPosition(int direction) {
		Coordinate ret = getMyCord();
		
		if(direction == NORTH) {
			ret.y -= getSpeed();
		}
		
		if(direction == EAST) {
			ret.x += getSpeed();
		}
		
		if(direction == SOUTH) {
			ret.y += getSpeed();
		}
		
		if(direction == WEST) {
			ret.x -= getSpeed();
		}
		
		return(ret);
	}
	
	public boolean canMove(int direction) {
		Coordinate nextPos = getNextPosition(direction);
		
		if(map.checkIfOutOfBounds(getXPos(), getYPos(), 0.9, 0.9)) {
			
			yPos = 12;
			
			if (xPos > 20) {
				xPos = 0;
			}
			
			if (xPos < 0) {
				xPos = 20;
			}
			
			return true;
		}
		
		else if(map.checkEachPartInWall(nextPos.x, nextPos.y, 0.9, 0.9)) {
			return false;
		}
		
		else {
			return true;
		}
		
	}
	
	public void updatePos() {
		if(canMove(nextDirection)) {
			direction = nextDirection;
		}
		
		if(canMove(direction)) {
			Coordinate newPos = getNextPosition(direction);
			setPos(newPos.x, newPos.y);
		}
	}

	protected abstract void eatDots();

	protected abstract void setDead(boolean b);

}
