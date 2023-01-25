package pk;
import java.util.ArrayList;
import java.util.Random;

public class Strategies {

    // Allows user to take one turn until they stop rolling dice, while rerolling all non skull dice a random amount of times.
    public static int rerollAll(Dice myDice, Player player){

        // Does the first roll for the user.
        System.out.printf("\nPlayer %s is now rolling... \n", player.getName());
        myDice.resetRolls();
        myDice.rollDice();

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = countFace(myDice.getRolls(), Faces.SKULL);
        if (numSkulls > 2){
            System.out.printf("Sorry, your turn is over!! You rolled %d skulls... \n", numSkulls);
            return 0;
        }
        
        int choice;
        int points = 100 * (countFace(myDice.getRolls(), Faces.DIAMOND) + countFace(myDice.getRolls(), Faces.GOLD));
        System.out.printf("You have %d points this turn! \n", points);
        System.out.printf("You rolled %d skulls! \n", numSkulls);

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
                System.out.printf("Player %s has finished rolling! \n", player.getName());
                return points;
            }

            // Rerolls all non skull dice.
            myDice.rollDice();
            numSkulls = countFace(myDice.getRolls(), Faces.SKULL);
            points = calcPoints(myDice);
            if (numSkulls > 2){
                System.out.printf("Sorry, your turn is over!! You rolled %d skulls... \n", numSkulls);
                return 0;
            }
            else{
                System.out.printf("You have %d points this turn! \n", points);
                System.out.printf("You rolled %d skulls! \n", numSkulls);
            }

        }

    }

    
    // Returns the number of a certain face in the list.
    public static int countFace(ArrayList<Faces> rolls, Faces face){
        int counter = 0;
        for (int i = 0; i < rolls.size(); i++){
            if (rolls.get(i) == face)
                counter++;
        }
        return counter;
    }

    // Logic for calculating points of a dice roll.
    public static int calcPoints(Dice myDice){
        return 100 * (countFace(myDice.getRolls(), Faces.DIAMOND) + countFace(myDice.getRolls(), Faces.GOLD));
    }

    
}
