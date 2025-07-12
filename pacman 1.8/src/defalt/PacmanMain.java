package defalt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PacmanMain extends JPanel implements ActionListener, KeyListener{

	char[][] pacmanLevel;
	
	private Actor[] actors = new Actor[5];
	private DeadActor[] deadActor = new DeadActor[4]; 
	
	private Timer tick = new Timer(SelectOptions.tickspeed, this); //40 is defult
	private Timer powerPelletCountDown = new Timer(SelectOptions.pelletTimerSpeed, this); //1000 for a 1 second tick timer
	
	private int score = 0;
	private int pelletCount = 0;
	private int foundFree = 0;
	
	public double mapWidth, mapHeight, pixelsHeight, pixelsWide, dotWidth, dotHeight;
	
	public boolean hit = false;
	public boolean superPacman = false;
	
	public PacmanMain(){
		this.addKeyListener(this);
		this.setFocusable(true);
		
		actors[0] = new Pacman(this);
		
		for (int i=1; i<=4; i++) {
			actors[i] = new RandomGhost(this);
		}
		
		for (int i=0; i<4; i++) {
			deadActor[i] = new DeadActor();
		}
	}
	
	public void loadMap(String file) {
		tick.start();
		
		try {
			//count rows and coloums
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			int rows = 0;
			int columns = line.length();
			while (line != null) {
				line = reader.readLine();
				rows++;
			}
			
			pacmanLevel = new char [columns][rows];
			
			//read the file into pacmanLevel
			reader = new BufferedReader(new FileReader(file));
			
			for (int y=0; y<rows; y++) {
				String row = reader.readLine();
				
				for (int x= 0; x<columns; x++ ) {
					char c = row.charAt(x);
					pacmanLevel[x][y] = c;
					
					if (c == 'P') {
						actors[0].setPos(x, y);
					}
					
					if (c == 'b') {
						actors[1].setPos(x, y);
						actors[1].setColour(Color.RED);
					}
					
					if (c == 'i') {
						actors[2].setPos(x,  y);	
						actors[2].setColour(Color.CYAN);
					}
					
					if (c == 'c') {
						actors[3].setPos(x, y);
						actors[3].setColour(Color.ORANGE);
					}
					
					if (c == 'p') {
						actors[4].setPos(x, y);	
						actors[4].setColour(Color.PINK);
					}
				}
			}
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double getPixelWidth() {
		return pixelsWide;
	}
	
	public double getPixelHeight() {
		return pixelsHeight;
	}
	
	public void drawMapBase(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		mapWidth = pacmanLevel.length;
		mapHeight = pacmanLevel[0].length;
		
		pixelsHeight = this.getHeight() / mapHeight;
		pixelsWide = this.getWidth() / mapWidth;
		
		dotWidth = pixelsWide / 8; 
		dotHeight = pixelsWide /8; 
		
		if (pixelsWide < pixelsHeight) {
			pixelsHeight = pixelsWide;
		}
		
		else {
			pixelsWide = pixelsHeight;
		}
		
		if (dotWidth < dotHeight) {
			dotHeight = dotWidth;
		}
		
		else {
			dotWidth = dotHeight;
		}
		
		g.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for (int x=0; x<mapWidth; x++) {
			for(int y=0; y<mapHeight; y++) {
				// walls
				
				if(pacmanLevel[x][y] == '#') {
					g.setColor(new Color(205, 200, 207));
					g2.fillRect((int) Math.floor(x*pixelsWide), (int)  Math.floor(y*pixelsHeight), (int)  Math.ceil(pixelsWide), (int)  Math.ceil(pixelsHeight));
				}
				
				// dots
				if(pacmanLevel[x][y] == ' ') {
					g.setColor(new Color(255, 255, 0));
					g2.fillOval((int) Math.floor(x*pixelsWide + pixelsWide/2 - dotWidth/2), (int)  Math.floor(y*pixelsHeight + + pixelsHeight/ 2 - dotHeight/2), (int)  Math.ceil(dotHeight), (int)  Math.ceil(dotWidth));
				}
				
				// powerPelits
				if(pacmanLevel[x][y] == '-') {
					g.setColor(new Color(255, 255, 255));
					g2.fillOval((int) Math.floor(x*pixelsWide + pixelsWide/2 - dotWidth/2), (int)  Math.floor(y*pixelsHeight + + pixelsHeight/ 2 - dotHeight/2), (int)  Math.ceil(dotHeight * 2), (int)  Math.ceil(dotWidth * 2));
				}
			}	
		}
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		drawMapBase(g);
	          
        if(hit) {
        	actors[0].setDead(true);
        	
        	g.setFont(new Font("Arial", Font.BOLD, 20));
        	g.setColor(Color.RED);
        	g.drawString("YOU DIED. Your final score is "+score, (int) (this.getWidth() * 0.3), (int) (this.getHeight() * 0.5));
        	tick.stop();
        }
        
		for(Actor a:actors) {
			a.drawSelf(g2);
		}
    }

	
	public void whatAmIOn(double x, double y) {
		
		x = Math.round(x);
		y = Math.round(y);
		
		if (pacmanLevel[(int)x][(int)y] == ' ') { // pellet
			score += 1;
			
			pacmanLevel[(int)x][(int)y] = '.';
		}
		
		if (pacmanLevel[(int)(x)][(int)y] == '-') {// power pellet
			score += 10;
			
			if (superPacman){
				powerPelletCountDown.stop();
				pelletCount = 0;
			}
			
			superPacman = true; // start timer for 10 seconds
			
			powerPelletCountDown.start(); // get to add 1 at action performed if above 10 then stop and reset count
			
			pacmanLevel[(int)x][(int)y] = '.';
		}
		
		for (int i=1; i<=4; i++) {
			int ghostX = (int) Math.round(actors[i].getXPos());
			int ghostY = (int) Math.round(actors[i].getYPos());
			
			if (ghostX == (int) x && ghostY == (int) y && superPacman == false) { // pacman death
				hit = true;
			}
			
			if (ghostX == (int) x && ghostY == (int) y && superPacman == true) { // kill ghost
				score += 10;
				
				actors[i].setPos(10, 12);
				actors[i].setSpeed(0);

				// add way to check for a free one here and then if so stop the for loop.
				
				while(foundFree < 9) {
					if (deadActor[foundFree].getActive() == false) {
						deadActor[foundFree].setActorsNumber(i);
						deadActor[foundFree].setOriginalColour(actors[i].getColour());
						deadActor[foundFree].startTheCounDown(); // also sets active as true
						foundFree = 10;
					}
					foundFree += 1;
				}
				
				foundFree = 0;
				
				actors[i].setColour(Color.BLACK);
			}
		}
		
		if (hit) {
			deathScreen();
		}
	}
	
	public void deathScreen() {
		for (int mapX=0; mapX<mapWidth; mapX++) {
			for(int mapY=0; mapY<mapHeight; mapY++) {
				
				pacmanLevel[mapX][mapY] = '.';
			}	
		}
		
		for (int i=0; i<=4; i++) {
			actors[i].setColour(Color.black);;
		}
	}
	
	public boolean checkIfOutOfBounds(double x, double y, double width, double height){
		x = x * 10;
		x = Math.round(x);
		x = x / 10;
		
		y = y * 10;
		y = Math.round(y);
		y = y / 10;		
		
		if (x <= 0 && y > 11 && y < 13) {
			return true;
		}
		
		if (x >= 20 && y > 11 && y < 13) {
			return true;
		}
		
		return  false;
	}
	
	public boolean checkEachPartInWall(double x, double y, double width, double height) {
				
		x = x * 10;
		x = Math.round(x);
		x = x / 10;
		
		y = y * 10;
		y = Math.round(y);
		y = y / 10;		
		
		
		if (pacmanLevel[(int)x][(int)y] == '#') {
			return true;
		}
		
		if (pacmanLevel[(int)(x+width)][(int)y] == '#') {
			return true;
		}
		
		if (pacmanLevel[(int)(x+width)][(int)(y+height)] == '#') {
			return true;
		}
		
		if (pacmanLevel[(int)x][(int)(y+height)] == '#') {
			return true;
		}
		
		return false;
	}
	
	public Coordinate getPacManPos() {
		Coordinate ret = new Coordinate();
		ret.x = actors[0].getXPos();
		ret.y = actors[0].getYPos();
		
		return ret;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		updateActor();
		repaint();
		
		// check here for any dead actors that need to be revived. 
		
		for (int i=0; i<4; i++) {
			if(deadActor[i].getActive() == true) {
				if(deadActor[i].getHasReturned() == true) {
					actors[deadActor[i].getActorsNumber()].setColour(deadActor[i].getOriginalColour());
					actors[deadActor[i].getActorsNumber()].setSpeed(0.1);
					deadActor[i].resetAllVerivles();
					
				}
			}
			
		}
		
		if (e.getSource() == powerPelletCountDown) {
			pelletCount += 1;
			if (pelletCount > 10) {
				superPacman = false;
				pelletCount = 0;
				powerPelletCountDown.stop();
			}
		}
	}

	public void updateActor() {
		for(Actor a:actors) {
			a.updatePos();
			a.eatDots();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		movement(e.getKeyCode());
	}
	
	
	public void movement(int Key) { 
		if (Key == KeyEvent.VK_W || Key == KeyEvent.VK_UP) {
			actors[0].setNextDirection(Actor.NORTH);
		}
		
		if (Key == KeyEvent.VK_A || Key == KeyEvent.VK_LEFT) {
			actors[0].setNextDirection(Actor.WEST);
		}
		
		if (Key == KeyEvent.VK_S || Key == KeyEvent.VK_DOWN) {
			actors[0].setNextDirection(Actor.SOUTH);
		}
		
		if (Key == KeyEvent.VK_D || Key == KeyEvent.VK_RIGHT) {
			actors[0].setNextDirection(Actor.EAST);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
