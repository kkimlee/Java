

public class Game extends Thread{
	private Tetris tetris;
	private Block block;
	
	public Game(Tetris tetris, Block block) {
		this.tetris = tetris;
		this.block = block;
	}
	
	public void run() {
		DrawGame draw = new DrawGame(tetris);
		Thread drawing = new Thread(draw);
		DownBlock down = new DownBlock(tetris);
		Clear clear = new Clear(tetris);
		BlockFactory factory = new BlockFactory(tetris, block);
		
		drawing.start();
		factory.start();
		down.start();
		clear.start();
		
		
	}
}
