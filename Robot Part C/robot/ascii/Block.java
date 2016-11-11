package robot.ascii;

import com.googlecode.lanterna.terminal.Terminal;

public class Block implements Drawable
{
	private int currblockSize;
	private int stackHeight;
	private int col;
	private int maxRow;

	public Block(int currblockSize, int stackHeight, int col, int maxRow)
	{
		this.currblockSize = currblockSize;
		this.stackHeight = stackHeight;
		this.col = col;
		this.maxRow = maxRow;
	}

	@Override
	public void draw(Terminal terminal)
	{
//		int maxRow = terminal.getTerminalSize().getRows()-1;
//		int maxCol = terminal.getTerminalSize().getColumns()-1;

//		System.out.println(maxRow);
//		System.out.println(maxCol);

			for (int i = 0; i < currblockSize; i++){

				//System.out.println(blockHeight);

				terminal.moveCursor(col, maxRow - stackHeight - i);
				if (currblockSize ==1){
					terminal.putCharacter('1');
				} else if (currblockSize == 2){
					terminal.putCharacter('2');
				}else if (currblockSize == 3){
					terminal.putCharacter('3');
				}
			}
	}

	public int getCurrblockSize() {
		return currblockSize;
	}

	public void setCurrblockSize(int currblockSize) {
		this.currblockSize = currblockSize;
	}

	public int getStackHeight() {
		return stackHeight;
	}

	public void setStackHeight(int stackHeight) {
		this.stackHeight = stackHeight;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public void redraw(Terminal terminal,int left, int right, int up, int down)
	{
			if (up == 1) {
				terminal.moveCursor(col, maxRow - stackHeight + currblockSize - 1);
				terminal.putCharacter(' ');
				stackHeight = stackHeight + up;
				terminal.moveCursor(col, maxRow - stackHeight);
				terminal.putCharacter(String.valueOf(currblockSize).charAt(0));
			} else if (down == 1) {
				terminal.moveCursor(col, maxRow - stackHeight);
				terminal.putCharacter(' ');
				stackHeight = stackHeight - down;
				terminal.moveCursor(col, maxRow - stackHeight + currblockSize - 1);
				terminal.putCharacter(String.valueOf(currblockSize).charAt(0));
			} else if (left == 1) {
				col = col - left;
				for (int i = 0; i < currblockSize; i++){
					terminal.moveCursor(col + 1, maxRow - stackHeight + i);
					terminal.putCharacter(' ');
					terminal.moveCursor(col, maxRow - stackHeight + i);
					terminal.putCharacter(String.valueOf(currblockSize).charAt(0));
				}
			} else if (right == 1) {
				col = col + right;
				for (int i = 0; i < currblockSize; i++){
					terminal.moveCursor(col - 1, maxRow - stackHeight + i);
					terminal.putCharacter(' ');
					terminal.moveCursor(col, maxRow - stackHeight + i);
					terminal.putCharacter(String.valueOf(currblockSize).charAt(0));
				}
			}
	}
}