import pk.*;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PiratenKarpen {

    private static Logger logger = LogManager.getLogger();
    static Scanner ui = new Scanner(System.in);

    public static void main(String[] args) {

        boolean trace = false;
        if (args.length != 0) {
            // Exits if command line argument is not 0 or 1.
            if (!(args[0].equals("1") || args[0].equals("0"))) {
                logger.fatal("Command line argument not valid");
                return;
            }
            trace = args[0].equals("1");
        }

        System.out.println("Welcome to Piraten Karpen Simulator!");
        Dice myDice = new Dice();
        Player p1 = new Player("1"), p2 = new Player("2");
        
        Games.playGames(myDice, p1, p2, 42, trace, logger);

        ui.close();
    }

}
