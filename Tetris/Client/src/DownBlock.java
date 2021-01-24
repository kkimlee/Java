
public class DownBlock extends Thread{
	private Tetris tetris;
	public DownBlock(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void run() {
		while(true) {
			if(tetris.server_ready) { // 서버의 게임준비 상태에 따라 블럭 멈춤
			for(int i = 0;i<tetris.blockW*tetris.blockH;i++) // 기존 위치의 블럭 지우기
			{	
				if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
					tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] = 0;
			}
			tetris.y=tetris.y+1; // 아래로 한칸이동
			if(tetris.CheckCrush(tetris.x,tetris.y)) // 이동한 방향에 충돌 없으면 블럭 그리기
			{
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
			else // 충동하면 원래위치로 이동
			{
				tetris.y=tetris.y-1;
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
				tetris.check=true;
			}
			}
			tetris.repaint();
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
			}
		}
	}
}
