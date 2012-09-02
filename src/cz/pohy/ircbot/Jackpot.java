package cz.pohy.ircbot;

import java.util.Random;

public class Jackpot {
	
	private String[] 	first = { "Švestka", "Hruška", "Jablko", "Jackpot", "Sedmièka" },
						second = { "Švestka", "Hruška", "Jablko", "Jackpot", "Sedmièka" },
						third = { "Švestka", "Hruška", "Jablko", "Jackpot", "Sedmièka" };
	
	private String 	wonMsg = "Vyhrál jsi a ziskávaš 35 bodù.",
					lostMsg = "Naneštìstí jsi nic nevyhrál.";
	
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
