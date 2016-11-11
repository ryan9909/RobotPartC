package control;

import robot.Robot;

// Robot Assignment for Programming 1 s2 2016
// Adapted by Caspar from original Robot code in RobotImpl.jar written by Dr Charles Thevathayan
public class RobotControl implements Control {
	private Robot robot;

	//duplicated code. It has been defined in control.java
//	private static final int MAX_HEIGHT = 13;
//	private static final int MAX_COL = 10;        // it should not be fixed number because bar array is not fixed.
	private static final int BAR_START_COL = 3;  // Usually, it should be moved to control.java and changed to public

	//array to stock all the obstacles(*)
//	private int[] barHeights;         //duplicated code.
	private int barHeights[] = new int[] { 2, 3, 5, 7, 1, 3 };

	//array to stock all the objects that should be moved(ascii)
//	private int[] blockHeights;       //duplicated code.
	private int blockHeights[] = new int[] { 3, 1, 2, 3, 1, 1, 1};

	//array to stock all the objects put int the slot1 and slot2
	private int[] smallBlockStackHeight=new int[] {0,0};
	/** MAX_HEIGHT **/
	private int arm1Height = 2;
	private int arm2Width = 1;
	private int arm3Depth = 0;

	// int[] blockHeights = inputBlockHeight();
	// called by RobotImpl
	// the unused arrays are based on cmd line params to RobotImpl not used in
	// this assignment
	@Override
	public void control(Robot robot, int barHeightsUnused[], int blockHeightsUnused[]) {

		// save robot so we can access it from other methods
		this.robot = robot;

		/* ***** unused code to debug
		// int[] barHeights = inputBarHeight();
		// int[] blockHeights = inputBlockHeight();

		// initialise the robot
		// int[] barHeights = {1,2,3,4,5};
		// int[] blockHeights = {1,2,3};
		******* */

		// it seems the param2 and param3 should not be used so I add a condition to jump it when either is null.
		if (barHeightsUnused != null && blockHeightsUnused != null) {
			this.barHeights = barHeightsUnused;
			this.blockHeights = blockHeightsUnused;
		}

		robot.init(barHeights, blockHeights, arm1Height, arm2Width, arm3Depth);

		//A better programming habits is that naming as clearly... and declare it on top.
		int colNumCounterofDesBarBlock3 = BAR_START_COL;
		int indexOfbarHeightsArrayForDesBarBlock3 = 0;
		//count number of Arm1col & block1col & block2col and all bar cols, and target block col
		int intMaxCol = BAR_START_COL + barHeights.length + 1;
		for (int blockIndexOfBlockHeightArray = blockHeights.length - 1; blockIndexOfBlockHeightArray >= 0; blockIndexOfBlockHeightArray--) {
			moveArmUpTo(MAX_HEIGHT);

			extandArm2RightTo(intMaxCol);

			//Attention: the param is depth(index), not height(length),
			//MAX_HEIGHT is height(length) and should be -1 to turn to depth(index) when u want to lower arm3
			lowerArm3DownTo(MAX_HEIGHT - 1 - calculateStackHeight(blockIndexOfBlockHeightArray));

			//modify arm statement to set catched-sth. is true so that the block can be moved together with the arm
			robot.pick();

			raiseArm3UpTo(0);

			if (blockHeights[blockIndexOfBlockHeightArray] == 3) {
				contractArm2LeftTo(colNumCounterofDesBarBlock3++);
				lowerArm3DownTo(MAX_HEIGHT - 1 - barHeights[indexOfbarHeightsArrayForDesBarBlock3++] - blockHeights[indexOfbarHeightsArrayForDesBarBlock3] - 1);
			} else{
				contractArm2LeftTo(blockHeights[blockIndexOfBlockHeightArray]);
				int heightIndex=blockHeights[blockIndexOfBlockHeightArray] -1;
				lowerArm3DownTo(MAX_HEIGHT - 1 - smallBlockStackHeight[heightIndex]-blockHeights[blockIndexOfBlockHeightArray]);
				smallBlockStackHeight[heightIndex]+=blockHeights[blockIndexOfBlockHeightArray];
			}

			//modify arm statement to set catched-sth. is false so that nothing should be moved together with the arm
			robot.drop();

			raiseArm3UpTo(0);
		}
	}

	// creating methods for each movement i need to perform
	private void For(Class<Integer> class1) {
		// TODO Auto-generated method stub

	}

	private void moveArmUpTo(int height) {
		while (this.arm1Height < height) {
			robot.up();
			this.arm1Height++;
		}
	}

	private void extandArm2RightTo(int col) {
		while (this.arm2Width < col) {
			robot.extend();
			this.arm2Width++;
		}
	}

	private void contractArm2LeftTo(int col) {
		while (this.arm2Width > col) {
			robot.contract();
			this.arm2Width--;
		}
	}

	private void lowerArm3DownTo(int depth) {
		while (this.arm3Depth < depth) {
			robot.lower();
			this.arm3Depth++;
		}
	}

	private void raiseArm3UpTo(int depth) {
		while (this.arm3Depth > depth) {
			robot.raise();
			this.arm3Depth--;
		}
	}

	private int calculateStackHeight(int blockindex) {
		int total = 0;
		for (int i = 0; i <= blockindex; i++)
			total += blockHeights[i];
		return total;
	}
}