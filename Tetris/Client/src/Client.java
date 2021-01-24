
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) {
	
		try {
			
			Socket client = new Socket("localhost",9999);
			Tetris tetris = new Tetris();
			Block block = new Block();
			Game game = new Game(tetris, block);
			Receive rec = new Receive(client);
			rec.SetGame(tetris);
			Send sen = new Send(client);
			sen.SetGame(tetris,block);
			
			rec.start();
			sen.start();
			game.start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
	}

}