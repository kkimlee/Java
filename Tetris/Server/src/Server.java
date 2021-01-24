import java.net.*;
import java.io.*;

public class Server {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(9999);
			System.out.println("클라이언트를 기다리는중");
			Socket socket = server.accept();
			System.out.println("연결");
			
			Tetris tetris = new Tetris();
			Block block = new Block();
			Game game = new Game(tetris, block);
			Receive rec = new Receive(socket);
			rec.SetGame(tetris);
			Send sen = new Send(socket);
			sen.SetGame(tetris, block);
			
			
			rec.start();
			sen.start();
			game.start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
