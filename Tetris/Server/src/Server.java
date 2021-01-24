import java.net.*;
import java.io.*;

public class Server {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(9999);
			System.out.println("Ŭ���̾�Ʈ�� ��ٸ�����");
			Socket socket = server.accept();
			System.out.println("����");
			
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
