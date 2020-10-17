import java.util.Random;

public class Player implements Runnable {

	private int id;			
	private GameInitiation gameData;
	private int totalNos;   
	private final static int NO = 10;
	private int[] no=new int[NO];
	private int[] ticket = new int[NO];
	public Player(GameInitiation gameData, int id) { 
		this.id = id; 		
		this.gameData = gameData;	
		this.totalNos = 0;
		for(int i = 0; i < NO; i++) {
			Random rand = new Random();
		    int p=rand.nextInt(50);
			ticket[i] = p;
		}
		System.out.print("Player "+(id+1)+" tokens are" + " [");
		for(int i = 0; i < NO; i++) {
			System.out.print(" "+ticket[i]);
		}
		System.out.print(" ]");
		System.out.println("");
	}
	
	public void run() {
		synchronized(gameData.lock1) {			
			while(!gameData.gameComplete) {
				while(!gameData.nogen || gameData.pChance[id]) {
					try { gameData.lock1.wait();} 
					catch (InterruptedException e) { e.printStackTrace();}
				}
				if(!gameData.gameComplete) {										
				for(int i = 0; i < NO; i++) {						
					if(gameData.Numbergen == ticket[i] && this.no[i]==0) {
							this.totalNos++;
							this.no[i]=1;
							break;
						}
					}
					if(this.totalNos == 3) {
						gameData.pSuccess[this.id] = true;	
						gameData.y=true;
					}
					gameData.pChance[id] = true;
					gameData.lock1.notifyAll();
				}
			}
		}
	}
}