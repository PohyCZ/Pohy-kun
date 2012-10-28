package pohykun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Logger {
//	private String line = ":Pohy!Pohy@dev.looper PRIVMSG #pohycz :!hlaska";
	private static FileWriter fstream;
	static BufferedWriter out;
	private static Calendar cal;
	
	Logger() {
	}
	//:Pohy!0ronon0@pohy.test PRIVMSG #pohycz :!quit hovno prd sracka
	public void log( String[] lineSplit ) {
		try {
			cal = Calendar.getInstance();
			if( !new File( "logs/" 
					+ cal.get( Calendar.YEAR ) 
					+ "/" + cal.get( Calendar.MONTH ) ).exists() ) {
				if( new File( "logs/" 
						+ cal.get( Calendar.YEAR ) 
						+ "/" + cal.get( Calendar.MONTH ) ).mkdirs() ) {
					fstream = new FileWriter( "logs/" 
							+ cal.get( Calendar.YEAR ) + "/" 
							+ cal.get( Calendar.MONTH ) + "/" 
							+ cal.get( Calendar.DAY_OF_MONTH ) + ".txt", true );
				}
			} else {
				fstream = new FileWriter( "logs/" 
						+ cal.get( Calendar.YEAR ) + "/" 
						+ cal.get( Calendar.MONTH ) + "/" 
						+ cal.get( Calendar.DAY_OF_MONTH ) 
						+ ".txt", true );
			}
			out = new BufferedWriter( fstream );
			
			if( lineSplit[0].indexOf( "!" ) >= 1 && message( lineSplit ).length() > 2 ) {
				out.write(
						timestamp( ":" ) +
						" <" + lineSplit[0].substring( 1, lineSplit[0].indexOf( "!" ) ) + "> " + message( lineSplit ).substring( 2 ) + "\n"
						);
			}
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void log( String msg, String nick ) {
		try {
			cal = Calendar.getInstance();
			if( !new File( "logs/" 
					+ cal.get( Calendar.YEAR ) 
					+ "/" + cal.get( Calendar.MONTH ) ).exists() ) {
				if( new File( "logs/" 
						+ cal.get( Calendar.YEAR ) 
						+ "/" + cal.get( Calendar.MONTH ) ).mkdirs() ) {
					fstream = new FileWriter( "logs/" 
							+ cal.get( Calendar.YEAR ) + "/" 
							+ cal.get( Calendar.MONTH ) + "/" 
							+ cal.get( Calendar.DAY_OF_MONTH ) + ".txt", true );
				}
			} else {
				fstream = new FileWriter( "logs/" 
						+ cal.get( Calendar.YEAR ) + "/" 
						+ cal.get( Calendar.MONTH ) + "/" 
						+ cal.get( Calendar.DAY_OF_MONTH ) 
						+ ".txt", true );
			}
			out = new BufferedWriter( fstream );
			
			out.write(
					timestamp( ":" ) +
					" <" + nick + "> " + msg + "\n"
					);
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String message( String[] lineSplit ) {
		String msg = "";
		
		for( int i = 3; i < lineSplit.length; i++ ) {
			msg += " " + lineSplit[i];
		}
		
		return msg;
	}
	
	private static String timestamp( String separator ) {
		int hour = cal.get( Calendar.HOUR_OF_DAY );
		int minute = cal.get( Calendar.MINUTE );
		int second = cal.get( Calendar.SECOND );
		
		String _hour;
		String _minute;
		String _second;
		
		String timestamp;
		
		if( hour < 10 ) {
			_hour = "0" + hour;
		} else {
			_hour = "" + hour;
		}
		
		if( minute < 10 ) {
			_minute = "0" + minute;
		} else {
			_minute = "" + minute;
		}
		
		if( second < 10 ) {
			_second = "0" + second;
		} else {
			_second = "" + second;
		}
		
		timestamp = _hour + separator + _minute + separator + _second;
		
		return timestamp;
	}
	
	public void closeLog() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
