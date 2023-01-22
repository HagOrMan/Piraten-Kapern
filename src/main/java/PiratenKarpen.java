import pk.Dice;
import pk.Faces;
import java.util.ArrayList;
import java.util.Collections;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");
        Dice myDice = new Dice();
        System.out.println(myDice.roll());
        System.out.println("That's all folks!");
        System.out.println("\nHaha, tricked you!! We're back, rolling 8 dice this time.");
    }

    // Rolls dice based on how many rolls are left until all 8 dice have been rolled.
    public static void rollDice(Dice myDice, ArrayList<Faces> rolls){
        for (int i = rolls.size(); i < 8; i++){
            rolls.add(myDice.roll());
            if (i != 7)
                System.out.print(rolls.get(i) + ", ");
            else
                System.out.print(rolls.get(i));
        }
    }

    // Allows user to take one turn until they stop rolling dice.
    public static void takeTurn(Dice myDice, String player){

        // Does the first roll for the user.
        System.out.printf("Player %s is now rolling... \n", player);
        ArrayList<Faces> rolls = new ArrayList<>();
        rollDice(myDice, rolls);

        // Checks how many skulls there are.
        int numSkulls = checkSkulls(rolls);
        if (numSkulls > 2){
            System.out.printf("Sorry, your turn is over!! You rolled %d skulls... \n", numSkulls);
        }

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.


    }

    // Returns the number of skulls in the list.
    public static int checkSkulls(ArrayList<Faces> rolls){
        int counter = 0;
        for (int i = 0; i < rolls.size(); i++){
            if (rolls.get(i) == Faces.SKULL)
                counter++;
        }
        return counter;
    }
    
}
