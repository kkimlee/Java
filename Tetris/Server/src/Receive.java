import java.io.*;
import java.net.*;


public class Receive extends Thread{
	private Socket server;
	private Tetris tetris;
	String[] Code; //클라이언트가 보낸 정보
	
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
				Code = inputMessage.split(" "); // 클라이언트가 보낸 정보를 공백으로 쪼갬
				tetris.client_score = Integer.parseInt(Code[0]); // 정보의 두번째값은 클라이언트의 점수
				tetris.client_width = Integer.parseInt(Code[1]); // 정보의 세번째 값은 클라이언트보드의 넓이
				tetris.client_height = Integer.parseInt(Code[2]); // 정보의 네번째 값은 클라이언트보드의 높이
				tetris.client_block_num = Integer.parseInt(Code[3]); // 클라이언트의 벽돌 개수
				tetris.client_next_blocktype = Integer.parseInt(Code[4]); // 클라이언트의 다음 블ㅓㄱ
				
				tetris.client_block_pos = new int[tetris.client_block_num];
				for(int i = 0; i < tetris.client_block_num; i++) {// 클라이언트의 벽돌 좌표
					tetris.client_block_pos[i] = Integer.parseInt(Code[5+i]);
				}
				
				System.out.print("클라이언트 점수: " + tetris.client_score + " 클라이언트 보드넓이 : " + tetris.client_width + " 클라이언트 보드높이 : " +tetris.client_height + " 클라이언트 블럭 개수 : " + tetris.client_block_num + " 클라이언트의 다음 블럭타입: "+ tetris.client_next_blocktype +" 클라이언트 벽돌 좌표 :");
				for(int i = 0; i<tetris.client_block_num; i++)
					System.out.print(" " + tetris.client_block_pos[i]);
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	

}
