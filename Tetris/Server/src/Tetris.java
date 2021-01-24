
import javax.swing.*;

import java.awt.*;

import java.awt.event.*;



public class Tetris extends JFrame {

	// �ڽ��� ���� ����
    int[][] board; // ���Ӻ���
    int[][] block_board; // ������
    int width; // ���� ����
    int height; // ���� ����
    int rotate=0; // �� ȸ������
    int x; // x��ǥ
    int y; // y��ǥ
    int[][][] block; // ��
    int[][][] next_block; // ���� ��
    int blockW; // ���� �� ����
    int blockH; // ���� �� ����
    int blockR; // ���� �� ȸ��Ÿ��
    int blockT; // �� ���
    boolean check = true;
    boolean ready = false; // �غ����
    int score;
    JLabel player_score = new JLabel();
    JLabel Score = new JLabel("����");
    JLabel Title = new JLabel("Tetris");
    JButton Ready = new JButton("Start");
    GameBoard swing_board = new GameBoard();
    Next_Block n_block = new Next_Block();
    
    // Ŭ���̾�Ʈ�� �������
    int[][] client_board; //Ŭ���̾�Ʈ ����
    int[][] client_block_board; // Ŭ���̾�Ʈ ������
    int[][][] client_next_block;
    int client_width = width; // Ŭ���̾�Ʈ ���� ����
    int client_height = height; // Ŭ���̾�Ʈ ���� ����
    int client_score; // Ŭ���̾�Ʈ ����
    int client_block_num; // Ŭ���̾�Ʈ ��� ����
    int[] client_block_pos; //Ŭ���̾�Ʈ ��� ��ǥ
    JLabel client_score_label = new JLabel();
    JLabel Score2 = new JLabel("����");
    int client_next_blocktype;
    
    public void Frame() { // ����ȭ�� ���
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

    class MyKeyListener extends KeyAdapter { // Ű �Է¹ޱ�0
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(keyCode == 37 && check == false) // LEFT
            {
                for(int i = 0;i<blockW*blockH;i++) // ������ �� �����
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                x=x-1; // �������� ��ǥ�̵�
                if(CheckCrush(x,y)) // �̵��� ���⿡ �浹 ������ �� �׸���
                {
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
                else // �浹�ϸ� ���� ��ġ�� �׸���
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
                for(int i = 0;i<blockW*blockH;i++) // ������ �� �����
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                x=x+1; // ���������� ��ǥ�̵�
                if(CheckCrush(x,y)) // �̵��� ���⿡ �浹 ������ �� �׸���
                {
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
                else // �浿�� ���� ��ġ�� �̵�
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
                for(int i = 0;i<blockW*blockH;i++) // ���� ��ġ�� �� �����
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                y=y+1; // �Ʒ��� ��ĭ�̵�
                if(CheckCrush(x,y)) // �̵��� ���⿡ �浹 ������ �� �׸���
                {
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
                else // �浿�ϸ� ������ġ�� �̵�
                {
                    y=y-1;
                    for(int i = 0;i<blockW*blockH;i++)
                    {
                        if(block[rotate][i/blockW][i%blockW]==1)
                            board[i/blockW+y][i%blockW+x] += block[rotate][i/blockW][i%blockW];
                    }
                }
            }
            if(keyCode == 38 && check == false) // UP(ȸ��)
            {
                for(int i = 0;i<blockW*blockH;i++)
                {
                    if(block[rotate][i/blockW][i%blockW]==1)
                        board[i/blockW+y][i%blockW+x] = 0;
                }
                rotate=(rotate+1)%blockR;
                if(CheckCrush(x,y)) // �̵��� ���⿡ �浹 ������ �� �׸���
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

            // SPACE, ���� �� ���� ������
            if(keyCode == 32 && check == false)
            {
                for(int i = 0;i<blockW*blockH;i++) // ���� ��ġ�� �� �����
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
    public void DrawingBoard() // ���� ���� �׸���
    {
        for(int i = 0;i<height;i++) // ����
        {
            for(int j =0;j<width;j++) // ����
            {
                if(board[i][j]==0) // ��ǥ���� ���������
                    System.out.print("��");
                else if(board[i][j]==1)// ���������
                    System.out.print("��");
                else // ��
                    System.out.print("��");
            }
            System.out.println();
        }
    }
    public void Board(int height, int width) { // ���� �ʱ�ȭ0
        this.width=width; // ���� ����
        this.height=height; // ���� ����
        board = new int[height][width];

        this.score = 0; // ���� �ʱ�ȭ
        player_score.setText(Integer.toString(score));
        for(int i =0;i<height;i++)
        {
            for(int j = 0;j<width;j++)
            {
                if(i==height-1 || j==0 || j==width-1) //���庮
                    board[i][j]=2;
                else
                    board[i][j]=0;
            }
        }
    }

    public boolean CheckCrush(int x,int y) //�浹ó��0
    {
        x = this.x;
        y = this.y;

        for(int i = 0;i<blockW*blockH;i++) // �̵���Ű���� ��ġ�� ������� ������ �̵� X
        {
            if(block[rotate][i/blockW][i%blockW]==1)
                if(board[i/blockW+y][i%blockW+x]!=0)
                    return false;
        }
        return true;
    }


}