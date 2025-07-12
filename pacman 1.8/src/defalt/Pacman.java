package defalt;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pacman extends Actor {

	private int endAngle = 30;
	private int startAngle = 300;
	
	private int resetEndAngle = endAngle;
	private int resetStartAngle = startAngle;

	private Color pacmanColour = Color.YELLOW;
	
	private boolean dead = false;
	
	public Pacman(PacmanMain pacmanMain) {
		super(pacmanMain);
	}
	
	@Override
	public void drawSelf(Graphics2D g) {
		
		if (dead) {
			pacmanColour = Color.black;
		}
		
		else {
			pacmanColour = Color.yellow;
		}
		
		g.setColor(pacmanColour);
		
		double sizeWidth = getMap().getPixelWidth();
		double sizeHeight = getMap().getPixelHeight();
		
		
		g.fillArc((int)(sizeWidth * getXPos()), (int) (sizeHeight * getYPos()), (int) sizeWidth, (int) sizeHeight, getDirection() + endAngle, startAngle);	
		
		endAngle -= 3;
		
		if (endAngle < 0) {
			endAngle = resetEndAngle;
		}
		
		startAngle += 6;
		
		if (startAngle > 360) {
			startAngle = resetStartAngle;
		}
		
	}
	
    @Override
	public void eatDots() {
		Coordinate nextPos = getNextPosition(direction);
		
		getMap().whatAmIOn(nextPos.x, nextPos.y);		
	}

	@Override
	protected void setDead(boolean b) {
		dead = b;
	}
}
