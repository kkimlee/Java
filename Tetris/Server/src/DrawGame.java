


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class DrawGame extends JFrame implements Runnable{
	private Tetris tetris;
	DrawClientBoard swing_client_board = new DrawClientBoard();
	Next_Client_Block client_next_block = new Next_Client_Block();
	public DrawGame(Tetris tetris) {
		this.tetris = tetris;
	}
	
	public void Frame() { // 게임화면 출력
		int pos = 50;
		tetris.Board(15,10);
		ClientBoard();
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
		
		tetris.client_score_label.setFont(new Font("Gothic",Font.CENTER_BASELINE,20)); // 현재 점수
		c.add(tetris.client_score_label);	
		tetris.client_score_label.setLocation(800,300);
		tetris.client_score_label.setSize(50,20);
		
		c.add(tetris.Ready); // 시작단추
		tetris.Ready.setLocation(300,320);
		tetris.Ready.setSize(100,20);
		tetris.Ready.addActionListener(new MyActionListener());
		
		c.add(tetris.n_block); // 테트리스 블럭 보드
		tetris.n_block.setLocation(pos+(tetris.width-1)*20,pos);
		tetris.n_block.setSize(120,120);
		
		c.add(client_next_block); // 테트리스 블럭 보드
		client_next_block.setLocation(500+pos+(tetris.width-1)*20,pos);
		client_next_block.setSize(120,120);

		c.add(tetris.swing_board); // 테트리스 보드
		tetris.swing_board.setLocation(pos,pos);
		tetris.swing_board.setSize(200,300);

		c.add(swing_client_board);
		swing_client_board.setLocation(500+pos,pos);
		swing_client_board.setSize(200,300);
		
		setSize(1000,500);
		setVisible(true);
		c.setFocusable(true);
		c.requestFocus();
	}
	
	class MyActionListener implements ActionListener { // 준비 버튼 리스너
		public void actionPerformed(ActionEvent e) {
			JButton ready = (JButton)e.getSource();
			if(ready.getText().equals("Start") && tetris.ready == false) { // Start누르면 시작 클라이언트도 시작됨
				ready.setText("Stop");
				tetris.ready = true;
				
				
			}
			else if(ready.getText().equals("Stop") && tetris.ready==true) { // Stop누르면 멈춤 클라이언트도 멈춤
				ready.setText("Start");
				tetris.ready = false;
				
				
			}
		}
	}
	
	class MyKeyListener extends KeyAdapter { // 키 입력받기0
		public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if(keyCode == 37 && tetris.check == false && tetris.ready) // LEFT
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
		if(keyCode == 39 && tetris.check == false && tetris.ready) // RIGHT
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
		if(keyCode == 40 && tetris.check == false && tetris.ready) //DOWN
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

		if(keyCode == 38 && tetris.check == false && tetris.ready) // UP(회전)
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
			if(keyCode == 32 && tetris.check == false && tetris.ready)
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
	class Next_Client_Block extends JPanel {
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
					
					
					if(tetris.client_block_board[i][j]==2)
					{
						g.setColor(Color.GRAY);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else if(tetris.client_block_board[i][j]==1)
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
	
	public void ClientBoard() { // 보드 초기화
		tetris.client_board = new int[tetris.client_height][tetris.client_width];

		tetris.client_score_label.setText(Integer.toString(tetris.client_score));
        for(int i =0;i<tetris.client_height;i++)
        {
            for(int j = 0;j<tetris.client_width;j++)
            {
                if(i==tetris.client_height-1 || j==0 || j==tetris.client_width-1) //보드벽
                	tetris.client_board[i][j]=2;
                else
                	tetris.client_board[i][j]=0;
            }
        }
        
        for(int num = 0; num < tetris.client_block_num; num++)
        {
        	 for(int i =0;i<tetris.client_height;i++)
             {
                 for(int j = 0;j<tetris.client_width;j++)
                 {
                     if(i*tetris.client_width+j==tetris.client_block_pos[num]) //보드벽
                    	 tetris.client_board[i][j] = 1;
                 }
             }
        }
    }
	
	class DrawClientBoard extends JPanel { // 보드 그리기
		public void paint(Graphics g)
		{
			super.paint(g);
			
			clientboard(g);
		}
		
		public void repaint()
		{
			super.repaint();
		}
		
		public void clientboard(Graphics g)
		{
			
			int x_pos;
			int y_pos;
			int size=20;
			for(int i = 0; i < tetris.client_height; i++)
			{
				for(int j = 0; j < tetris.client_width; j++)
				{
					x_pos = j*size;
					y_pos = i*size;
					
					
					if(tetris.client_board[i][j]==2)
					{
						g.setColor(Color.GRAY);
						g.fillRect(x_pos, y_pos, size, size);
					}
					else if(tetris.client_board[i][j]==1)
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
	
	
	
	public void run() {
		Frame();
		while(true) {
			ClientBoard();
			super.repaint();
			try {
				Thread.sleep(500);
			} catch(InterruptedException e) {
			}
		}
	}
}
