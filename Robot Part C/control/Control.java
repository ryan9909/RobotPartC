package control;

import robot.Robot;

// Control interface by Caspar (used internally by Robot)
// do not modify this code!
public interface Control
{
	// you can use these constants in your code
	public static final int MAX_HEIGHT = 13;
	public static final int MAX_BARS = 7;
	public static final int MAX_BAR_HEIGHT = 7;
	public static final int MAX_BLOCKS = 12;
	public static final int MAX_BLOCK_HEIGHT = 3;
	
	public void control(Robot robot, int barHeights[], int blockHeights[]);
}
