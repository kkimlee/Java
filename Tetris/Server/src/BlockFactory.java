

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
				if(i==0 || i==5 || j==0 || j==5) //보드벽
					tetris.block_board[i][j]=2;
				else
					tetris.block_board[i][j]=0;
			}
		}
	}
	
	
	public void CreateBlock() // 블럭 생성
	{
		tetris.check=false;
		block.MakeBlock();
		tetris.block = block.block;// 생성된 블럭을 block에 저장
		tetris.blockW = tetris.block[0][0].length; // block의 가로 
		tetris.blockH = tetris.block[0].length; // block의 세로
		tetris.blockR = tetris.block.length; // block의 회전값
		tetris.rotate = 0;
		tetris.x=tetris.width/2-1; // 생성된 블럭이 나타나는 x좌표
		tetris.y=0; // 생성된 블럭이 나타나는 y좌표
	}
	public void Next_Block()
	{
		block.SetType();
		block.MakeBlock(); // 임의의 블럭 생성
		tetris.next_block = block.block; // 생성된 블럭을 block에 저장
		int next_width = tetris.next_block[0][0].length;
		int next_height = tetris.next_block[0].length;
		
		for(int i = 0; i <next_width*next_height; i++)
		{
			if(tetris.next_block[0][i/next_width][i%next_width]==1)
				tetris.block_board[i/next_width+1][i%next_width+1] +=tetris.next_block[0][i/next_width][i%next_width];
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
	
	public void Client_Block_Board() {
		tetris.client_block_board = new int[6][6];
		
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 6; j++)
			{
				if(i==0 || i==5 || j==0 || j==5) //보드벽
					tetris.client_block_board[i][j]=2;
				else
					tetris.client_block_board[i][j]=0;
			}
		}
	}
	
	public void Client_Next_Block()
	{
		block.type = tetris.client_next_blocktype;
		block.MakeBlock();
		tetris.client_next_block = block.block;
		int client_next_width = tetris.client_next_block[0][0].length;
		int client_next_height = tetris.client_next_block[0].length;
	
		for(int i = 0; i <client_next_width*client_next_height; i++)
		{
			
			if(tetris.client_next_block[0][i/client_next_width][i%client_next_width]==1)
				tetris.client_block_board[i/client_next_width+1][i%client_next_width+1] +=tetris.client_next_block[0][i/client_next_width][i%client_next_width];
		}
	}
	
	public void run() {
		while(true) {
			
			if(tetris.check) {
				
				CreateBlock();
				Client_Block_Board();
				if(tetris.client_next_blocktype>0)
					Client_Next_Block();		
				Block_Board();
				Next_Block();
				
		
				if(tetris.CheckCrush(tetris.x,tetris.y) && tetris.ready) // 서버준비되면 블럭 생성
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
