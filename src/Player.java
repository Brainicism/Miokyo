
public class Player {
	private int initialPosX = 500; //starting x position of player
	private int initialPosY = 300;	//starting y position of player
	private int playerPosX; //top left corner of player's circle
	private int playerPosY;  //top left corner of player's circle
	private int playerSize; //player's initial size
	private int dx; //change in x from initial pos
	private int dy; //change in y from initial pos
	private double speed; //speed of ball
	private int increaseReq =0; //amount of cells needed to increase size
	public Player(int initPosX, int initPosY, int playPosX, int playPosY, int playSize, int changeX, int changeY, double spd) {
		initialPosX = initPosX;
		initialPosY = initPosY;
		playerPosX = playPosX;
		playerPosY= playPosY;
		playerSize = playSize; 
		playerPosX = 0;
		playerPosY = 0;
		dx = changeX;
		dy= changeY;
		speed = spd;

	}
	public int getIncreaseReq () // gets the number of cells needed to increase size
	{
		return increaseReq; //returns increaseReq variable
	}
	public void changeIncreaseReq(int increment) //changes the increase requirement
	{
		increaseReq += increment; // increases the increase requirement by an increment
	}
	public void updatePos() //updates the player position
	{
		playerPosX = initialPosX -playerSize/2 - dx; // gets the player x position
		playerPosY = initialPosY -playerSize/2 - dy; // gets the player y position
	}
	public int getdx() // gets the distance x
	{
		return dx; //returns the distance x
	}
	public int getdy() // gets the distance x
	{
		return dy; //returns the distance x
	}
	
	public int getInitPosX() // gets the initial x position of player
	{
		return initialPosX; //returns the initial  x position 
	}
	
	public int getInitPosY() // gets the initial y position of player
	{
		return initialPosY; // returns the initial y position 
	}
	public int getPlayPosX() //gets the players x position
	{
		return playerPosX; //returns the players x position
	}

	public int getPlayPosY() //gets the players y position
	{
		return playerPosY; // returns the players y position 
	}
	
	public int getPlaySize() //gets the player size
	{
	return playerSize;	//returns the player size
	}
	public int getSize() // gets the player size
	{
		return playerSize; //returns the player size
	}

	public void updateChangePos () //updates the change in position
	{
		
		
			speed = 5; // sets the speed of the player
		

		if (GUI.mouseX > initialPosX + playerSize/2 - dx) //if mouse is right of circle
		{
			dx -=speed;
		
		}
		if (GUI.mouseX <  initialPosX + playerSize/2 - dx) //if mouse is left of circle
		{
			dx +=speed;
		}
		if (GUI.mouseY >  initialPosY + playerSize/2 - dy) //if mouse is below circle
		{
			dy -= speed;
		}
		if (GUI.mouseY < initialPosY + playerSize/2 - dy) //if mouse is above circle
		{
			dy +=speed;
		}
	}
	public void changeSize (int increment) //changes the players size
	{
		playerSize += increment; //increases the player size by an increment
	}

	/**
	 * @param args
	 */
	

}



