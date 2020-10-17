import java.util.Random;
import java.lang.Thread;
public class OOPAssignment {

	public static void main(String [] args) {
		
		final int N=3;
		final GameInitiation game  = new GameInitiation();
		final Moderator moderator  = new Moderator(game);
		final Player[] player1=new Player[N]; 
		for(int i=0;i<N;i++) {
			player1[i] = new Player(game, i);
		}
		Thread dealerThread  = new Thread(moderator);
		Thread[] player1Thread = new Thread[N];
		for(int i=0;i<N;i++) {
			player1Thread[i] = new Thread(player1[i]);
		}
		dealerThread. start();
		for(int i=0;i<N;i++) {
			player1Thread[i].start();
		}
		while (!game.y) {
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			Random rand = new Random();
			int x=rand.nextInt(50);
			synchronized(game.lock2) {
				moderator.setAnnouncedNumber(x+1);
				game.lock2.notify();
			}	
		}
		
	}
}