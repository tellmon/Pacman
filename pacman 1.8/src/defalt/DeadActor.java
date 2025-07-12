package defalt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class DeadActor implements ActionListener{

	private Color originalColour;
	private Timer deadGhostTimer = new Timer(1000, this); //1 second per tick timer.
	
	private int deadGhost = 0; // counter
	private int deadGhostActor = 0; // stores there actors number
	
	private boolean hasReturned = false;
	private boolean active = false;
	
	/*
	 * to do it proble do it in the way listed
	 * set values. colour and i number and active = true
	 * start timer
	 * 
	 * when checking
	 * 
	 * for i in list():
	 * 	if list[i].active() = true
	 * 		if getHadReturend = true
	 *			get the colour and the i number
	 *  		reset all verivles;
	 */
	
	public boolean getActive() {
		return active;
	}
	
	public void resetAllVerivles () {
		hasReturned = false;
		active = false;
		deadGhost = 0;
		deadGhostActor = 0; 
		
	}
	
	public boolean getHasReturned() {		
		return hasReturned;
	}
	
	public void startTheCounDown() {
		deadGhostTimer.start();
		active = true;
	}
	
	public void setOriginalColour(Color colour) {
		originalColour = colour;
	}
	
	public Color getOriginalColour() {
		return originalColour;
	}
	
	public void setActorsNumber(int num) {
		deadGhostActor = num;
	}
	
	public int getActorsNumber() {
		return deadGhostActor;
	}

 	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deadGhostTimer) {
			deadGhost += 1;
			if (deadGhost > 10) {
				deadGhost = 0;
				deadGhostTimer.stop();
				hasReturned = true;
			}
		}
		
	}
}