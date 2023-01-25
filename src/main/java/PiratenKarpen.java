import pk.*;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);
    static Scanner ui = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        Dice myDice = new Dice();
        Player p1 = new Player("1"), p2 = new Player("2");
        
        Games.playGames(myDice, p1, p2, 42);

        ui.close();
    }

}
