

public class Game {
	public static void main(String[] agrs) {
		Tetris tetris = new Tetris();
		DrawGame draw = new DrawGame(tetris);
		Thread drawing = new Thread(draw);
		DownBlock down = new DownBlock(tetris);
		Clear clear = new Clear(tetris);
		BlockFactory factory = new BlockFactory(tetris);
		
		drawing.start();
		factory.start();
		down.start();
		clear.start();
		
	}	
	
}

