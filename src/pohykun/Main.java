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
	
	@SuppressWarnings("unused")
	private static IRCBot IrcBot;
	
	private static String 	server,
							nick,
							name,
							commandPrefix,
							admins;
	private static int 		port;
    
    Main() {
    	try ( FileReader reader = new FileReader( "pohykun.conf" ) ) {
    		Properties configFile = new Properties();
			configFile.load( reader );
			
			server = configFile.getProperty( "server" );
			port = Integer.valueOf( configFile.getProperty( "port" ) );
			nick = configFile.getProperty( "nick" );
			name = configFile.getProperty( "name" );
			commandPrefix = configFile.getProperty( "commandPrefix" );
			admins = configFile.getProperty( "admins" );
		
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	IrcBot = new IRCBot( server, port, nick, name, commandPrefix, admins );
    }
    
    public static void main(String[] args) {
		new Main();	
    }
}
