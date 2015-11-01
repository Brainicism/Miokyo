/*
 * 			        	Miokyo
 * 			By: Brian Le and Raza Kazmi
 *               Date: June 21st, 2015
 * 					Class: ICS-4U0
 * 				  Instructor: A. Sayed
 * Details:
 * 		Miokyo is based off the popular minimalist flash game, agar.io. The objective of Miokyo is to 
 * 		absorb the randomly generated cells in order to grow in size. Once your size reaches 200, you win
 *      the game. Enemies will spawn at a regular time interval, moving in random directions, at speeds faster
 *      than the user. The user has to actively avoid the enemies, in order to reach the goal of 200 size.
 * 		If the enemy is touched, the user's size is halved, and the enemy is destroyed.
 * 
 * Known Bugs:
 * 		- If the user collides with an enemy too many times, their size will be too small to absorb more cells
 * 		- Occasionally, console will display an indexOutOfBounds exception, but will not affect gameplay
 */

//imports from API
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JLabel;


@SuppressWarnings("serial")
public class GUI extends Applet
implements MouseListener, MouseWheelListener, MouseMotionListener, Runnable {

	static int mouseDistance; //distance from mouse to center of player
	boolean gameScreen = true; // sets the game screen to true
	boolean gameWinScreen = false; // sets the winning screen to false
	static int mouseX; //x position of mouse
	static int mouseY; //y position of mouse

	ArrayList <Cell> cellList = new ArrayList <Cell> (0); // a list of all the cells 
	static ArrayList <Enemy> enemyList = new ArrayList <Enemy> (0); // a list of enemies
	static int numCell = 0; // number of cells
	double enemySpawnTimer =0.0; // the enemies spawn timer
	static Player user = new Player(500,300,0,0,20,0,0,0); // instantiating player object
	private Image img;
	private Image dbImage;
	private Graphics dbg;
	static int appletHeight =950; // sets the applet height
	static int appletWidth =1920; // sets the applet width
	double gameTimer =60; // How long the game has been played for
	public void init ()
	{
		addMouseListener(this); // adds mouse listener
		resize(appletWidth,appletHeight); //resizes the applet

		Frame c = (Frame)this.getParent().getParent();
		c.setTitle("Miokyo");  // sets title of applet
		img = Toolkit.getDefaultToolkit().createImage("grid.png");
		img = img.getScaledInstance(4050,1250 , Image.SCALE_DEFAULT);


		add(new JLabel("Look ma!  No Menu!")); //adds jlabel
		Frame[] frames = Frame.getFrames();
		for (Frame frame : frames) {
			frame.setMenuBar(null);
			frame.pack();
		}
	} 


	public void start ()
	{

		addMouseListener(this);// adds mouse listener
		this.addMouseMotionListener(this);

		Thread th = new Thread (this);
		th.start ();

	}


	public void run () throws IndexOutOfBoundsException

	{

		Thread.currentThread ().setPriority (Thread.MIN_PRIORITY);

		createEnemy(); // creates the enemies


		while (true)
		{
			if (gameScreen == true) // if game is on going
			{
				getAppletContext().showStatus("");
				gameTimer +=0.02; // increases game timer

				if (cellList.size() <350) //sets the limit of cells to 350 at a given time
				{
					createCell(); //creates the cells
				}

				for (int i = 0; i < cellList.size(); i++) // for loop going through all cells
				{
					cellList.get(i).updateDistanceFromUser(); //checks the distance the cells are from the user
					if (cellList.get(i).checkCollision() == true || cellList.get(i).checkCollisionEnemy() == true) // collision detection based on distance from user
					{
						cellList.remove(i); //removes the cell when it interacts with user
					}


				}
				for (int i = 0; i < enemyList.size(); i++) // for loop going through enemies
				{
					enemyList.get(i).updateDistanceFromUser(); // checks the distance the enemies are from the user
					enemyList.get(i).updateMovement(); // updates the enemies movements
					if (enemyList.get(i).checkCollision() == true) // checks collision detection for the enemies
					{
						enemyList.remove(i); //removes the enemy that hit the user
						user.changeSize(-user.getSize()/2); // reduces the users size when hit by enemy


					}


				}
				repaint(); //repaints
				enemySpawnTimer +=0.02; // Counts the enemy spawn timer
				if (enemySpawnTimer >= 12) //when the enemy timer hit 12 seconds
				{
					createEnemy(); //creates a new enemy
					enemySpawnTimer = 0; // resets the spawn timer back to 0
				}

				delay (20); //delays
				user.updateChangePos(); // updates the users change in position
				user.updatePos(); // updates the users current position
				if (user.getSize() == 200) // if user hits a size of 200
				{
					gameScreen = false; // sets the game screen to false
					gameWinScreen = true; // sets the winning screen to true
					repaint(); //repaints
				}



				mouseDistance = (int) Math.sqrt((Math.abs(mouseX - (user.getInitPosX() + user.getPlaySize()/2 - user.getdx()))) + (Math.abs(mouseY-(user.getInitPosY() + user.getPlaySize()/2 - user.getdy())))); // the corelation between the mouse distance and the user position
			}
		}

	}

	public void update (Graphics g)
	{

		// initialize buffer
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize ().width, this.getSize ().height); //creates image
			dbg = dbImage.getGraphics ();
		}

		// clear screen in background
		dbg.setColor (getBackground ()); // sets the background color
		dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height); //draws rectangle

		// draw elements in background
		dbg.setColor (getForeground ());
		paint (dbg);

		// draw image on the screen
		g.drawImage (dbImage, 0, 0, this);

	}
	public static void delay (int sec) //delay method
	{
		try {
			Thread.sleep(sec);               
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	public void paint(Graphics g) {
		if (gameScreen == true) // if gamescreen is true
		{
			g.drawImage(img, 0, 0, null); // draws background image
			Font font = new Font("Calibri", Font.BOLD, 24); // creates font

			g.setFont(font); //sets font

			




			for (int i = 0 ; i < cellList.size() ; i++) // for loop going through cell list
			{
				if (cellList.get(i)!=null) // if cells alive and not absorbed by user
				{
					g.setColor(cellList.get(i).getColor()); // gets the color for cells
					g.fillOval(cellList.get(i).getXPos(),cellList.get(i).getYPos(), cellList.get(i).getSize(), cellList.get(i).getSize()); // draws the cells in
				}
			}
			for (int i = 0 ; i < enemyList.size() ; i++) // for loop going though the enemy list
			{
				if (enemyList.get(i)!=null) // if enemies are alive
				{
					g.setColor(enemyList.get(i).getColor()); // sets the enemy color
					g.fillOval(enemyList.get(i).getPosX(),enemyList.get(i).getPosY(), enemyList.get(i).getSize(), enemyList.get(i).getSize()); //draws the enemies in
					g.setColor(Color.black); // sets color to black

					g.drawOval(enemyList.get(i).getPosX(),enemyList.get(i).getPosY(), enemyList.get(i).getSize()+1, enemyList.get(i).getSize()+1); //draws a black outline on enemies

				}
			}
			g.setColor(Color.orange); //sets color to orange
			g.fillOval(user.getPlayPosX(), user.getPlayPosY(), user.getPlaySize() +user.getPlaySize(),user.getPlaySize() +user.getPlaySize()); // draws the user in
			g.setColor(Color.black); //sets color to black
			g.drawOval(user.getPlayPosX(), user.getPlayPosY(), user.getPlaySize() +user.getPlaySize()+1,user.getPlaySize() +user.getPlaySize()+1); //draws black outline around user
			g.drawString("Score: " + user.getSize(), 825, 65); // draws the score 

			g.setColor(Color.black); // sets color to black
			g.drawRect(20, 50, 500, 20); // draws the enemy spawn loader box
			g.setColor(Color.red); //sets the color to red

			g.fillRect(20, 50, 500-(11- (int)enemySpawnTimer)*41, 20); // fills the enemy spawn timer as it gets closer to spawning
			
			
		}
		if (gameWinScreen == true) // if game is won
		{
			g.drawString("You won in "  + Math.round(gameTimer)/60 + " minute(s) and " + Math.round(gameTimer)%60 + " seconds" , 735, 505); //draws a message saying how long it took to beat game

		}



	}

	// Methods imported from API to get mouse detection

	// detects if mouse is entered
	public void mouseEntered(MouseEvent event) {
	}
	//detects if mouse is exited
	public void mouseExited(MouseEvent event) {
	}
	//detects if the mouse is pressed
	public void mousePressed(MouseEvent event) {

		//System.out.println(mouseX + "," + mouseY);
	}
	//detects if the mouse was released
	public void mouseReleased(MouseEvent event) {
	}
	//detects if the mouse was clicked
	public void mouseClicked(MouseEvent event) {
	}
	// detects if the mouse was dragged
	@Override
	public void mouseDragged(MouseEvent event) {

	}
	// detects if the mouse was moved
	public void mouseMoved(MouseEvent event) {
		mouseX = (int) event.getPoint().getX(); //gets the x position of the mouse
		mouseY = (int) event.getPoint().getY(); //gets the y positon of the mouse

	}
	// detects if the mouse wheel was moved
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {

	}
	public void createCell() // method to create cell
	{
		Cell newCell = new Cell (); // instantiates new cell object
		cellList.add(newCell); // adds the cell object to the array list
	}
	public void createEnemy() // method to create enemies
	{
		Enemy newEnemy = new Enemy(0,0,0,0,100,0,0,7); //instantiates new enemy object
		enemyList.add(newEnemy); // adds the enemy object to the array list

	}
	public void deleteCell() throws IndexOutOfBoundsException // method to delete cells
	{
		try {
			cellList.remove(0); //removes the cells

		} catch (IndexOutOfBoundsException e) {
			System.err.println("IndexOutOfBoundsException: " + e.getMessage());
		} 


	}


}


