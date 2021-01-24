
public class Clear extends Thread // ���� ä��� ����0
{
	private Tetris tetris;
	
	public Clear(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void run() {
		int count = 0; // ���� ���� ���ִ��� ī��Ʈ
		int clear = 0; // ������ ���� ��������, 
		while(true) {
			if(tetris.check) {
				for(int i = 0; i < tetris.height; i++)
				{
					for(int j = 1; j < tetris.width-1; j++)
					{
						if(tetris.board[i][j]==1) //i��° �ٿ� ����� ������ ������
							count++;
					}
					if(count==tetris.width-2) // ������ ���� ���̿��� ���� ����
					{
						for(int delete = 1;delete<tetris.width-1;delete++)	
						{	
							tetris.board[i][delete]=0;
						}
						clear++;
						
						for(int findH = i; findH>0;findH--) // ������ �� ���� ���� �Ʒ��� �̵� ��Ŵ
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
					count=0; // ���� ī��Ʈ�� ���� 0���� 
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