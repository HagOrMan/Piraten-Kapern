package pk;
import java.util.ArrayList;
import java.util.Random;

public class Dice {

    private ArrayList<Faces> rolls;

    public Dice(){
        rolls = new ArrayList<Faces>();
    }

    public Faces roll() {
        int howManyFaces = Faces.values().length;
        // System.out.println("  (DEBUG) there are " + howManyFaces + " faces");
        // System.out.println("  (DEBUG) " + Arrays.toString(Faces.values()));
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    // Rolls dice based on how many rolls are left until all 8 dice have been rolled.
    public void rollDice(){

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
            if (i != 7){
                System.out.print(rolls.get(i) + ", ");
            }
            else{
                System.out.print(rolls.get(i) + "\n");
            }
        }
    }

    public void resetRolls(){
        rolls.clear();
    }
    
    public ArrayList<Faces> getRolls(){ return rolls; }
}
