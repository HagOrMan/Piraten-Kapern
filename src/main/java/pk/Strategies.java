package pk;
import java.util.ArrayList;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Strategies {

    // Allows user to take one turn until they stop rolling dice, while rerolling all non skull dice a random amount of times.
    public static int rerollAll(Dice myDice, Player player, boolean trace, Logger logger){

        // Does the first roll for the user.
        logger.info("Player " + player.getName() + " is now rolling...");
        myDice.resetRolls();
        myDice.rollDice(logger);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = countFace(myDice.getRolls(), Faces.SKULL);
        if (numSkulls > 2){
            logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");
            return 0;
        }
        
        int choice;
        int points = calcPoints(myDice);
        logger.trace("You have " + points + " points this turn!");
        logger.trace("You rolled " + numSkulls + " skulls!");

        // Comment out later, keep for simulation.
        Random bag = new Random();

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){

            // Randomly decides if they will keep their score or reroll all.
            if (bag.nextInt(2) == 0){
                choice = 0;
            }
            else{
                choice = 1;
            }

            // Ends turn.
            if (choice == 0){
                logger.info("Player " + player.getName() + " has finished rolling!");
                return points;
            }

            // Rerolls all non skull dice.
            myDice.rollDice(logger);
            numSkulls = countFace(myDice.getRolls(), Faces.SKULL);
            points = calcPoints(myDice);
            if (numSkulls > 2){
                logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");
                return 0;
            }
            else{
                logger.trace("You have " + points + " points this turn!");
                logger.trace("You rolled " + numSkulls + " skulls!");
            }

        }

    }

    
    // Returns the number of a certain face in the list.
    public static int countFace(ArrayList<Faces> rolls, Faces face){
        int counter = 0;
        for (Faces roll : rolls) {
            if (roll == face)
                counter++;
        }
        return counter;
    }

    // Logic for calculating points of their dice roll.
    public static int calcPoints(Dice myDice){

        // Basic first logic for diamond and gold points.
        int points = 100 * (countFace(myDice.getRolls(), Faces.DIAMOND) + countFace(myDice.getRolls(), Faces.GOLD));

        // Adding points for any combos.
        for (Faces face : Faces.values()){
            if (face == Faces.SKULL){ continue; }
            switch (countFace(myDice.getRolls(), face)) {
                case 3 -> points += 100;
                case 4 -> points += 200;
                case 5 -> points += 500;
                case 6 -> points += 1000;
                case 7 -> points += 2000;
                case 8 -> points += 4000;
                default -> {
                }
            }
        }

        return points;
    }

    
}
