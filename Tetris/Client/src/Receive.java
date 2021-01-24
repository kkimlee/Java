import java.io.*;
import java.net.*;


public class Receive extends Thread{
	private Socket client;
	private Tetris tetris;
	String[] Code; //������ ���� ����
	
	public Receive(Socket socket) {
		this.client = socket;
	}
	
	public void SetGame(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
			String inputMessage;
			
			
			while(true) {
				inputMessage = in.readLine();
				Code = inputMessage.split(" "); // ������ ���� ������ �������� �ɰ�
				tetris.server_ready = Boolean.valueOf (Code[0]); // ������ ù��°���� ������ �����غ����
				tetris.server_score = Integer.parseInt(Code[1]); // ������ �ι�°���� ������ ����
				tetris.server_width = Integer.parseInt(Code[2]); // ������ ����° ���� ���������� ����
				tetris.server_height = Integer.parseInt(Code[3]); // ������ �׹�° ���� ���������� ����
				tetris.server_block_num = Integer.parseInt(Code[4]); // ������ ���� ����
				tetris.server_next_blocktype = Integer.parseInt(Code[5]);
				
				tetris.server_block_pos = new int[tetris.server_block_num]; 
				for(int i = 0; i<tetris.server_block_num;i++) { // ������ ���� ��ǥ
					tetris.server_block_pos[i]=Integer.parseInt(Code[6+i]);
				}
				System.out.print("���� ����: " + tetris.server_ready + " ���� ����: " + tetris.server_score + " ���� ������ ����: " + tetris.server_width + " ���� ������ ����: " + tetris.server_height + " ���� �� ���� : " + tetris.server_block_num + " ������ ������Ÿ��: " + tetris.server_next_blocktype + " ���� ���� ��ǥ :");
				for(int i = 0; i<tetris.server_block_num; i++)
					System.out.print(" " + tetris.server_block_pos[i]);
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
