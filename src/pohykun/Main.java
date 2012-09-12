/**
 * @author David Pohan <0ronon0@gmail.com>
 * @version 0.02
 * @since 2012-08-31
 */

package pohykun;


public class Main {

//    private static String server = "127.0.0.1";
//    private static int port = 6669;
	private static String server = "irc.rizon.net";
	private static int port = 6667;
    private static String nick = "Pohy-kun";
    private static String name = "PohyBot";
    private static String commandPrefix = "!";
	
    public static void main(String[] args) {
        @SuppressWarnings("unused")
		IRCBot IrcBot = new IRCBot( server, port, nick, name, commandPrefix );
    }
}
