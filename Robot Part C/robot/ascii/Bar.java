package robot.ascii;

import com.googlecode.lanterna.terminal.Terminal;

public class Bar implements Drawable
{
	private int columnLoc = 0;
	private int barHeight = 0;
	
	public Bar(int columnLoc, int barHeight){
		this.columnLoc = columnLoc;
		this.barHeight = barHeight;
	}
	
	@Override
	public void draw(Terminal terminal){

		int maxRow = terminal.getTerminalSize().getRows()-1;
		
		for (int rowPos = maxRow; rowPos > maxRow - barHeight; rowPos--)
		{
			terminal.moveCursor(columnLoc, rowPos);
			terminal.putCharacter('*');
		}
	}
}
