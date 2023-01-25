package pk;
import java.util.ArrayList;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dice {

    private ArrayList<Faces> rolls;

    public Dice(){
        rolls = new ArrayList<Faces>();
    }

    public Faces roll() {
        int howManyFaces = Faces.values().length;
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    // Rolls dice based on how many rolls are left until all 8 dice have been rolled.
    public void rollDice(Logger logger){

        String diceRolls = "";
        for (int i = 0; i < 8; i++){
            // Checks to make sure that the arraylist is not full.
            if (rolls.size() > i){
                // Rerolls any non skull dice.
                if (rolls.get(i) != Faces.SKULL){
                    rolls.set(i, roll());
                }
            }
            // Rolls new dice and adds to arraylist if the list is not full yet.
            else{
                rolls.add(roll());
            }

            diceRolls += rolls.get(i);
            if (i != 7){
                diceRolls += ", ";
            }
        }

        logger.trace(diceRolls);

    }

    public void resetRolls(){ rolls.clear(); }
    
    public ArrayList<Faces> getRolls(){ return rolls; }
}
