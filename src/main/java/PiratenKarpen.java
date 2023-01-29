import pk.*;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);

    public static void main(String[] args) {

        // Checks trace, default value is false if no argument is specified.
        boolean trace = false;
        if (args.length != 0) {
            // Exits if command line argument is not 0 or 1.
            if (!(args[0].equals("1") || args[0].equals("0"))) {
                logger.fatal("Command line argument not valid");
                return;
            }
            trace = args[0].equals("1");
        }

        // If command line argument is specified, uses that strategy for each player. Random if no argument.
        Player p1, p2;
        if (args.length == 2){
            p1 = new Player("1", args[1]);
        }
        else {
            p1 = new Player("1", "random");
        }
        if (args.length == 3){
            p2 = new Player("2", args[2]);
        }
        else {
            p2 = new Player("2", "random");
        }

        System.out.println("Welcome to Piraten Karpen Simulator!");
        Dice myDice = new Dice();
        
        Games.playGames(myDice, p1, p2, 42, trace, logger);

    }

}
