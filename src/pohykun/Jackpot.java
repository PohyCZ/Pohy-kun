package pohykun;

import java.util.Random;

public class Jackpot {
	
	private String[] 	first = { "�vestka", "Hru�ka", "Jablko", "Jackpot", "Sedmi�ka" },
						second = { "�vestka", "Hru�ka", "Jablko", "Jackpot", "Sedmi�ka" },
						third = { "�vestka", "Hru�ka", "Jablko", "Jackpot", "Sedmi�ka" };
	
	private String 	wonMsg = "Vyhr�l jsi a zisk�va� 35 bod�.",
					lostMsg = "Nane�t�st� jsi nic nevyhr�l.";
	
	private Random random;
	
	private static String dbName = "jackpot.xml";
	private static int waitTime = 900000;
	
	private ScoreBoard scoreBoard;
	
	Jackpot() {
		random = new Random();
		scoreBoard = new ScoreBoard(dbName);
	}
	
	public String play( String user ) {
		if(scoreBoard.read(user, "played") == null) 
			scoreBoard.add(user, "" + (System.currentTimeMillis() - 900000));
		
		int time = (int)(System.currentTimeMillis() - Long.valueOf(scoreBoard.read(user, "time")));
		System.out.println( time );
		if ( time >= waitTime ) {
			boolean won = false;
			String _first = first[random.nextInt(first.length)];
			String _second = second[random.nextInt(second.length)];
			String _third = third[random.nextInt(third.length)];
			if (_first == _second && _second == _third && _first == _third) {
				won = true;
			}
			writePlay(user, won);
			if (won) {
				return "Padly ti symboly: " + _first + ", " + _second + ", " + _third + ". " + wonMsg;
			} else {
				return "Padly ti symboly: " + _first + ", " + _second + ", " + _third + ". " + lostMsg;
			}
		} else {
			return "M��e� hr�t a� za " + ( ( waitTime - time ) / 1000 ) + " sekund.";
		}
	}
	
	public String stat(String user) {
		String statMsg = null;
		
		
		
		return statMsg;
	}
	
	private void writePlay( String nick, boolean won ) {
		long time = System.currentTimeMillis();
		
		int _won, _played;
		_played = Integer.valueOf(scoreBoard.read(nick, "played")) + 1;
		_won = Integer.valueOf(scoreBoard.read(nick, "won")) + 1;
		
		scoreBoard.update(nick, "played", "" + _played);
		if(won)
			scoreBoard.update(nick, "won", "" + _won);
		scoreBoard.update(nick, "time", "" + time);
	}
	
}
