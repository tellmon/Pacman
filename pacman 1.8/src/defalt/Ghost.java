package defalt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Ghost extends Actor{
	
	public int angle = 0;
	public int rand = 0;
	
	private Color ghostColour;

	List<Integer> validMoves = new ArrayList<>();
	
	public Ghost(PacmanMain pacmanMain) {
		super(pacmanMain);
		// TODO Auto-generated constructor stub
	} 
	
	
	
	@Override
	public void drawSelf(Graphics2D g) {
		
		if (getMap().superPacman) {
			ghostColour = Color.BLUE;
		}
		
		else {
			ghostColour = getColour();
		}
		
		double sizeWidth = getMap().getPixelWidth();
		double sizeHeight = getMap().getPixelHeight();
		
		g.setColor(ghostColour); //body
		g.fillArc((int)(sizeWidth * getXPos()), (int) (sizeHeight * getYPos()), (int) sizeWidth, (int) sizeHeight, 0 , 180);
		g.fillRect((int)(sizeWidth * getXPos()), (int) (sizeHeight * getYPos()  + sizeWidth * 0.45), (int) sizeWidth, (int) (sizeHeight - sizeHeight /2));
		
		
		g.setColor(Color.WHITE); // eye socate
		g.fillOval((int)(sizeWidth * getXPos() + sizeWidth * 0.15), (int) (sizeHeight * getYPos() + sizeWidth * 0.25), (int) sizeWidth / 3, (int) sizeHeight/ 3);
		g.fillOval((int)(sizeWidth * getXPos() + sizeWidth * 0.55), (int) (sizeHeight * getYPos() + sizeWidth * 0.25 ), (int) sizeWidth / 3, (int) sizeHeight/ 3);
		
		g.setColor(Color.BLUE); //eye
		g.fillOval((int)(sizeWidth * getXPos() + sizeWidth * 0.2), (int) (sizeHeight * getYPos() + sizeWidth * 0.3), (int) sizeWidth / 5, (int) sizeHeight/ 5);
		g.fillOval((int)(sizeWidth * getXPos() + sizeWidth * 0.6), (int) (sizeHeight * getYPos() + sizeWidth * 0.3), (int) sizeWidth / 5, (int) sizeHeight/ 5);
		
		g.setColor(Color.BLACK); //feet
		g.fillRect((int)(sizeWidth * getXPos() + sizeWidth * 0.15), (int) (sizeHeight * getYPos() + sizeWidth * 0.75), (int) sizeWidth / 5, (int) sizeHeight/ 5);
		g.fillRect((int)(sizeWidth * getXPos() + sizeWidth * 0.65), (int) (sizeHeight * getYPos() + sizeWidth * 0.75), (int) sizeWidth / 5, (int) sizeHeight/ 5);
	}
	
	public int getTurningAngle(int angle) {
		return ((getDirection() + 360 + angle) % 360);
	}
	
	public void turn(int angle) {
		setNextDirection(getTurningAngle(angle));
	}


	@Override
	protected void eatDots() {
		// TODO Auto-generated method stub
		
	}


}
