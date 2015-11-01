import java.awt.Color;


public class Enemy {
	private int initialPosX = 500; //starting x position of enemy
	private int initialPosY = 300;	//starting y position of enemy
	private int posX; //top left corner of enemies circle
	private int posY; //top left corner of enemies circle
	private int size; //enemies initial size
	private int dx; //change in x from initial pos
	private int dy; //change in y from initial pos
	private double speed; //speed of ball
	private int distanceFromUser; // the distance of the enemies to the user
	private double movementTimer; // a timer, enemies movements will be based on certain times
	private int direction = (int)Math.round((Math.random()*3)); // direction which is random between 0-3
	private int distanceX; // the x distance
	private int distanceY; // the y distance
	private Color col; //color 
	
	public Enemy(int initPosX, int initPosY, int ePosX, int ePosY, int eSize, int changeX, int changeY, double spd) {
		initialPosX = initPosX;
		initialPosY = initPosY;
		posX = ePosX;
		posY = ePosY;
		size = eSize;
		dx = changeX;
		dy = changeY;
		speed = spd;
		col = Color.red;
		// TODO Auto-generated constructor stub
	}
	public void updateDistanceFromUser() // updates the distance the enemy is from the user
	{

		distanceX = (Math.abs(GUI.user.getInitPosX() + GUI.user.getPlaySize()/2 - GUI.user.getdx() - this.getPosX()-this.getSize()/2)); // gets the x distance from user
		distanceY =(Math.abs(GUI.user.getInitPosY() + GUI.user.getPlaySize()/2 - GUI.user.getdy() - this.getPosY() - this.getSize()/2)); // gets the y distance from the user
		distanceFromUser = (int) Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2)); // sets the distance from the user
	}
	public int getDistanceFromUser() // gets the distance from the user
	{
		return distanceFromUser; //returns distance from the user
	}
	public Color getColor () // gets the color 
	{
		return col; // returns color
	}
	public boolean checkCollision() //checks collision between enemy and user
	{
		if (distanceFromUser <= this.getSize()/2 + GUI.user.getSize()/2) // if the distance from user is less than enemy size 
		{
			return true; //collision detection happened
		}
		return false; //collision detection didn't happen 
	}

	public void updateMovement() //updates the enemies movement
	{
		
		posX = initialPosX + dx; // sets the x position 
		posY = initialPosY + dy; // sets the y position


		movementTimer+=0.02; // increases movement timer, enemies movements based on this
		if (direction == 0) // if direction is 0, goes right and up
		{
			dx += speed; //
			dy +=speed;
		}
		else if (direction == 1) // if direction is 1, goes right and down
		{
			dx += speed;
			dy -=speed;
		}
		else if (direction == 2) // if direction is 2, goes left and up
		{
			dx -= speed;
			dy +=speed;
		}
		else if (direction == 3) // if direction is 3, goes left and down
		{
			dx -= speed;
			dy -=speed;
		}
		if (movementTimer >= 1) // if the movement timer less than 1 or equal
		{
			movementTimer = 0; //resets timer back to 0
			direction = (int)Math.round((Math.random()*3)); // randomly chooses sets direction 0-3
		}
		
		if (posX >= GUI.appletWidth-50) // if x position of enemy is greater than applet width
		{
			direction = (int)Math.round((Math.random()*1)) + 2; //changes the direction to directions 2 and 3 
		}
		if (posX <= 0) // if x position is less than or equal to 0
		{
			direction = (int)Math.round((Math.random()*1)); // changes the direction to directions 0 and 1
		}
		if (posY <= 0) // if y position is less than or equal to 0
		{
			int randNum [] = new int [] {0,2}; // an array of 2 random directions
			direction = randNum[(int)Math.round((Math.random()))]; // sets the direction to the set of 2 random directions
		}
		if (posY >= GUI.appletHeight -50) // if the y position is greater than applet height
		{
			int randNum2 [] = new int [] {1,3}; // array of 2 random directions
			direction = randNum2[(int)Math.round((Math.random()))]; // sets direction to the set of 2 random directions
		}
	}

	public int getDx () // gets the distance x
	{
		return dx; //returns the distance x
	}

	public int getDy() // gets the distance y
	{
		return dy; //returns the distance y
	}

	public int getInitialPosX() // gets the initial x position 
	{
		return initialPosX; // returns the initial x position
	}

	public int getInitialPosY() // gets the initial y position
	{
		return initialPosY; // returns the initial y position 
	}

	public int getPosX() // gets the x position
	{
		return posX; // returns the x position 
	}

	public int getPosY() // gets the y position
	{
		return posY; // returns the y position
	}

	public int getSize() //gets the size 
	{
		return size; //returns the size
	}

}