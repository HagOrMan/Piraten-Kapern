import pk.Dice;
import pk.Faces;
import java.util.ArrayList;
import java.util.Scanner;

public class PiratenKarpen {

    static Scanner ui = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");
        Dice myDice = new Dice();
        System.out.println(myDice.roll());
        System.out.println("That's all folks!");
        System.out.println("\nHaha, tricked you!! We're back, rolling 8 dice this time.");
        takeTurn(myDice, "1");
        takeTurn(myDice, "2");
        takeTurn(myDice, "3");

        ui.close();
    }

    // Rolls dice based on how many rolls are left until all 8 dice have been rolled.
    public static void rollDice(Dice myDice, ArrayList<Faces> rolls){

        for (int i = 0; i < 8; i++){
            if (rolls.size() > i){
                if (rolls.get(i) != Faces.SKULL){
                    rolls.set(i, myDice.roll());
                }
            }
            else{
                rolls.add(myDice.roll());
            }
            if (i != 7){
                System.out.print(rolls.get(i) + ", ");
            }
            else{
                System.out.print(rolls.get(i) + "\n");
            }
        }
    }

    // Allows user to take one turn until they stop rolling dice.
    public static void takeTurn(Dice myDice, String player){

        // Does the first roll for the user.
        System.out.printf("\nPlayer %s is now rolling... \n", player);
        ArrayList<Faces> rolls = new ArrayList<>();
        rollDice(myDice, rolls);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = checkSkulls(rolls);
        if (numSkulls > 2){
            System.out.printf("Sorry, your turn is over!! You rolled %d skulls... \n", numSkulls);
            return;
        }
        
        String choice;

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){
            System.out.println("\nEnter 'y' or 'Y' if you would like to continue rolling, or anything else if you want to end your turn: ");
            choice = ui.nextLine();

            // Ends turn.
            if (!choice.equalsIgnoreCase("y")){
                System.out.printf("Player %s has finished rolling! \n", player);
                return;
            }

            // Rerolls all non skull dice.
            rollDice(myDice, rolls);
            numSkulls = checkSkulls(rolls);
            if (numSkulls > 2){
                System.out.printf("Sorry, your turn is over!! You rolled %d skulls... \n", numSkulls);
                return;
            }
            else{
                System.out.printf("You rolled %d skulls! \n", numSkulls);
            }

        }

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
