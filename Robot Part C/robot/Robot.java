package robot;

//Robot interface by Caspar (used internally by Robot)
//do not modify this code!
public interface Robot
{
	/**
	 * @param barHeights
	 *            specifies the height of the bars from left to right (the array
	 *            is copied internally)
	 * @param blockHeights
	 *            specifies the height of each block which will be stacked in
	 *            the far right column
	 * @param height
	 *            starting height of arm1
	 * @param width
	 *            starting width of arm2
	 * @param depth
	 *            starting depth of arm3
	 */
	public abstract void init(int barHeights[], int blockHeights[], int height,
			int width, int depth);

	/**
	 * pick up block from top of block stack (arm 3 must be directly above block)
	 */
	public abstract void pick();

	/**
	 * drop block in column (arm 3 must be directly above drop location) 
	 */
	public abstract void drop();

	/**
	 * move arm 1 up (height)
	 */
	public abstract void up();

	/**
	 * move arm 1 down (height)
	 */
	public abstract void down();

	/**
	 * move arm2 left (width)
	 */
	public abstract void contract();

	/**
	 * move arm2 right (width)
	 */
	public abstract void extend();

	/**
	 * move arm3 down (depth)
	 */
	public abstract void lower();

	/**
	 * move arm3 up (depth)
	 */
	public abstract void raise();

}