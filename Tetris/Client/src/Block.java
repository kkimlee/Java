

public class Block {
	int type = -1;
	int block[][][];
	
	public  void SetType()
	{
		this.type = (int)(Math.random()*7);
	}
	
	public void MakeBlock()
	{
		if(this.type<0)
			SetType();
		
		switch(this.type) {
		 case 0:
			 block = new int[][][]  {{{1,1},{1,1}}};
			 break;
		 case 1:
			 block = new int[][][] {
				 {
						{0,0,0},
						{1,1,1},
						{0,1,0}
					},
					{
						{0,1,0},
						{1,1,0},
						{0,1,0}
					},
					{
						{0,1,0},
						{1,1,1},
						{0,0,0}
					},
					{
						{0,1,0},
						{0,1,1},
						{0,1,0}
					}
			 };
			 break;
		 case 2:
			 block = new int[][][] {
					{
						{0,1,0},
						{0,1,0},
						{0,1,1}
					},
					{
						{0,0,0},
						{1,1,1},
						{1,0,0}
					},
					{
						{1,1,0},
						{0,1,0},
						{0,1,0}
					},
					{
						{0,0,1},
						{1,1,1},
						{0,0,0}
					}
			 };
			 break;
		 case 3:
			 block = new int[][][] {
					{
						{0,1,1},
						{0,1,0},
						{0,1,0}
					},
					{
						{0,0,0},
						{1,1,1},
						{0,0,1}
					},
					{
						{0,1,0},
						{0,1,0},
						{1,1,0}
					},
					{
						{1,0,0},
						{1,1,1},
						{0,0,0}
					}
			 };
			 break;
		 case 4:
			 block = new int[][][] {
					{
						{1,0,0,0},
						{1,0,0,0},
						{1,0,0,0},
						{1,0,0,0}
					},
					{
						{1,1,1,1},
						{0,0,0,0},
						{0,0,0,0},
						{0,0,0,0}
					}
			 };
			 break;
		 case 5:
			 block = new int[][][] {
					{
						{0,1,0},
						{0,1,1},
						{0,0,1}
					},
					{
						{0,1,1},
						{1,1,0},
						{0,0,0}
					}
			 };
			 break;
		 case 6:
			 block = new int[][][] {
					{
						{0,1,0},
						{1,1,0},
						{1,0,0}
					},
					{
						{1,1,0},
						{0,1,1},
						{0,0,0}
					}
			 };
			 break;
		
		}
		
	}

}