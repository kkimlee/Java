import java.io.*;
import java.net.*;
import java.util.*;

public class Send extends Thread{
	private Socket server;
	private Tetris tetris;
	private Block block;
	
	public Send(Socket socket) {
		this.server = socket;
	}
	
	public void SetGame(Tetris tetris, Block block) {
		this.tetris = tetris;
		this.block = block;
	}
	
	public void run() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			
			String outMessage;
			boolean ready; // 게임 준비상태
			int score; // 서버의 점수
			int size;	// 서버의 벽돌개수		
			int[] pos; // 별돌 좌표
			int [] board; // 좌표값
			int width; // 테트리스 보드의 넓이
			int height; // 테트리스 보드의 높이
			int blocktype;
			
			while(true) {
				int j = 0;
				size = 0;
				
				for(int i = 0; i<tetris.width*tetris.height; i++) { // 벽돌 개수 파악
					if(tetris.board[i/tetris.width][i%tetris.width]==1)
						size++;
				}
				pos = new int[size]; // 벽돌 개수 만큼의 크기를 갖는 좌표배열 생성
				
				for(int i = 0; i<tetris.width*tetris.height; i++) { // 좌표배열에 벽돌좌표 저장
					if(tetris.board[i/tetris.width][i%tetris.width]==1) {
						pos[j] = i;
						j++;
					}
				}
				
				board = new int[size];
				for(int i = 0; i<size; i++)
					board[i]=pos[i];
				ready = tetris.ready; 
				score = tetris.score; 
				width = tetris.width;
				height = tetris.height;
				blocktype = block.type;
				

				System.out.println("서버 상태 : "+ready+" 서버 점수: "+score+" 서버 보드 넓이 : " + width + " 서버 보드 높이: " + height + " 서버 블록 개수: "+ size +" 서버 다음 블럭타입: " + blocktype);
				outMessage = ready + " " + score + " " + width + " " + height + " " +size + " " + blocktype; // 준비상태 점수 넓이 높이 블럭개수 다음블럭타입
				for(int i = 0; i<size;i++) // 좌표 보내기
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
