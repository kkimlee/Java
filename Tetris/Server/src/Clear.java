
public class Clear extends Thread // 한줄 채우면 삭제0
{
	private Tetris tetris;
	
	public Clear(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void run() {
		int count = 0; // 줄이 가득 차있는지 카운트
		int clear = 0; // 삭제된 줄이 몇줄인지, 
		while(true) {
			if(tetris.check) {
				for(int i = 0; i < tetris.height; i++)
				{
					for(int j = 1; j < tetris.width-1; j++)
					{
						if(tetris.board[i][j]==1) //i번째 줄에 블록이 있으면 값증가
							count++;
					}
					if(count==tetris.width-2) // 보드의 가로 길이에서 벽을 뺀것
					{
						for(int delete = 1;delete<tetris.width-1;delete++)	
						{	
							tetris.board[i][delete]=0;
						}
						clear++;
						
						for(int findH = i; findH>0;findH--) // 삭제된 줄 위의 블럭을 아래로 이동 시킴
						{
							for(int findW = 1; findW<tetris.width-1;findW++)
								if(tetris.board[findH][findW]==1)
								{
									tetris.board[findH+clear][findW]=1;
									tetris.board[findH][findW]=0;
								}
						}
							
					}
					tetris.score += clear*100;
					clear = 0;
					count=0; // 다음 카운트를 위해 0으로 
				}
				tetris.player_score.setText(Integer.toString(tetris.score));
			}
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
			}
		}
	}
}