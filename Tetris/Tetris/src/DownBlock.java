
public class DownBlock extends Thread{
	private Tetris tetris;
	public DownBlock(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void run() {
		while(true) {
			for(int i = 0;i<tetris.blockW*tetris.blockH;i++) // ���� ��ġ�� �� �����
			{	
				if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
					tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] = 0;
			}
			tetris.y=tetris.y+1; // �Ʒ��� ��ĭ�̵�
			if(tetris.CheckCrush(tetris.x,tetris.y)) // �̵��� ���⿡ �浹 ������ �� �׸���
			{
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
			else // �浿�ϸ� ������ġ�� �̵�
			{
				tetris.y=tetris.y-1;
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
				tetris.check=true;
			}
			
			tetris.DrawingBoard();
			tetris.repaint();
			System.out.println(tetris.check);
			
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
			}
		}
	}
}
