package cz.pohy.ircbot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Logger {
//	private String line = ":Pohy!Pohy@dev.looper PRIVMSG #pohycz :!hlaska";
	private FileWriter fstream;
	BufferedWriter out;
	private Calendar cal;
	
	Logger() {
		try {
			cal = Calendar.getInstance();
			if( !new File( "logs/" + cal.get( Calendar.YEAR ) + "/" + cal.get( Calendar.MONTH ) ).exists() ) {
				if( new File( "logs/" + cal.get( Calendar.YEAR ) + "/" + cal.get( Calendar.MONTH ) ).mkdirs() ) {
					fstream = new FileWriter( "logs/" + cal.get( Calendar.YEAR ) + "/" + cal.get( Calendar.MONTH ) + "/" + cal.get( Calendar.DAY_OF_MONTH ) + ".txt", true );
				}
			} else {
				fstream = new FileWriter( "logs/" + cal.get( Calendar.YEAR ) + "/" + cal.get( Calendar.MONTH ) + "/" + cal.get( Calendar.DAY_OF_MONTH ) + ".txt", true );
			}
			out = new BufferedWriter( fstream );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public void log( String line, String channel ) {
		
		try {
			String[] tmp = line.split( " :" );
			if( tmp[0].indexOf( "!" ) >= 1 ) {
				out.write(
						timestamp( ":" ) +
						" <" + tmp[0].substring( 1, tmp[0].indexOf( "!" ) ) + "> " + tmp[1] + "\n"
						);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String timestamp( String separator ) {
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
