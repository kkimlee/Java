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
			boolean ready; // 게임 준비상태
			int score; // 서버의 점수
			int size;	// 서버의 벽돌개수		
			int[] pos; // 별돌 좌표
			int[] board; // 좌표값 String으로 넣기
			int width;
			int height;
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
				score = tetris.score; 
				width = tetris.width;
				height = tetris.height;
				blocktype = block.type;

				System.out.println("클라이언트 점수: "+score+" 클라이언트 보드 넓이 : " + width + " 클라이언트 보드 높이: " + height + " 클라이언트 블록 개수: "+size + " 다음 블럭: " + blocktype);
				outMessage = score + " " + width + " " + height + " " + size + " " + blocktype; // 준비상태 점수 블럭개수
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
