package pk;
import java.util.ArrayList;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Strategies {

    // Allows user to take one turn until they stop rolling dice, while rerolling all non skull dice a random amount of times.
    public static int randomRoller(Dice myDice, Player player, boolean trace, Logger logger){

        // Does the first roll for the user.
        if (trace) {logger.info("Player " + player.getName() + " is now rolling...");}
        myDice.resetRolls();
        myDice.rollDice(logger, trace);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = myDice.getFaceRoll(Faces.SKULL);
        if (numSkulls > 2){
            if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
            return 0;
        }
        
        boolean choice;
        int points = calcPoints(myDice);
        if (trace) {
            logger.trace("You have " + points + " points this turn!");
            logger.trace("You rolled " + numSkulls + " skulls!");
        }

        Random bag = new Random();

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){

            // Randomly decides if they will keep their score or reroll all.
            if (bag.nextInt(2) == 0){
                choice = false;
            }
            else{
                choice = true;
            }

            // Ends turn.
            if (choice){
                if (trace) {logger.info("Player " + player.getName() + " has finished rolling!");}
                return points;
            }

            // Rerolls all non skull dice.
            myDice.rollDice(logger, trace);
            numSkulls = myDice.getFaceRoll(Faces.SKULL);
            points = calcPoints(myDice);
            if (numSkulls > 2){
                if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
                return 0;
            }
            else{
                if (trace) {
                    logger.trace("You have " + points + " points this turn!");
                    logger.trace("You rolled " + numSkulls + " skulls!");
                }
            }

        }

    }

    public static int comboRoller(Dice myDice, Player player, boolean trace, Logger logger){
        // Does the first roll for the user.
        if (trace) {logger.info("Player " + player.getName() + " is now rolling...");}
        myDice.resetRolls();
        myDice.rollDice(logger, trace);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = myDice.getFaceRoll(Faces.SKULL);
        if (numSkulls > 2){
            if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
            return 0;
        }

        int points = calcPoints(myDice);
        if (trace) {
            logger.trace("You have " + points + " points this turn!");
            logger.trace("You rolled " + numSkulls + " skulls!");
        }

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){

            // Ends turn depending on the combo player strategy.
            if (comboStopStrategy(myDice)){
                if (trace) {logger.info("Player " + player.getName() + " has finished rolling!");}
                return points;
            }

            // Rerolls all specified non skull dice.
            myDice.rollComboDice(logger, comboRollStrategy(myDice), trace);
            numSkulls = myDice.getFaceRoll(Faces.SKULL);
            points = calcPoints(myDice);
            if (numSkulls > 2){
                if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
                return 0;
            }
            else{
                if (trace) {
                    logger.trace("You have " + points + " points this turn!");
                    logger.trace("You rolled " + numSkulls + " skulls!");
                }
            }

        }

    }

    // Decides which dice to reroll to get the best score.
    public static ArrayList<Integer> comboRollStrategy(Dice myDice){
        ArrayList<Integer> rerolls = new ArrayList<Integer>();

        // Checks for at least 5 of gold or diamonds, keeping those, then at least 6 of anything else.
        // Checking gold
        for (int i = 0; i < myDice.getRolls().size(); i++){
            if (myDice.getRolls().get(i) == Faces.GOLD){
                rerolls.add(i);
            }
        }
        if (rerolls.size() < 5) { rerolls.clear(); }
        else { if (verifyComboRoll(myDice, rerolls)) { return rerolls;} }

        // Checking diamonds.
        for (int i = 0; i < myDice.getRolls().size(); i++){
            if (myDice.getRolls().get(i) == Faces.DIAMOND){
                rerolls.add(i);
            }
        }
        if (rerolls.size() < 5) { rerolls.clear(); }
        else { if (verifyComboRoll(myDice, rerolls)) { return rerolls;} }

        // Checking others, keeping if at least 6.
        for (Faces face : Faces.values()){
            if (face != Faces.SKULL){
                for (int i = 0; i < myDice.getRolls().size(); i++){
                    if (myDice.getRolls().get(i) == face){
                        rerolls.add(i);
                    }
                }
                if (rerolls.size() < 6) { rerolls.clear(); }
                else { if (verifyComboRoll(myDice, rerolls)) { return rerolls;} }
            }
        }

        // Now checks if at least 4 gold or diamonds.
        // Checking gold
        for (int i = 0; i < myDice.getRolls().size(); i++){
            if (myDice.getRolls().get(i) == Faces.GOLD){
                rerolls.add(i);
            }
        }
        if (rerolls.size() < 4) { rerolls.clear(); }
        else { if (verifyComboRoll(myDice, rerolls)) { return rerolls;} }

        // Checking diamonds.
        for (int i = 0; i < myDice.getRolls().size(); i++){
            if (myDice.getRolls().get(i) == Faces.DIAMOND){
                rerolls.add(i);
            }
        }
        if (rerolls.size() < 4) { rerolls.clear(); }
        else { if (verifyComboRoll(myDice, rerolls)) { return rerolls;} }

        // Checking others, keeping if at least 5.
        for (Faces face : Faces.values()){
            if (face != Faces.SKULL){
                for (int i = 0; i < myDice.getRolls().size(); i++){
                    if (myDice.getRolls().get(i) == face){
                        rerolls.add(i);
                    }
                }
                if (rerolls.size() < 5) { rerolls.clear(); }
                else {
                    // Adds any gold or diamonds to this if possible.
                    for (int i = 0; i < myDice.getRolls().size(); i++){
                        if (myDice.getRolls().get(i) == Faces.DIAMOND || myDice.getRolls().get(i) == Faces.GOLD){
                            rerolls.add(i);
                        }
                    }
                    if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
                }
            }
        }

        // Checking if at least 3 of one item and at least 2 of gold or diamonds.
        for (Faces face : Faces.values()){
            if (face != Faces.SKULL && face != Faces.GOLD && face != Faces.DIAMOND) {

                // First checks for at least 3 of this item.
                if (myDice.getFaceRoll(face) >= 3){

                    for (int i = 0; i < myDice.getRolls().size(); i++){
                        if (myDice.getRolls().get(i) == face){
                            rerolls.add(i);
                        }
                    }
                    // Next checks for at least 2 gold or diamond.
                    if (myDice.getFaceRoll(Faces.DIAMOND) + myDice.getFaceRoll(Faces.GOLD) >= 2 ){
                        for (int i = 0; i < myDice.getRolls().size(); i++){
                            if (myDice.getRolls().get(i) == Faces.DIAMOND || myDice.getRolls().get(i) == Faces.GOLD){
                                rerolls.add(i);
                            }
                        }
                        if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
                    }
                }
            }
        }
        rerolls.clear();

        // Returns any gold and diamonds if nothing else worked.
        // Adds any gold or diamonds to this if possible.
        for (int i = 0; i < myDice.getRolls().size(); i++){
            if (myDice.getRolls().get(i) == Faces.DIAMOND || myDice.getRolls().get(i) == Faces.GOLD){
                rerolls.add(i);
                // If adding this puts us over the max amount of dice to keep, just returns rerolls.
                if (!verifyComboRoll(myDice, rerolls)){ rerolls.remove(i); return rerolls; }
            }
        }
        if (rerolls.size() > 0){ if (verifyComboRoll(myDice, rerolls)) { return rerolls;} }

        // Final option is returning a 'blank' value if this somehow happens to reroll all.
        rerolls.add(-1);
        return rerolls;
    }

    // Decides if the player should keep rolling or stop because they have enough points, true if they should stop.
    public static boolean comboStopStrategy(Dice myDice){

        // Returns immediately to stop rolling if more than or equal to 800 points.
        if (calcPoints(myDice) >= 800){
            return true;
        }
        // Keep rolling if more than or equal to 800 and no skulls yet.
        if (calcPoints(myDice) >= 600 && myDice.getFaceRoll(Faces.SKULL) == 0){
            return false;
        }
        // Keep rolling if less than or equal to 300 and 2 skulls.
        if (calcPoints(myDice) <= 300 && myDice.getFaceRoll(Faces.SKULL) == 2){
            return false;
        }
        // If more than 3 diamonds or gold and 1 or fewer skulls, keep rolling.
        if (myDice.getFaceRoll(Faces.SKULL) <= 1 &&
                (myDice.getFaceRoll(Faces.DIAMOND) >= 3 || myDice.getFaceRoll(Faces.GOLD) >= 3)){
            return false;
        }

        // Checks if we have any combo of 3 or more and 1 skull max, in which case we want to reroll.
        for (Faces face : Faces.values()){
            if (face != Faces.SKULL){
                if (myDice.getFaceRoll(face) >= 3 && myDice.getFaceRoll(Faces.SKULL) <= 1){
                    return false;
                }
            }
        }

        // Checks if we have any combo of 5 or more and 2 skulls, in which case we want to end our turn.
        for (Faces face : Faces.values()){
            if (face != Faces.SKULL){
                if (myDice.getFaceRoll(face) >= 5 && myDice.getFaceRoll(Faces.SKULL) == 2){
                    return true;
                }
            }
        }

        // Keep rolling if more than or equal to 300 points and 1 or fewer skulls.
        if (calcPoints(myDice) >= 300 && myDice.getFaceRoll(Faces.SKULL) <= 1){
            return false;
        }

        // If nothing above is true, decide based on if more than 300 points, stopping the roll if yes.
        return calcPoints(myDice) > 300;
    }

    // Ensures that the number of dice we want to reroll isn't less than 2.
    public static boolean verifyComboRoll(Dice myDice, ArrayList<Integer> rerolls){
        return 8 - rerolls.size() - myDice.getFaceRoll(Faces.SKULL) >= 2;
    }

    // Logic for calculating points of their dice roll.
    public static int calcPoints(Dice myDice){

        // Basic first logic for diamond and gold points.
        int points = 100 * (myDice.getFaceRoll(Faces.DIAMOND) + myDice.getFaceRoll(Faces.GOLD));

        // Adding points for any combos.
        for (Faces face : Faces.values()){
            if (face == Faces.SKULL){ continue; }
            switch (myDice.getFaceRoll(face)) {
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
