import java.io.*;
import java.net.*;
import java.util.*;

public class Send extends Thread{
	private Socket client;
	private Tetris tetris;
	private Block block;
	
	public Send(Socket socket) {
		this.client = socket;
	}
	
	public void SetGame(Tetris tetris, Block block) {
		this.tetris = tetris;
		this.block = block;
	}
	
	public void run() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			String outMessage;
			boolean ready; // ���� �غ����
			int score; // ������ ����
			int size;	// ������ ��������		
			int[] pos; // ���� ��ǥ
			int[] board; // ��ǥ�� String���� �ֱ�
			int width;
			int height;
			int blocktype;
			
			while(true) {
				int j = 0;
				size = 0;
				
				for(int i = 0; i<tetris.width*tetris.height; i++) { // ���� ���� �ľ�
					if(tetris.board[i/tetris.width][i%tetris.width]==1)
						size++;
				}
				pos = new int[size]; // ���� ���� ��ŭ�� ũ�⸦ ���� ��ǥ�迭 ����
				
				for(int i = 0; i<tetris.width*tetris.height; i++) { // ��ǥ�迭�� ������ǥ ����
					if(tetris.board[i/tetris.width][i%tetris.width]==1) {
						pos[j] = i;
						j++;
					}
				}
				
				board = new int[size];
				for(int i = 0; i<size; i++)
					board[i]=pos[i];
				score = tetris.score; 
				width = tetris.width;
				height = tetris.height;
				blocktype = block.type;

				System.out.println("Ŭ���̾�Ʈ ����: "+score+" Ŭ���̾�Ʈ ���� ���� : " + width + " Ŭ���̾�Ʈ ���� ����: " + height + " Ŭ���̾�Ʈ ��� ����: "+size + " ���� ��: " + blocktype);
				outMessage = score + " " + width + " " + height + " " + size + " " + blocktype; // �غ���� ���� ������
				for(int i = 0; i<size;i++) // ��ǥ ������
					outMessage = outMessage + " " +board[i];
				out.write(outMessage + "\n");
				out.flush();
				
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
				}
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 	
	}

}
