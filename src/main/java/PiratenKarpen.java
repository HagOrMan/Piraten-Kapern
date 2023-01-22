import pk.Dice;
import pk.Faces;
import java.util.ArrayList;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");
        Dice myDice = new Dice();
        System.out.println(myDice.roll());
        System.out.println("That's all folks!");
        System.out.println("\nHaha, tricked you!! We're back, rolling 8 dice this time.");
    }

    public static ArrayList<Faces> rollDice(Dice myDice, ArrayList<Faces> rolls){
        for (int i = rolls.size(); i < 8; i++){
            rolls.add(myDice.roll());
            if (i != 7)
                System.out.print(rolls.get(i) + ", ");
            else
                System.out.print(rolls.get(i));
        }
        return rolls;
    }

    public static void takeTurn(Dice myDice, String player){

        System.out.printf("Player $s is now rolling... \n", player);
        ArrayList<Faces> rolls = new ArrayList<>();
        rollDice(myDice, rolls);

    }
    
}
