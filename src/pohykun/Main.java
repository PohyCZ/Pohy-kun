/**
 * @author David Pohan <0ronon0@gmail.com>
 * @version 0.02
 * @since 2012-08-31
 */

package pohykun;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Main {

//    private static String server = "127.0.0.1";
//    private static int port = 6669;
//	private static String server = "irc.rizon.net";
//	private static int port = 6667;
//    private static String nick = "Pohy-kun";
//    private static String name = "PohyBot";
//    private static String commandPrefix = "!";
	
	private static String 	server,
							nick,
							name,
							commandPrefix,
							admins;
	private static int 		port;
	
    public static void main(String[] args) {
    	try ( FileReader reader = new FileReader( "pohykun.conf" ) ) {
    		Properties configFile = new Properties();
			configFile.load( reader );
			
			server = configFile.getProperty( "server" );
			port = Integer.valueOf( configFile.getProperty( "port" ) );
			nick = configFile.getProperty( "nick" );
			name = configFile.getProperty( "name" );
			commandPrefix = configFile.getProperty( "commandPrefix" );
			admins = configFile.getProperty( "admins" );
		
			@SuppressWarnings("unused")
			IRCBot IrcBot = new IRCBot( server, port, nick, name, commandPrefix, admins );
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
