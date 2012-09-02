/**
 * @author David Pohan <0ronon0@gmail.com>
 * @version 1.0
 * @since 2012-08-31
 */

package cz.pohy.ircbot;

import java.io.*;
import java.net.Socket;

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
	 * Bot nickname
	 */
	private String nick;
	/**
	 * "Real" name of bot
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
	
	private Jackpot jackpot;
	
	private Logger logger;
	private String currentChannel;

	IRCBot( String server, int port, String nick, String name, String commandPrefix ) {
		this.server = server;
		this.port = port;
		this.nick = nick;
		this.name = name;
		this.commandPrefix = commandPrefix;
		
		jackpot = new Jackpot();
		
		logger = new Logger();
		
		try {
			//fstream = new FileWriter( "log" );
			//out = new BufferedWriter( fstream );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
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
			System.out.println( cmd + " " + msg );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void send_data( String cmd ) {
		try {
			writer.write( cmd + "\r\n" );
			System.out.println( cmd );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	private void command( String clientCmd, String serverCmd, String[] tmp ) {
//		if( tmp[1].equals( commandPrefix + clientCmd ) ) {
//			System.out.println( ">>" + clientCmd );
//			send_data( serverCmd );
//		}
//	}
	
	private void command( String clientCmd, String serverCmd, String user, String[] tmp ) {
		if( tmp[1].equals( commandPrefix + clientCmd ) && tmp[0].substring( 1, tmp[0].indexOf( "!" ) ).equals( user ) ) {
			System.out.println( ">>" + clientCmd );
			send_data( serverCmd );
		}
	}
	
	private void commandChan( String clientCmd, String serverCmd, String msg, String[] tmp ) {
		if( tmp[1].equals( commandPrefix + clientCmd ) ) {
			System.out.println( ">>" + clientCmd + ": " + msg );
			send_data( serverCmd, tmp[0].substring( tmp[0].indexOf( "#" ) ) + " :" + msg );
		}
	}
	
	private void commandUser( String clientCmd, String serverCmd, String msg, String[] tmp ) {
		if( tmp[1].equals( commandPrefix + clientCmd ) ) {
			System.out.println( ">>" + clientCmd + ": " + msg );
			send_data( serverCmd, tmp[0].substring( tmp[0].indexOf( "#" ) ) + " :" + tmp[0].substring( 1, tmp[0].indexOf( "!" ) ) + ": " + msg );
		}
	}
	
	private void main() {
	    try {
	        writer.flush();
	
			while ( (line = reader.readLine()) != null ) {
				
	            if ( line.toLowerCase().startsWith( "PING ") ) {
	                writer.write("PONG " + line.substring(5) + "\r\n");
	                writer.flush( );
	            }
	            
	            System.out.println( line );
	            
	            String[] tmp =  line.split( " :" );
	            if ( tmp.length > 1 ) {
	            	String[] args = tmp[1].split( " " );
	            	
	            	command( "quit", "QUIT", "Pohy", tmp );
	            	
	            	commandChan( "hlaska", "PRIVMSG", "Java powa, smradi!", tmp );
	            	
	            	commandUser( "jackpot", "PRIVMSG", jackpot.play(), tmp );
	            	
					String join = "join";
					if( args[0].equals( commandPrefix + join ) && args[1].length() >= 1 ) {
				        	join_channel( args[1] );
				        	System.out.println( ">>Joining " + args[1] );
					}
					
					//logger.log( line + "\n", tmp[0].substring( tmp[0].indexOf( "#" ) ) );
					
					writer.flush();
				}
	            
	        }
			//logger.closeLog();
	    } catch( Throwable e ) {
	    	e.printStackTrace();
	    }
	}
}
