package pohykun;

import java.io.*;
import java.net.Socket;
import static java.lang.System.out;

public class IRCBot {
	
	/**
	 * Server to connect to
	 */
	private String server;
	/**
	 * Port for connection to server
	 */
	private int port;
	/**
	 * Bot's nickname
	 */
	private String nick;
	/**
	 * "Real" name of the bot
	 */
	private String name;
	private String channel = "#pohycz";
	/**
	 * Prefix for commands that will bot recognize like "!", "." or "@", etc...
	 */
	private String commandPrefix;
	
	/**
	 * Buffer for sending data to server
	 */
	private BufferedWriter writer;
	/**
	 * Buffer for receiving data from server
	 */
	private BufferedReader reader;
	
	/**
	 * String for outputing data from server
	 */
	private String line = null;
	
	private String[] admins;
	
	private Logger logger = new Logger();
	
	private Jackpot jackpot;

	IRCBot( String server, int port, String nick, String name, String commandPrefix, String admins ) {
		this.server = server;
		this.port = port;
		this.nick = nick;
		this.name = name;
		this.commandPrefix = commandPrefix;
		
		this.admins = admins.split( "," );
		
		jackpot = new Jackpot();
		
		initialize();
	}
	
	private void initialize() {
		try {
			Socket socket = new Socket( server, port );
	        writer = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ) );
	        reader = new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
	        
	        login( nick, name );
	        join_channel( channel );
	        
	        main();
		} catch( Throwable e ) {
			e.printStackTrace();
		}
	}

	private void login( String nick, String name ) {
		send_data( "USER", nick + " pohy.g6.cz " + nick + " :" + name );
		send_data( "NICK", nick );
	}
	
	private void join_channel( String channel ) {
		send_data( "JOIN", channel );
	}

	private void send_data( String cmd, String msg ) {
		try {
			writer.write( cmd + " " + msg + "\r\n" );
			out.println( "Sent data to server: " + cmd + " " + msg );
			writer.flush();
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void send_data( String cmd ) {
		try {
			writer.write( cmd + "\r\n" );
			out.println( cmd );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void main() {
	    try {
	
			while ( (line = reader.readLine()) != null ) {
				
	            out.println( line );
	            
	            String[] lineSplit = line.split( " " );
	            
	            logger.log( lineSplit );
	            
	            String user;
	            if( lineSplit[0].indexOf( "!" ) > 1 ) {
            		user = lineSplit[0].substring( 1, lineSplit[0].indexOf( "!" ) );
            	} else {
            		user = "";
            	}
	            
	            if( lineSplit[0].equals( "PING" ) ) {
	            	send_data( "PONG", lineSplit[1] );
	            }
	            
	            //user commands
	            if( lineSplit.length > 3 ) {
	            	if( lineSplit[3].length() > 1 && lineSplit[3].substring( 1, 2 ).equals( commandPrefix ) ) {
		            	switch( lineSplit[3].substring( 2, lineSplit[3].length() ) ) {
		            	case "jackpot":
		            		send_data( "PRIVMSG", lineSplit[2] + " :" + user + ": " + jackpot.play() );
		            		break;
		            	}
	            	}
	            }
	            
	            //admin commands
	            if( lineSplit.length > 3 ) {
	            	for( int i = 0; i < admins.length; i++ ) {
		            	if( lineSplit[3].length() > 1 && lineSplit[3].substring( 1, 2 ).equals( commandPrefix ) 
		            			&& lineSplit[0].length() > 1 && lineSplit[0].substring( 1, lineSplit[0].indexOf( "!" ) ).equals( admins[i] ) ) {
			            	switch( lineSplit[3].substring( 2, lineSplit[3].length() ) ) {
			            	case "join":
			            		if( lineSplit.length > 4 ) {
			            			join_channel( lineSplit[4] );
			            		} else {
			            			send_data( "PRIVMSG", lineSplit[2] + " :" + user + ": What channel you want me to join?" );
			            			out.println( "Channel not specified" );
			            		}
			            		break;
			            	case "part":
			            		if( lineSplit.length > 4 ) {
			            			send_data( "PART", lineSplit[4] );
			            		} else {
			            			send_data( "PART", lineSplit[2] );
			            		}
			            		break;
			            	case "quit":
			            		send_data( "QUIT", "quit" );
			            		break;
			            	}
		            	}
	            	}
	            }
	            
	        }
	    } catch( Throwable e ) {
	    	e.printStackTrace();
	    }
	}
	
}
