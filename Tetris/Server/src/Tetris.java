
import javax.swing.*;

import java.awt.*;

import java.awt.event.*;



public class Tetris extends JFrame {

	// 자신의 보드 상태
    int[][] board; // 게임보드
    int[][] block_board; // 블럭보드
    int width; // 보드 가로
    int height; // 보드 세로
    int rotate=0; // 블럭 회전상태
    int x; // x좌표
    int y; // y좌표
    int[][][] block; // 블럭
    int[][][] next_block; // 다음 블럭
    int blockW; // 현재 블럭 가로
    int blockH; // 현재 블럭 세로
    int blockR; // 현재 블럭 회전타입
    int blockT; // 블럭 모양
    boolean check = true;
    boolean ready = false; // 준비상태
    int score;
    JLabel player_score = new JLabel();
    JLabel Score = new JLabel("점수");
    JLabel Title = new JLabel("Tetris");
    JButton Ready = new JButton("Start");
    GameBoard swing_board = new GameBoard();
    Next_Block n_block = new Next_Block();
    
    // 클라이언트의 보드상태
    int[][] client_board; //클라이언트 보드
    int[][] client_block_board; // 클라이언트 블럭보드
    int[][][] client_next_block;
    int client_width = width; // 클라이언트 보드 넓이
    int client_height = height; // 클라이언트 보드 높이
    int client_score; // 클라이언트 점수
    int client_block_num; // 클라이언트 블록 개수
    int[] client_block_pos; //클라이언트 블록 좌표
    JLabel client_score_label = new JLabel();
    JLabel Score2 = new JLabel("점수");
    int client_next_blocktype;
    
    public void Frame() { // 게임화면 출력
        int pos = 50;
        Board(15,10);
        setTitle("test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        player_score.setText(Integer.toString(score));
        Container c = getContentPane();
        c.addKeyListener(new MyKeyListener());

        c.setLayout(null);

        Title.setFont(new Font("Gothic",Font.ITALIC, 30));
        c.add(Title);
        Title.setLocation(200,0);
        Title.setSize(100,50);

        Score.setFont(new Font("Gothic",Font.CENTER_BASELINE,20));
        c.add(Score);
        Score.setLocation(300,280);
        Score.setSize(50,20);

        player_score.setFont(new Font("Gothic",Font.CENTER_BASELINE,20));
        c.add(player_score);
        player_score.setLocation(300,300);
        player_score.setSize(50,20);

        c.add(n_block);
        n_block.setLocation(pos+(width-1)*20,pos);
        n_block.setSize(120,120);

        c.add(swing_board);
        swing_board.setLocation(pos,pos);
        swing_board.setSize(300,400);


        setSize(500,500);
        setVisible(true);
        c.setFocusable(true);
        c.requestFocus();
    }

    class MyKeyListener extends KeyAdapter { // 키 입력받기0
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(keyCode == 37 && check == false) // LEFT
            {
                for(int i = 0;i<blockW*blockH;i++) // 기존에 블럭 지우기
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                x=x-1; // 왼쪽으로 좌표이동
                if(CheckCrush(x,y)) // 이동한 방향에 충돌 없으면 블럭 그리기
                {
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
                else // 충돌하면 원래 위치에 그리기
                {
                    x=x+1;
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
            }
            if(keyCode == 39 && check == false) // RIGHT
            {
                for(int i = 0;i<blockW*blockH;i++) // 기존에 블럭 지우기
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                x=x+1; // 오른쪽으로 좌표이동
                if(CheckCrush(x,y)) // 이동한 방향에 충돌 없으면 블럭 그리기
                {
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
                else // 충동시 원래 위치로 이동
                {
                    x=x-1;
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
            }
            if(keyCode == 40 && check == false) //DOWN
            {
                for(int i = 0;i<blockW*blockH;i++) // 기존 위치의 블럭 지우기
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                y=y+1; // 아래로 한칸이동
                if(CheckCrush(x,y)) // 이동한 방향에 충돌 없으면 블럭 그리기
                {
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
                else // 충동하면 원래위치로 이동
                {
                    y=y-1;
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
            }
            if(keyCode == 38 && check == false) // UP(회전)
            {
                for(int i = 0;i<blockW*blockH;i++)
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                rotate=(rotate+1)%blockR;
                if(CheckCrush(x,y)) // 이동한 방향에 충돌 없으면 블럭 그리기
                {
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
                else{
                    if(rotate==0)
                        rotate=block.length;
                    else
                        rotate=rotate-1;
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
            }

            // SPACE, 벽돌 한 번에 내리기
            if(keyCode == 32 && check == false)
            {
                for(int i = 0;i<blockW*blockH;i++) // 기존 위치의 블럭 지우기
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                for(;CheckCrush(x,y);y++);
                y--;
                for(int i = 0;i<blockW*blockH;i++)
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                }
            }
            repaint();
        }

    }
    class GameBoard extends JPanel {
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
            for(int i = 0; i < height; i++)
            {
                for(int j = 0; j < width; j++)
                {
                    x_pos = j*size;
                    y_pos = i*size;


                    if(board[i][j]==2)
                    {
                        g.setColor(Color.GRAY);
                        g.fillRect(x_pos, y_pos, size, size);
                    }
                    else if(board[i][j]==1)
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


                    if(block_board[i][j]==2)
                    {
                        g.setColor(Color.GRAY);
                        g.fillRect(x_pos, y_pos, size, size);
                    }
                    else if(block_board[i][j]==1)
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
        for(int i = 0;i<height;i++) // 세로
        {
            for(int j =0;j<width;j++) // 가로
            {
                if(board[i][j]==0) // 좌표값이 비어있으면
                    System.out.print("□");
                else if(board[i][j]==1)// 블록있으면
                    System.out.print("■");
                else // 벽
                    System.out.print("▩");
            }
            System.out.println();
        }
    }
    public void Board(int height, int width) { // 보드 초기화0
        this.width=width; // 보드 가로
        this.height=height; // 보드 세로
        board = new int[height][width];

        this.score = 0; // 점수 초기화
        player_score.setText(Integer.toString(score));
        for(int i =0;i<height;i++)
        {
            for(int j = 0;j<width;j++)
            {
                if(i==height-1 || j==0 || j==width-1) //보드벽
                    board[i][j]=2;
                else
                    board[i][j]=0;
            }
        }
    }

    public boolean CheckCrush(int x,int y) //충돌처리0
    {
        x = this.x;
        y = this.y;

        for(int i = 0;i<blockW*blockH;i++) // 이동시키려는 위치가 비어있지 않으면 이동 X
        {
            if(block[rotate][i/blockW][i%blockW]==1)
                if(board[i/blockW+y][i%blockW+x]!=0)
                    return false;
        }
        return true;
    }


}