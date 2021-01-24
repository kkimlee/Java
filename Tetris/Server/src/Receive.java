import java.io.*;
import java.net.*;


public class Receive extends Thread{
	private Socket server;
	private Tetris tetris;
	String[] Code; //Ŭ���̾�Ʈ�� ���� ����
	
	public Receive(Socket socket) {
		this.server = socket;
	}
	
	public void SetGame(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream())); 
			String inputMessage;
			
			while(true) {
				inputMessage = in.readLine();
				Code = inputMessage.split(" "); // Ŭ���̾�Ʈ�� ���� ������ �������� �ɰ�
				tetris.client_score = Integer.parseInt(Code[0]); // ������ �ι�°���� Ŭ���̾�Ʈ�� ����
				tetris.client_width = Integer.parseInt(Code[1]); // ������ ����° ���� Ŭ���̾�Ʈ������ ����
				tetris.client_height = Integer.parseInt(Code[2]); // ������ �׹�° ���� Ŭ���̾�Ʈ������ ����
				tetris.client_block_num = Integer.parseInt(Code[3]); // Ŭ���̾�Ʈ�� ���� ����
				tetris.client_next_blocktype = Integer.parseInt(Code[4]); // Ŭ���̾�Ʈ�� ���� ��ä�
				
				tetris.client_block_pos = new int[tetris.client_block_num];
				for(int i = 0; i < tetris.client_block_num; i++) {// Ŭ���̾�Ʈ�� ���� ��ǥ
					tetris.client_block_pos[i] = Integer.parseInt(Code[5+i]);
				}
				
				System.out.print("Ŭ���̾�Ʈ ����: " + tetris.client_score + " Ŭ���̾�Ʈ ������� : " + tetris.client_width + " Ŭ���̾�Ʈ ������� : " +tetris.client_height + " Ŭ���̾�Ʈ �� ���� : " + tetris.client_block_num + " Ŭ���̾�Ʈ�� ���� ��Ÿ��: "+ tetris.client_next_blocktype +" Ŭ���̾�Ʈ ���� ��ǥ :");
				for(int i = 0; i<tetris.client_block_num; i++)
					System.out.print(" " + tetris.client_block_pos[i]);
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	

}
