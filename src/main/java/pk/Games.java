package pk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Games {

    // Simulates 42 games.
    public static void playGames(Dice myDice, Player p1, Player p2, int numGames, boolean trace, Logger logger){

        // Simulates a specified number of games and increments winner counters depending on who won.
        for (int i = 0; i < numGames; i++){
            p1.resetPoints(); p2.resetPoints();
            playOneGame(myDice, p1, p2, trace, logger);
        }

        // Prints all win percentages.
        System.out.println("\n\n\nAll 42 games are now done!");
        System.out.printf("Player 1 Win Percentage: %.2f \n", p1.calcWinPerc());
        System.out.printf("Player 2 Win Percentage: %.2f \n", p2.calcWinPerc());
        
    }

    // Plays one game of piraten karpen.
    public static void playOneGame(Dice myDice, Player p1, Player p2, boolean trace, Logger logger){

        while (p1.getPoints() < 6000 && p2.getPoints() < 6000){

            // Eachs player takes their turn rolling.
            p1.addPoints(Strategies.rerollAll(myDice, p1, trace, logger));
            logger.info("Player " + p1.getName() + " has " + p1.getPoints() + " points total.");
            p2.addPoints(Strategies.rerollAll(myDice, p2, trace, logger));
            logger.info("Player " + p2.getName() + " has " + p2.getPoints() + " points total.");

            // Lets p1 go again if p2 got more than 6000 points but p1 hasn't this turn.
            if (p2.getPoints() >= 6000 && p1.getPoints() < 6000){
                p1.addPoints(Strategies.rerollAll(myDice, p1, trace, logger));
                logger.info("Player " + p1.getName() + " has " + p1.getPoints() + " points total.");
            }

        }

        // Declares the winner of the game and everyone's points.
        logger.info("The game is now over.");
        logger.info("Player " + p1.getName() + " Points: " + p1.getPoints());
        logger.info("Player " + p2.getName() + " Points: " + p2.getPoints());
        p1.playedGame(); p2.playedGame();
    
        if (p1.getPoints() > p2.getPoints()){
            logger.info("Player " + p1.getName() + " won this game!");
            p1.won();
        }
        else if (p1.getPoints() < p2.getPoints()){
            logger.info("Player " + p2.getName() + " won this game!");
            p2.won();
        }
        else{
            logger.info("The game is a tie!!!");
        }

    }
    
    
}
