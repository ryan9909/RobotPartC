package robot.ascii;

import com.googlecode.lanterna.terminal.Terminal;

import control.Control;


public class Arm implements Drawable
{
	/** length of arm1 */
	private int armHeight = 0;
	/** last column number that arm2 extands to */
	private int arm2columnLoc = 0;
	/** distance index of arm3 extands from arm2. 0 means arm3 does not extand at all. */
	private int arm3Depth = 0;

	private Block blockPickedUp = null;

	public Arm(int columnLoc, int armHeight, int armDepth){
		super();
		this.arm2columnLoc = columnLoc;
		this.armHeight = armHeight;
		this.arm3Depth = armDepth;
	}
	@Override
	public void draw(Terminal terminal)
	{
		int maxHeight =Control.MAX_HEIGHT;
//		int maxWide = terminal.getTerminalSize().getColumns()-1;

		for (int x = maxHeight; x >= maxHeight - armHeight; x--){

			terminal.moveCursor(0, x);
			if (x == maxHeight - armHeight) {
				terminal.putCharacter('-');
			} else {
				terminal.putCharacter('|');
			}

			delayAnimation(100);

		}

		for (int y = 1; y <= arm2columnLoc; y++){
			terminal.moveCursor(y, maxHeight - armHeight);
			if (y == arm2columnLoc) {
				terminal.putCharacter('|');
			} else {
				terminal.putCharacter('-');
			}
			delayAnimation(100);
		}

		for (int z = 1; z <= arm3Depth; z++){
			terminal.moveCursor(arm2columnLoc, z);
			terminal.putCharacter('|');
			delayAnimation(100);
		}
	}

	// delay in ms
	private void delayAnimation(int ms)
	{
		try
		{
			Thread.sleep(ms);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isBlockPickedUp() {
		return blockPickedUp != null;
	}

	public void pick(Block block) {
		this.blockPickedUp = block;
	}

	public Block getCurBlock() {
		return this.blockPickedUp;
	}
	public void drop() {
		this.blockPickedUp = null;
	}

	public void up(Terminal terminal)
	{
		int maxHeight = Control.MAX_HEIGHT;
//		int maxWide = terminal.getTerminalSize().getColumns()-1;

		armHeight++;

		if (armHeight > maxHeight) {
			armHeight = maxHeight;
		}
		//arm1(before)
		terminal.moveCursor(0, maxHeight-armHeight+1);
		terminal.putCharacter('|');
		//arm1(after)
		terminal.moveCursor(0, maxHeight-armHeight);
		terminal.putCharacter('-');

		//arm2
		for (int i=1; i<arm2columnLoc-1;i++){
			//before
			terminal.moveCursor(i, maxHeight-armHeight+1);
			terminal.putCharacter(' ');
			//after
			terminal.moveCursor(arm2columnLoc, maxHeight-armHeight);
			terminal.putCharacter('-');

		}
//		terminal.moveCursor(columnLoc, maxHeight-armHeight);
//		terminal.putCharacter('-');

		//arm3(before)
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight+1);
		terminal.putCharacter(' ');
		//arm3(after)
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight);
		terminal.putCharacter('|');

		if (isBlockPickedUp()) {
			blockPickedUp.redraw(terminal, 0, 0, 1, 0);
		}

	}

	public void down(Terminal terminal)
	{
		int maxHeight = Control.MAX_HEIGHT;
//		int maxWide = terminal.getTerminalSize().getColumns();

		if (isBlockPickedUp()) {
			blockPickedUp.redraw(terminal, 0, 0, 0, 1);
		}
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight);

		terminal.putCharacter(' ');
		armHeight--;
		if (armHeight < 1) {
			armHeight = 1;
		}
	}

	public void contract(Terminal terminal)
	{
		int maxHeight = Control.MAX_HEIGHT;
//		int maxWide = terminal.getTerminalSize().getColumns()-1;

		if (isBlockPickedUp()) {
			blockPickedUp.redraw(terminal, 1, 0, 0, 0);
		}
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight);
		terminal.putCharacter(' ');
		arm2columnLoc--;
		if (arm2columnLoc < 0) {
			arm2columnLoc = 0;
		}
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight);
		terminal.putCharacter('|');
	}

	public void extend(Terminal terminal)
	{
		int maxHeight = Control.MAX_HEIGHT;
		int maxWide = terminal.getTerminalSize().getColumns()-1;
		arm2columnLoc++;
		if (arm2columnLoc > maxWide) {
			arm2columnLoc = maxWide;
		}
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight);
		terminal.putCharacter('|');
		terminal.moveCursor(arm2columnLoc-1, maxHeight-armHeight);
		terminal.putCharacter('-');

		if (isBlockPickedUp()) {
			blockPickedUp.redraw(terminal, 0, 1, 0, 0);
		}
	}
	public void raise(Terminal terminal)
	{
		int maxHeight = Control.MAX_HEIGHT;
//		int maxWide = terminal.getTerminalSize().getColumns()-1;
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight + arm3Depth);
		terminal.putCharacter(' ');
		arm3Depth--;
//		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight + arm3Depth);
//		terminal.putCharacter('|');
		if (isBlockPickedUp()) {
			blockPickedUp.redraw(terminal, 0, 0, 1, 0);
		}
	}
	public void lower(Terminal terminal)
	{
		int maxHeight = Control.MAX_HEIGHT;
//		int maxWide = terminal.getTerminalSize().getColumns()-1;

		if (isBlockPickedUp()) {
			blockPickedUp.redraw(terminal, 0, 0, 0, 1);
		}
		arm3Depth++;
		terminal.moveCursor(arm2columnLoc, maxHeight-armHeight + arm3Depth);
		terminal.putCharacter('|');
	}

}

