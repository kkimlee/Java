


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




public class DrawGame extends JFrame implements Runnable{
	private Tetris tetris;
	DrawServerBoard swing_server_board = new DrawServerBoard();
	Next_Server_Block server_next_block = new Next_Server_Block();
	public DrawGame(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void Frame() { // 게임화면 출력
		int pos = 50;
		tetris.Board(15,10);
		ServerBoard();
		setTitle("test");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tetris.player_score.setText(Integer.toString(tetris.score));
		Container c = getContentPane();
		c.addKeyListener(new MyKeyListener());
		
		c.setLayout(null);
		
		tetris.Title.setFont(new Font("Gothic",Font.ITALIC, 30)); // 게임 이름
		c.add(tetris.Title);
		tetris.Title.setLocation(200,0);
		tetris.Title.setSize(100,50);
		
		tetris.Score.setFont(new Font("Gothic",Font.CENTER_BASELINE,20)); // 점수 텍스트
		c.add(tetris.Score);
		tetris.Score.setLocation(300,280);
		tetris.Score.setSize(50,20);
		
		tetris.player_score.setFont(new Font("Gothic",Font.CENTER_BASELINE,20)); // 현재 점수
		c.add(tetris.player_score);	
		tetris.player_score.setLocation(300,300);
		tetris.player_score.setSize(50,20);
		
		tetris.Score2.setFont(new Font("Gothic",Font.CENTER_BASELINE,20)); // 점수 텍스트
		c.add(tetris.Score2);
		tetris.Score2.setLocation(800,280);
		tetris.Score2.setSize(50,20);
		
		tetris.server_score_label.setFont(new Font("Gothic",Font.CENTER_BASELINE,20)); // 현재 점수
		c.add(tetris.server_score_label);	
		tetris.server_score_label.setLocation(800,300);
		tetris.server_score_label.setSize(50,20);
		
		c.add(tetris.n_block); // 테트리스 블럭 보드
		tetris.n_block.setLocation(pos+(tetris.width-1)*20,pos);
		tetris.n_block.setSize(120,120);
		
		c.add(server_next_block); // 테트리스 블럭 보드
		server_next_block.setLocation(500+pos+(tetris.width-1)*20,pos);
		server_next_block.setSize(120,120);

		c.add(tetris.swing_board); // 테트리스 보드
		tetris.swing_board.setLocation(pos,pos);
		tetris.swing_board.setSize(200,300);

		c.add(swing_server_board);
		swing_server_board.setLocation(500+pos,pos);
		swing_server_board.setSize(200,300);
		
		setSize(1000,500);
		setVisible(true);
		c.setFocusable(true);
		c.requestFocus();
	}
	
	class MyKeyListener extends KeyAdapter { // 키 입력받기0
		public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if(keyCode == 37 && tetris.check == false && tetris.server_ready) // LEFT
		{					
			for(int i = 0;i<tetris.blockW*tetris.blockH;i++) // 기존에 블럭 지우기
			{	
				if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
					tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] = 0;
			}
			tetris.x=tetris.x-1; // 왼쪽으로 좌표이동
			if(tetris.CheckCrush(tetris.x,tetris.y)) // 이동한 방향에 충돌 없으면 블럭 그리기
			{
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
			else // 충돌하면 원래 위치에 그리기
			{
				tetris.x=tetris.x+1;
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
		}
		if(keyCode == 39 && tetris.check == false && tetris.server_ready) // RIGHT
		{
			for(int i = 0;i<tetris.blockW*tetris.blockH;i++) // 기존에 블럭 지우기
			{	
				if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1) 
					tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] = 0;
			}
			tetris.x=tetris.x+1; // 오른쪽으로 좌표이동
			if(tetris.CheckCrush(tetris.x,tetris.y)) // 이동한 방향에 충돌 없으면 블럭 그리기
			{
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
			else // 충동시 원래 위치로 이동
			{
				tetris.x=tetris.x-1;
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
		}
		if(keyCode == 40 && tetris.check == false && tetris.server_ready) //DOWN
		{
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
			}
		}

		if(keyCode == 38 && tetris.check == false && tetris.server_ready) // UP(회전)
		{
			for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
			{	
				if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
					tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] = 0;
			}
			tetris.rotate++;
			tetris.rotate=tetris.rotate%tetris.blockR;
			if(tetris.CheckCrush(tetris.x,tetris.y)) // 이동한 방향에 충돌 없으면 블럭 그리기
			{
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
			else{
				if(tetris.rotate==0)
					tetris.rotate=tetris.block.length;
				else
					tetris.rotate=tetris.rotate-1;
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{	
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
		}

			// SPACE, 벽돌 한 번에 내리기
			if(keyCode == 32 && tetris.check == false && tetris.server_ready)
			{
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++) // 기존 위치의 블럭 지우기
				{
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] = 0;
				}
				for(;tetris.CheckCrush(tetris.x,tetris.y);tetris.y++);
				tetris.y--;
				for(int i = 0;i<tetris.blockW*tetris.blockH;i++)
				{
					if(tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW]==1)
						tetris.board[i/tetris.blockW+tetris.y][i%tetris.blockW+tetris.x] += tetris.block[tetris.rotate][i/tetris.blockW][i%tetris.blockW];
				}
			}
			repaint(); // 입력받은후 보드 다시그리기
		}

	}
	
	class GameBoard extends JPanel { // 보드 그리기
		public void paint(Graphics g)
		{
			super.paint(g);
			
			gameboard(g);
		}
		
		public void repaint()
		{
			super.repaint();
		}
		
		public void gameboard(Graphics g)
		{
			
			int x_pos;
			int y_pos;
			int size=20;
			for(int i = 0; i < tetris.height; i++)
			{
				for(int j = 0; j < tetris.width; j++)
				{
					x_pos = j*size;
					y_pos = i*size;
					
					
					if(tetris.board[i][j]==2)
					{
						g.setColor(Color.GRAY);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else if(tetris.board[i][j]==1)
					{
						g.setColor(Color.BLACK);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else
					{
						g.setColor(Color.WHITE);
						g.fillRect(x_pos, y_pos, size, size);
					}
					
					g.setColor(Color.WHITE);
					g.drawRect(x_pos, y_pos, size, size);
					
				}
			}
		}
	}
	class Next_Block extends JPanel {
		public void paint(Graphics g)
		{
			super.paint(g);
			
			Draw_Block(g);
		}
		
		public void repaint()
		{
			super.repaint();
		}
		
		public void Draw_Block(Graphics g)
		{
			int x_pos;
			int y_pos;
			int size=20;
			for(int i = 0; i < 6; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					x_pos = j*size;
					y_pos = i*size;
					
					
					if(tetris.block_board[i][j]==2)
					{
						g.setColor(Color.GRAY);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else if(tetris.block_board[i][j]==1)
					{
						g.setColor(Color.BLACK);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else
					{
						g.setColor(Color.WHITE);
						g.fillRect(x_pos, y_pos, size, size);
					}
					
					g.setColor(Color.WHITE);
					g.drawRect(x_pos, y_pos, size, size);
					
				}
			}
		}
	}
	
	class Next_Server_Block extends JPanel {
		public void paint(Graphics g)
		{
			super.paint(g);
			
			Draw_Block(g);
		}
		
		public void repaint()
		{
			super.repaint();
		}
		
		public void Draw_Block(Graphics g)
		{
			int x_pos;
			int y_pos;
			int size=20;
			for(int i = 0; i < 6; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					x_pos = j*size;
					y_pos = i*size;
					
					
					if(tetris.server_block_board[i][j]==2)
					{
						g.setColor(Color.GRAY);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else if(tetris.server_block_board[i][j]==1)
					{
						g.setColor(Color.BLACK);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else
					{
						g.setColor(Color.WHITE);
						g.fillRect(x_pos, y_pos, size, size);
					}
					
					g.setColor(Color.WHITE);
					g.drawRect(x_pos, y_pos, size, size);
					
				}
			}
		}
	}
	
	public void Board(int height, int width) { // 보드 초기화0
		tetris.width=width; // 보드 가로
		tetris.height=height; // 보드 세로
		tetris.board = new int[height][width];

		tetris.score = 0; // 점수 초기화
		tetris. player_score.setText(Integer.toString(tetris.score));
        for(int i =0;i<height;i++)
        {
            for(int j = 0;j<width;j++)
            {
                if(i==height-1 || j==0 || j==width-1) //보드벽
                	tetris.board[i][j]=2;
                else
                	tetris.board[i][j]=0;
            }
        }
    }
	
	public void ServerBoard() { // 보드 초기화
		tetris.server_board = new int[tetris.server_height][tetris.server_width];

		tetris.server_score_label.setText(Integer.toString(tetris.server_score));
        for(int i =0;i<tetris.server_height;i++)
        {
            for(int j = 0;j<tetris.server_width;j++)
            {
                if(i==tetris.server_height-1 || j==0 || j==tetris.server_width-1) //보드벽
                	tetris.server_board[i][j]=2;
                else
                	tetris.server_board[i][j]=0;
            }
        }
        
        for(int num = 0; num < tetris.server_block_num; num++)
        {
        	 for(int i =0;i<tetris.server_height;i++)
             {
                 for(int j = 0;j<tetris.server_width;j++)
                 {
                     if(i*tetris.server_width+j==tetris.server_block_pos[num]) //보드벽
                    	 tetris.server_board[i][j] = 1;
                 }
             }
        }
    }
	
	class DrawServerBoard extends JPanel { // 보드 그리기
		public void paint(Graphics g)
		{
			super.paint(g);
			
			serverboard(g);
		}
		
		public void repaint()
		{
			super.repaint();
		}
		
		public void serverboard(Graphics g)
		{
			
			int x_pos;
			int y_pos;
			int size=20;
			for(int i = 0; i < tetris.server_height; i++)
			{
				for(int j = 0; j < tetris.server_width; j++)
				{
					x_pos = j*size;
					y_pos = i*size;
					
					
					if(tetris.server_board[i][j]==2)
					{
						g.setColor(Color.GRAY);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else if(tetris.server_board[i][j]==1)
					{
						g.setColor(Color.BLACK);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else
					{
						g.setColor(Color.WHITE);
						g.fillRect(x_pos, y_pos, size, size);
					}
					
					g.setColor(Color.WHITE);
					g.drawRect(x_pos, y_pos, size, size);
					
				}
			}
		}
	}
	
	public void DrawingServerBoard() // 게임 보드 그리기
	{
		for(int i = 0;i<tetris.server_height;i++) // 세로
		{
			for(int j =0;j<tetris.server_width;j++) // 가로
			{
				if(tetris.server_board[i][j]==0) // 좌표값이 비어있으면
					System.out.print("□");
				else if(tetris.server_board[i][j]==1)// 블록있으면
					System.out.print("■");
				else // 벽
					System.out.print("▩");
			}
			System.out.println();
		}
	}
	
	public void DrawingBoard() // 게임 보드 그리기
	{
		for(int i = 0;i<tetris.height;i++) // 세로
		{
			for(int j =0;j<tetris.width;j++) // 가로
			{
				if(tetris.board[i][j]==0) // 좌표값이 비어있으면
					System.out.print("□");
				else if(tetris.board[i][j]==1)// 블록있으면
					System.out.print("■");
				else // 벽
					System.out.print("▩");
			}
			System.out.println();
		}
	}
	
	
	
	
	public void run() {
		Frame();
		while(true) {
			ServerBoard();
			super.repaint();
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
			}
		}
	}
}
