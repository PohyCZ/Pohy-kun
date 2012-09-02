package cz.pohy.ircbot;

import java.util.Random;

public class Jackpot {
	
	private String[] 	first = { "�vestka", "Hru�ka", "Jablko", "Jackpot", "Sedmi�ka" },
						second = { "�vestka", "Hru�ka", "Jablko", "Jackpot", "Sedmi�ka" },
						third = { "�vestka", "Hru�ka", "Jablko", "Jackpot", "Sedmi�ka" };
	
	private String 	wonMsg = "Vyhr�l jsi a zisk�va� 35 bod�.",
					lostMsg = "Nane�t�st� jsi nic nevyhr�l.";
	
	private Random random;
	
	Jackpot() {
		random = new Random();
	}
	
	public String play() {
		boolean won = false;
		
		String _first = first[random.nextInt( first.length )];
		String _second = second[random.nextInt( second.length )];
		String _third = third[random.nextInt( third.length )];
		
		if( _first == _second && _second == _third && _first == _third ) {
			won = true;
		}
		
		if( won ) {
			return "Padly ti symboly: " + _first + ", " + _second + ", " + _third + ". " + wonMsg;
		} else {
			return "Padly ti symboly: " + _first + ", " + _second + ", " + _third + ". " + lostMsg;
		}
	}
	
}
