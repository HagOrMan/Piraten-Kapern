package pk;
import java.util.ArrayList;
import java.util.Random;
import org.apache.logging.log4j.Logger;

public class Strategies {

    // Allows user to take one turn until they stop rolling dice, while rerolling all non skull dice a random amount of times.
    public static int randomRoller(Dice myDice, Player player, boolean trace, Logger logger, Card card){

        // Does the first roll for the user.
        if (trace) {logger.info("Player " + player.getName() + " is now rolling...");}
        myDice.resetRolls();
        myDice.rollDice(logger, trace);
        int points = calcPoints(myDice, card);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = myDice.getFaceRoll(Faces.SKULL);
        if (numSkulls > 2){
            if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
            return points;
        }
        
        boolean choice;
        if (trace) {
            logger.trace("You have " + points + " points this turn!");
            logger.trace("You rolled " + numSkulls + " skulls!");
        }

        Random bag = new Random();

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){

            // Randomly decides if they will keep their score or reroll all.
            choice = bag.nextInt(2) != 0;

            // Ends turn.
            if (choice){
                if (trace) {logger.info("Player " + player.getName() + " has finished rolling!");}
                return points;
            }

            // Rerolls all non skull dice.
            myDice.rollDice(logger, trace);
            numSkulls = myDice.getFaceRoll(Faces.SKULL);
            points = calcPoints(myDice, card);
            if (numSkulls > 2){
                if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
                return points;
            }
            else{
                if (trace) {
                    logger.trace("You have " + points + " points this turn!");
                    logger.trace("You rolled " + numSkulls + " skulls!");
                }
            }

        }

    }

    public static int comboRoller(Dice myDice, Player player, boolean trace, Logger logger, Card card){
        // Does the first roll for the user.
        if (trace) {logger.info("Player " + player.getName() + " is now rolling...");}
        myDice.resetRolls();
        myDice.rollDice(logger, trace);
        int points = calcPoints(myDice, card);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = myDice.getFaceRoll(Faces.SKULL);
        if (numSkulls > 2){
            if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
            return points;
        }

        if (trace) {
            logger.trace("You have " + points + " points this turn!");
            logger.trace("You rolled " + numSkulls + " skulls!");
        }

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){

            // Ends turn depending on the combo player strategy.
            if (comboStopStrategy(myDice, card)){
                if (trace) {logger.info("Player " + player.getName() + " has finished rolling!");}
                return points;
            }

            // Rerolls all specified non skull dice.
            myDice.rollComboDice(logger, comboRollStrategy(myDice), trace);
            numSkulls = myDice.getFaceRoll(Faces.SKULL);
            points = calcPoints(myDice, card);

            if (numSkulls > 2){
                if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
                return points;
            }
            else{
                if (trace) {
                    logger.trace("You have " + points + " points this turn!");
                    logger.trace("You rolled " + numSkulls + " skulls!");
                }
            }

        }

    }

    // Decides which dice to reroll to get the best score. Checks in priority of what gives the most points.
    public static ArrayList<Integer> comboRollStrategy(Dice myDice){
        ArrayList<Integer> rerolls = new ArrayList<>();

        // Checks for at least 5 of gold or diamonds, keeping those, then at least 6 of anything else.
        // Checking gold
        if (myDice.getFaceRoll(Faces.GOLD) >= 5) {
            for (int i = 0; i < myDice.getRolls().size(); i++) {
                if (myDice.getRolls().get(i) == Faces.GOLD) {
                    rerolls.add(i);
                }
            }
            if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
        }
        rerolls.clear();

        // Checking diamonds.
        if (myDice.getFaceRoll(Faces.DIAMOND) >= 5) {
            for (int i = 0; i < myDice.getRolls().size(); i++) {
                if (myDice.getRolls().get(i) == Faces.DIAMOND) {
                    rerolls.add(i);
                }
            }
            if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
        }
        rerolls.clear();

        // Checking others, keeping if at least 6.
        for (Faces face : Faces.values()){
            if (face != Faces.SKULL){
                if (myDice.getFaceRoll(face) >= 6) {
                    for (int i = 0; i < myDice.getRolls().size(); i++) {
                        if (myDice.getRolls().get(i) == face) {
                            rerolls.add(i);
                        }
                    }
                    if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
                }
            }
        }
        rerolls.clear();

        // Now checks if at least 4 gold or diamonds.
        // Checking gold.
        if (myDice.getFaceRoll(Faces.GOLD) >= 4) {
            for (int i = 0; i < myDice.getRolls().size(); i++) {
                if (myDice.getRolls().get(i) == Faces.GOLD) {
                    rerolls.add(i);
                }
            }
            if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
        }
        rerolls.clear();

        // Checking diamonds.
        if (myDice.getFaceRoll(Faces.DIAMOND) >= 4) {
            for (int i = 0; i < myDice.getRolls().size(); i++) {
                if (myDice.getRolls().get(i) == Faces.DIAMOND) {
                    rerolls.add(i);
                }
            }
            if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
        }
        rerolls.clear();

        // Checking others, keeping if at least 5.
        for (Faces face : Faces.values()){
            if (face != Faces.SKULL){
                if (myDice.getFaceRoll(face) >= 5) {
                    for (int i = 0; i < myDice.getRolls().size(); i++) {
                        if (myDice.getRolls().get(i) == face) {
                            rerolls.add(i);
                        }
                    }

                    // Adds any gold or diamonds to this if possible.
                    for (int i = 0; i < myDice.getRolls().size(); i++){
                        if (myDice.getRolls().get(i) == Faces.DIAMOND || myDice.getRolls().get(i) == Faces.GOLD){
                            rerolls.add(i);

                            // If adding the gold/diamond puts us over the amount of rerolls we can make, gets rid of it.
                            if (!verifyComboRoll(myDice, rerolls)){
                                rerolls.remove(rerolls.size() - 1);
                                return rerolls;
                            }
                        }
                    }
                    if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
                }
            }
        }
        rerolls.clear();

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
                if (!verifyComboRoll(myDice, rerolls)){
                    rerolls.remove(rerolls.size() - 1);
                    return rerolls;
                }
            }
        }
        if (rerolls.size() > 0){
            if (verifyComboRoll(myDice, rerolls)) { return rerolls;}
        }

        // Final option is returning an empty list if this somehow happens, making it reroll all.
        return rerolls;
    }

    // Decides if the player should keep rolling or stop because they have enough points, true if they should stop.
    // If statements are in order of priority for stopping/continuing rerolling.
    public static boolean comboStopStrategy(Dice myDice, Card card){

        // Returns immediately to stop rolling if more than or equal to 800 points.
        if (calcPoints(myDice, card) >= 800){
            return true;
        }
        // Keep rolling if more than or equal to 600 and no skulls yet.
        if (calcPoints(myDice, card) >= 600 && myDice.getFaceRoll(Faces.SKULL) == 0){
            return false;
        }
        //Stop rolling if 700 points and at least 1 skull.
        if (calcPoints(myDice, card) >= 700 && myDice.getFaceRoll(Faces.SKULL) > 0){
            return true;
        }
        // Keep rolling if less than 300 and 2 skulls.
        if (calcPoints(myDice, card) < 300 && myDice.getFaceRoll(Faces.SKULL) == 2){
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
        if (calcPoints(myDice, card) >= 300 && myDice.getFaceRoll(Faces.SKULL) <= 1){
            return false;
        }

        // If nothing above is true, decide based on if more than 300 points, stopping the roll if yes.
        return calcPoints(myDice, card) > 300;
    }

    // Ensures that the number of dice we want to reroll isn't less than 2.
    public static boolean verifyComboRoll(Dice myDice, ArrayList<Integer> rerolls){
        return myDice.getRolls().size() - rerolls.size() - myDice.getFaceRoll(Faces.SKULL) >= 2;
    }

    // Decides which dice to reroll to win the sea battle.
    public static int comboRoller(Dice myDice, Player player, boolean trace, Logger logger, Card card, int target){

        // Does the first roll for the user.
        if (trace) {logger.info("Player " + player.getName() + " is now rolling...");}
        myDice.resetRolls();
        myDice.rollDice(logger, trace);
        int points = calcPoints(myDice, card);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = myDice.getFaceRoll(Faces.SKULL);
        if (numSkulls > 2){
            if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
            return points;
        }

        if (trace) {
            logger.trace("You have " + points + " points this turn!");
            logger.trace("You rolled " + numSkulls + " skulls!");
        }

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){

            // Ends turn depending on the sea combo player strategy.
            if (comboStopStrategy(myDice, target, card)){
                if (trace) {logger.info("Player " + player.getName() + " has finished rolling!");}
                return points;
            }

            // Rerolls all specified non skull dice.
            myDice.rollComboDice(logger, comboRollStrategy(myDice, target), trace);
            numSkulls = myDice.getFaceRoll(Faces.SKULL);
            points = calcPoints(myDice, card);
            if (numSkulls > 2){
                if (trace) {logger.info("Sorry, your turn is over!! You rolled " + numSkulls + " skulls...");}
                return points;
            }
            else{
                if (trace) {
                    logger.trace("You have " + points + " points this turn!");
                    logger.trace("You rolled " + numSkulls + " skulls!");
                }
            }

        }

    }

    // Decides which dice to reroll to get the best score while hitting the sea battle target.
    public static ArrayList<Integer> comboRollStrategy(Dice myDice, int target){
        ArrayList<Integer> rerolls = new ArrayList<>();

        // First always add enough sabers to hit the target or just as many as possible.
        for (int i = 0; i < myDice.getRolls().size() && rerolls.size() < target; i++){
            if (myDice.getRolls().get(i) == Faces.SABER){
                rerolls.add(i);
            }
        }

        // To be safe, we want to only reroll two dice at a time, and keep diamonds/gold if possible.
        // Therefore, we keep 6 dice, minus the skulls.
        if (myDice.getFaceRoll(Faces.DIAMOND) + myDice.getFaceRoll(Faces.GOLD) > 0) {
            for (int i = 0; i < myDice.getRolls().size(); i++) {
                if (myDice.getRolls().get(i) == Faces.DIAMOND || myDice.getRolls().get(i) == Faces.GOLD){
                    rerolls.add(i);

                    // Returns if we've kept all the dice that we can.
                    if (!verifyComboRoll(myDice, rerolls)){
                        rerolls.remove(rerolls.size() - 1);
                        return rerolls;
                    }
                }
            }
        }

        // Code here is run if not enough gold/diamonds, so choose filler dice to keep so we only reroll 2.
        int diceToFind = myDice.getRolls().size() - rerolls.size() - myDice.getFaceRoll(Faces.SKULL) - 2;
        for (int i = 0; i < myDice.getRolls().size() && diceToFind > 0; i++){
            // Makes sure it's not a skull or something already in the list that we're adding.
            if (myDice.getRolls().get(i) != Faces.SKULL && !rerolls.contains(i)){
                rerolls.add(i);
                diceToFind--;
            }
        }
        return rerolls;

    }

    // Decides if the player should keep rolling or stop because they have enough points, true if they should stop.
    // If statements are in order of priority for stopping/continuing rerolling.
    public static boolean comboStopStrategy(Dice myDice, int target, Card card){

        // If we haven't hit the target, always reroll.
        if (myDice.getFaceRoll(Faces.SABER) < target){
            return false;
        }
        // Returns immediately to stop rolling if more than or equal to 800 points, and we reached the target.
        if (calcPoints(myDice, card) >= 800){
            return true;
        }
        // If no skulls, and we hit the target, try and get some extra points by rerolling, else end turn.
        return myDice.getFaceRoll(Faces.SKULL) != 0;

    }

    // Logic for calculating points of their dice roll.
    public static int calcPoints(Dice myDice, Card card){

        int points = 0;

        // If a sea battle card was drawn, checks if they have enough sabers, returning  if not.
        if (card.getType().equals("Sea Battle")){
            if (myDice.getFaceRoll(Faces.SABER) < Integer.parseInt(card.getModifier()) || myDice.getFaceRoll(Faces.SKULL) > 2){
                // Subtracts appropriate amount of points for not meeting the saber requirement / getting 3+ skulls.
                return switch (card.getModifier()){
                    case "2" -> points = (-300);
                    case "3" -> points = (-500);
                    case "4" -> points = (-1000);
                    default -> points += 0;
                };
            }
            // Adds appropriate amount of points.
            switch (Integer.parseInt(card.getModifier())){
                case 2 -> points = 300;
                case 3 -> points = 500;
                case 4 -> points = 1000;
                default -> points += 0;
            }
        }

        if (myDice.getFaceRoll(Faces.SKULL) > 2) {
            return 0;
        }

        // Basic first logic for diamond and gold points.
        points += 100 * (myDice.getFaceRoll(Faces.DIAMOND) + myDice.getFaceRoll(Faces.GOLD));

        // Checks if monkey business, using proper logic if so.
        if (card.getType().equals("Monkey Business")){
            switch (myDice.getFaceRoll(Faces.MONKEY) + myDice.getFaceRoll(Faces.PARROT)) {
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
        else {
            switch (myDice.getFaceRoll(Faces.PARROT)) {
                case 3 -> points += 100;
                case 4 -> points += 200;
                case 5 -> points += 500;
                case 6 -> points += 1000;
                case 7 -> points += 2000;
                case 8 -> points += 4000;
                default -> {
                }
            }
            switch (myDice.getFaceRoll(Faces.MONKEY)) {
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

        // Adding points for any combos.
        for (Faces face : Faces.values()) {
            if (face == Faces.SKULL || face == Faces.MONKEY || face == Faces.PARROT) {
                continue;
            }
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

        if (checkExtraPoints(myDice, card)){
            points += 500;
        }

        return points;
    }

    // Checks if all dice rolled contribute to scoring points, meaning 500 extra points are awarded.
    private static boolean checkExtraPoints(Dice myDice, Card card){

        if (myDice.getFaceRoll(Faces.SKULL) > 0){
            return false;
        }

        // Checks all non-gold and non-diamond found in the dice rolls, and if they are part of a combo.
        for (Faces face : Faces.values()){
            if (face != Faces.GOLD && face != Faces.DIAMOND && myDice.getRolls().contains(face)){

                // Checks if we're at a saber and sea battle 2 is true because then you don't need 3 to get the treasure.
                if (face == Faces.SABER && card.getName().equals("Sea Battle 2")){
                    // If sea battle 2 is played, only need 2 sabers.
                    if (myDice.getFaceRoll(face) < 2){
                        return false;
                    }
                }
                // Checks if monkey business, since then only need 3 parrots/monkeys combined.
                else if (card.getType().equals("Monkey Business")){
                    if (face == Faces.MONKEY || face == Faces.PARROT){
                        if (myDice.getFaceRoll(Faces.PARROT) + myDice.getFaceRoll(Faces.MONKEY) < 3) {
                            return false;
                        }
                    }
                    else if (myDice.getFaceRoll(face) < 3) {
                            return false;
                    }
                }
                else {
                    // If a face exists in the rolls but is not part of a combo, returns false.
                    if (myDice.getFaceRoll(face) < 3) {
                        return false;
                    }
                }
            }

        }

        return true;
    }


    
}
