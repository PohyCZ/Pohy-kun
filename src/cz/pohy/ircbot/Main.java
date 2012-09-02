package cz.pohy.ircbot;


public class Main {

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
