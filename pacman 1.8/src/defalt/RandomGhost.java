package defalt;

import java.util.Random;

public class RandomGhost extends Ghost{

	public RandomGhost(PacmanMain pacmanMain) {
		super(pacmanMain);
		// TODO Auto-generated constructor stub	
	}
	
	public void updatePos() {
		super.updatePos();	
		Random ran = new Random();
		
		// check what directions are free then generate a random number where to go
		
		
		if(canMove(getTurningAngle(0)) || canMove(getTurningAngle(-90)) || canMove(getTurningAngle(90))) { // can move forward or there is space on left or right
			
			if(canMove(getTurningAngle(0))) { // can move forward
				validMoves.add(0);
			}
			
			if(canMove(getTurningAngle(-90))) { // can move left
				validMoves.add(-90);
			}
			
			if(canMove(getTurningAngle(90))) { // can move right
				validMoves.add(90);
			}
			
		}
		
		else{
			validMoves.add(-180);
		}
		
		// takes all the true ones and make a picks randomly where to get to
		int randomIndex  = ran.nextInt(validMoves.size());
		int randomNumber = validMoves.get(randomIndex);
		
		turn(randomNumber);
		
		validMoves.clear();
	}

	@Override
	protected void setDead(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
