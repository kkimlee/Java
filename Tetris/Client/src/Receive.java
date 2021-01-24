import java.io.*;
import java.net.*;


public class Receive extends Thread{
	private Socket client;
	private Tetris tetris;
	String[] Code; //서버가 보낸 정보
	
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
				Code = inputMessage.split(" "); // 서버가 보낸 정보를 공백으로 쪼갬
				tetris.server_ready = Boolean.valueOf (Code[0]); // 정보의 첫번째값은 서버의 게임준비상태
				tetris.server_score = Integer.parseInt(Code[1]); // 정보의 두번째값은 서버의 점수
				tetris.server_width = Integer.parseInt(Code[2]); // 정보의 세번째 값은 서버보드의 넓이
				tetris.server_height = Integer.parseInt(Code[3]); // 정보의 네번째 값은 서버보드의 높이
				tetris.server_block_num = Integer.parseInt(Code[4]); // 서버의 벽돌 개수
				tetris.server_next_blocktype = Integer.parseInt(Code[5]);
				
				tetris.server_block_pos = new int[tetris.server_block_num]; 
				for(int i = 0; i<tetris.server_block_num;i++) { // 서버의 벽돌 좌표
					tetris.server_block_pos[i]=Integer.parseInt(Code[6+i]);
				}
				System.out.print("서버 상태: " + tetris.server_ready + " 서버 점수: " + tetris.server_score + " 서버 보드의 넓이: " + tetris.server_width + " 서버 보드의 높이: " + tetris.server_height + " 서버 블럭 개수 : " + tetris.server_block_num + " 서버의 다음블럭타입: " + tetris.server_next_blocktype + " 서버 벽돌 좌표 :");
				for(int i = 0; i<tetris.server_block_num; i++)
					System.out.print(" " + tetris.server_block_pos[i]);
				System.out.println();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
