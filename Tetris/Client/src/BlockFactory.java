

public class BlockFactory extends Thread{
	private Tetris tetris;
	private Block block;
	
	public BlockFactory(Tetris tetris, Block block) {
		this.tetris = tetris;
		this.block = block;
	}
	
	
	public void Block_Board() {
		tetris.block_board = new int[6][6];
		
		for(int i =0;i<6;i++)
		{
			for(int j = 0;j<6;j++)
			{
				if(i==0 || i==5 || j==0 || j==5) //���庮
					tetris.block_board[i][j]=2;
				else
					tetris.block_board[i][j]=0;
			}
		}
	}
	

	public void CreateBlock() // �� ����
	{
		tetris.check=false;
		block.MakeBlock();
		tetris.block = block.block;// ������ ���� block�� ����
		tetris.blockW = tetris.block[0][0].length; // block�� ���� 
		tetris.blockH = tetris.block[0].length; // block�� ����
		tetris.blockR = tetris.block.length; // block�� ȸ����
		tetris.rotate = 0;
		tetris.x=tetris.width/2-1; // ������ ���� ��Ÿ���� x��ǥ
		tetris.y=0; // ������ ���� ��Ÿ���� y��ǥ
	}
	public void Next_Block()
	{
		block.SetType();
		block.MakeBlock(); // ������ �� ����
		tetris.next_block = block.block; // ������ ���� block�� ����
		int next_width = tetris.next_block[0][0].length;
		int next_height = tetris.next_block[0].length;
		
		for(int i = 0; i <next_width*next_height; i++)
		{
			if(tetris.next_block[0][i/next_width][i%next_width]==1)
				tetris.block_board[i/next_width+1][i%next_width+1] +=tetris.next_block[0][i/next_width][i%next_width];
		}
	}
	
	public void Server_Next_Block()
	{
		block.type  = tetris.server_next_blocktype;
		block.MakeBlock();
		tetris.server_next_block = block.block;
		int server_next_width = tetris.server_next_block[0][0].length;
		int server_next_height = tetris.server_next_block[0].length;
		
		for(int i = 0; i <server_next_width*server_next_height; i++)
		{
			if(tetris.server_next_block[0][i/server_next_width][i%server_next_width]==1)
				tetris.server_block_board[i/server_next_width+1][i%server_next_width+1] +=tetris.server_next_block[0][i/server_next_width][i%server_next_width];
		}
	}
	
	public void Server_Block_Board() {
		tetris.server_block_board = new int[6][6];
		
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				if(i==0 || i==5 || j==0 || j==5) //���庮
					tetris.server_block_board[i][j]=2;
				else
					tetris.server_block_board[i][j]=0;
			}
		}
	}

	
	public void Add()//0
	{
		for(int i = 0; i < tetris.blockW*tetris.blockH; i++)
		{
			if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
				tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
		}
	}
	
	public void run() {
		while(true) {
			
			if(tetris.check) {
				CreateBlock();
				Server_Block_Board();
				if(tetris.server_next_blocktype>0)
					Server_Next_Block();
				Block_Board();
				Next_Block();
				
				if(tetris.CheckCrush(tetris.x,tetris.y) && tetris.server_ready) // ������ �����غ� ���¿� ���� �� ����
				{
					Add();
				}
			}
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
			}
			
		}
	}
}
