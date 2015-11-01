import java.awt.Color;


public class Cell {
	private Color cellCol; // The color of the cell
	private int size=10; // the width/height of the cell
	private int xPos; // the x position of the cell
	private int yPos;  // the y position of the cell
	private int distanceFromUser; //the distance between the cell and the user
	private int distanceX; // the distance between the cell and the user in the x component
	private int distanceY; // the distance between the cell and the user in the y component
	private int distanceFromEnemy; //the distance between the cell and an enemy
	public Cell() {
		// TODO Auto-generated constructor stub
		int rCol= (int)Math.round((Math.random()*255)); //generates a random rgb color
		int gCol= (int)Math.round((Math.random()*255));
		int bCol= (int)Math.round((Math.random()*255));
		xPos= (int)Math.round((Math.random()*GUI.appletWidth)); //generates a random x and y position
		yPos= (int)Math.round((Math.random()*GUI.appletHeight-10));
		cellCol = new Color (rCol,gCol,bCol);
		GUI.numCell++; //increses the tally of number of cells

	}

	public int getDistanceFromUser() // returns how far the cell is from the user
	{
		return distanceFromUser;	
	}
	public void updateDistanceFromUser() // recalculates distance from user
	{
		distanceX = (Math.abs(GUI.user.getInitPosX() + GUI.user.getPlaySize()/2 - GUI.user.getdx() - this.getCenterX()- this.getSize()/2));
		distanceY =(Math.abs(GUI.user.getInitPosY() + GUI.user.getPlaySize()/2 - GUI.user.getdy() - this.getCenterY() - this.getSize()/2));
		distanceFromUser = (int) Math.sqrt(Math.pow(distanceX,2) + Math.pow(distanceY,2));
	}
	public boolean checkCollisionEnemy() //checks for collision with enemy
	{
		for (int i = 0 ; i < GUI.enemyList.size() ; i++) //runs through list of enemies
		{
			int distanceFromEnemyX = (Math.abs(GUI.enemyList.get(i).getPosX() + GUI.enemyList.get(i).getSize()/2 - this.getCenterX() - this.getSize()/2));
			int distanceFromEnemyY = (Math.abs(GUI.enemyList.get(i).getPosY() + GUI.enemyList.get(i).getSize()/2 - this.getCenterY() - this.getSize()/2)); 
			distanceFromEnemy = (int) Math.sqrt(Math.pow(distanceFromEnemyX,2) + Math.pow(distanceFromEnemyY,2));
			if (distanceFromEnemy < GUI.enemyList.get(i).getSize()/2) //detects collision
			{
				return true;
			}
		}
		return false;
	}
	public boolean checkCollision() // checks for collision with user
	{
		
		if (distanceFromUser<GUI.user.getSize()) // user collision
		{
			if (GUI.user.getSize() < 40) //when size < 40
			{
				if (GUI.user.getIncreaseReq() == 0) // user grows when requirement is reached
				{
					GUI.user.changeSize(1);
					GUI.user.changeIncreaseReq(2); // requires 2 cells to increase size by 1
				}
				GUI.user.changeIncreaseReq(-1);

			}
			else if (GUI.user.getSize() < 100)
			{
				if (GUI.user.getIncreaseReq() == 0)
				{
					GUI.user.changeSize(1);
					GUI.user.changeIncreaseReq(3);
				}

				GUI.user.changeIncreaseReq(-1);
			}
			else if (GUI.user.getSize() < 150)
			{
				if (GUI.user.getIncreaseReq() == 0)
				{
					GUI.user.changeSize(1);
					GUI.user.changeIncreaseReq(5);
				}

				GUI.user.changeIncreaseReq(-1);
			}
			else if (GUI.user.getSize() < 200)
			{
				if (GUI.user.getIncreaseReq() == 0)
				{
					GUI.user.changeSize(1);
					GUI.user.changeIncreaseReq(7);
				}

				GUI.user.changeIncreaseReq(-1);
			}

			return true;
		}
		
		else
			return false;
	}

	public Color getColor() // returns the color of the cell
	{

		return cellCol;

	}

	public int getXPos() //returns the x position of the cell
	{
		return xPos;	
	}

	public int getYPos() //returns the y position of the cell
	{
		return yPos;

	}
	public int getSize() //returns the size of the cell
	{
		return size; 
	}
	public int getCenterX() //returns the x position of the center of the cell
	{
		return (xPos + size/2);
	}
	public int getCenterY() //returns the x position of the center of the cell
	{
		return (yPos + size/2);
	}
	/**
	 * @param args
	 */


}



