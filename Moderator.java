public class Moderator implements Runnable {
	private GameInitiation gameData; 
	private int numberAnnounced = 0; 
	public Moderator(GameInitiation gameData) {
		this.gameData = gameData;	
	}
	public void run() {

		synchronized(gameData.lock1) {
			while(!gameData.y) {
				gameData.nogen = false;
				for(int i=0;i<gameData.N;i++) {
					gameData.pChance[i]=false;
				}
				synchronized(gameData.lock2){			
					while(0 == numberAnnounced){
						try {
							gameData.lock2.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				gameData.Numbergen = numberAnnounced;
				System.out.println("Moderator generated "+numberAnnounced);
				numberAnnounced = 0;  
				gameData.nogen = true;
				gameData.lock1.notifyAll();
				while(!gameData.pChance[0] || !gameData.pChance[1] || !gameData.pChance[2]) {
					try {
						gameData.lock1.wait(); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			for(int i=0;i<gameData.N;i++) {
				if(gameData.pSuccess[i]==true)
					{
					System.out.println("Player "+(i+1)+" has won");
					break;
					}
			gameData.gameComplete = true;
			gameData.lock1.notifyAll();	
			}
		}
	}

	public void setAnnouncedNumber(int i) {
		this.numberAnnounced = i;	
	}
}