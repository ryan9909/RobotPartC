package robot.ascii;

import java.util.Stack;

//import java.util.ArrayList;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import control.Control;
import control.RobotControl;
import robot.Robot;

//Robot Assignment for Programming 1 s2 2016
//ASCIIBot classes written by Caspar Ryan
public class ASCIIBot implements Robot
{
	/** the Lanterna terminal */
	private Terminal terminal;
	private Arm arm;
	private int barHeights[] = null;
	private int blockHeights[] = null;
	/** store the blocks that had been moved from the target column */
	private Stack<Block> stackBlockRemoved = new Stack<Block>();

//	private int targetHeight = 13;
//	private int targetWidth = 10;
//	private int targetDepth = 0;


	// for simplicity I do not do graceful exiting code so just use the eclipse STOP button to exit
	public static void main(String[] args)
	{
		// instantiate ASCIIBot and run control()
		new RobotControl().control(new ASCIIBot(),null, null);
	}

	// the constructor initialises the Lanterna Terminal
	public ASCIIBot()
	{
		// create the terminal 20 rows, 15 columns (not exactly, depend on the defination in the control.java)
		terminal = TerminalFacade.createSwingTerminal(
				15, Control.MAX_HEIGHT);

		// required by Lanterna framework to initialise
		terminal.enterPrivateMode();
		terminal.setCursorVisible(false);

		terminal.clearScreen();
	}

	@Override
	public void init(int[] barHeights, int[] blockHeights, int height,
			int width, int depth)
	{
		this.barHeights = barHeights;
		this.blockHeights = blockHeights;
		// simple code example to help you get started!
		//new Bar().draw(terminal);

		// delay 100 milliseconds for next "frame"
		delayAnimation(100);

		// do real code here ..

		// init pane at first
		int maxRow = terminal.getTerminalSize().getRows()-1;
		int maxCol = 3 + barHeights.length + 1;

		for(int i = 0; i < barHeights.length; i++)
		{
				new Bar(i + 3,barHeights[i]).draw(terminal);
		}

		int stackHeightCounter = 0;
		for (int i = 0; i < blockHeights.length; i++){
			new Block (blockHeights[i],stackHeightCounter, maxCol, maxRow).draw(terminal);
			stackHeightCounter = stackHeightCounter + blockHeights[i];
		}

		arm = new Arm(width,height,depth);
		arm.draw(terminal);
	}


	@Override
	public void pick()
	{
		int heightCounter = 0;
		int maxRow = Control.MAX_HEIGHT;
		int col = 3 + barHeights.length + 1;;
		int block = this.blockHeights[this.blockHeights.length-1-stackBlockRemoved.size()];

		//calculate current target block's position in the block stack
		for (int i = 0; i < blockHeights.length - stackBlockRemoved.size(); i++){
			heightCounter = heightCounter+ blockHeights[i];
		}

		stackBlockRemoved.push(new Block (block, heightCounter, col, maxRow));
		arm.pick(stackBlockRemoved.peek());
	}

	@Override
	public void drop()
	{
		stackBlockRemoved.pop();
		stackBlockRemoved.push(arm.getCurBlock());
		arm.drop();
	}

	@Override
	public void up()
	{
			arm.up(terminal);
			delayAnimation(100);
	}


	@Override
	public void down()
	{
		arm.down(terminal);
		delayAnimation(100);
	}

	@Override
	public void contract()
	{
		arm.contract(terminal);
		delayAnimation(100);
	}

	@Override
	public void extend()
	{
		arm.extend(terminal);
		delayAnimation(100);
	}

	@Override
	public void lower()
	{
		arm.lower(terminal);
		delayAnimation(100);
	}

	@Override
	public void raise()
	{
		arm.raise(terminal);
		delayAnimation(100);
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
}
