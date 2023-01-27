package pk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dice {

    private ArrayList<Faces> rolls;
    private Map<Faces, Integer> faceRolls;

    public Dice(){
        rolls = new ArrayList<Faces>();
        faceRolls = new HashMap<>();
        for (Faces face: Faces.values()){
            faceRolls.put(face, 0);
        }
    }

    public Faces roll() {
        int howManyFaces = Faces.values().length;
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    // Rolls dice based on how many rolls are left until all 8 dice have been rolled.
    public void rollDice(Logger logger, boolean trace){

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

        if (trace) {logger.trace(diceRolls);}
        updateFaceRolls();

    }

    // Rolls dice based on which indices are specified.
    public void rollComboDice(Logger logger, ArrayList<Integer> keepIndices, boolean trace){
        String diceRerolls = "Rerolled: ";
        // Rerolls any dice not in the specified index to keep.
        for (int i = 0; i < 8; i++) {
            // Rerolls any non skull dice that also aren't in the list of dice to keep.
            if (rolls.get(i) != Faces.SKULL && !keepIndices.contains(i)) {
                diceRerolls += rolls.get(i) + "  ";
                rolls.set(i, roll());
            }
        }

        // Puts all rolls into a string.
        String diceRolls = "";
        for (int i = 0; i < 8; i++){
            diceRolls += rolls.get(i);
            if (i != 7){
                diceRolls += ", ";
            }
        }

        if (trace) { logger.trace("Combo Player " + diceRerolls);   logger.trace(diceRolls);}
        updateFaceRolls();

    }


    public void resetRolls(){ rolls.clear(); }
    
    public ArrayList<Faces> getRolls(){ return rolls; }

    // Updates the number of rolls each face has in the dice rolls.
    private void updateFaceRolls(){
        for (Faces face : Faces.values()){
            faceRolls.replace(face, countFace(face));
        }
    }

    // Counts the number of dice that are of a certain face in the roll.
    private int countFace(Faces face){
        int counter = 0;
        for (Faces roll : rolls){
            if (roll == face){
                counter++;
            }
        }
        return counter;
    }

    public int getFaceRoll(Faces face){ return faceRolls.get(face); }

}
