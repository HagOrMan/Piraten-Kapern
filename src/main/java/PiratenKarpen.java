import pk.Dice;
import pk.Faces;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PiratenKarpen {

    static Scanner ui = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");
        Dice myDice = new Dice();
        System.out.println(myDice.roll());
        System.out.println("That's all folks!");
        System.out.println("\nHaha, tricked you!! We're back, rolling 8 dice this time.");
        
        playGames(myDice);

        ui.close();
    }

    // Simulates 42 games.
    public static void playGames(Dice myDice){
        String winner = "";
        int p1Wins = 0, p2Wins = 0;

        // Simulates 42 games and increments winner counters depending on who won.
        for (int i = 0; i < 42; i++){
            winner = playOneGame(myDice);
            if (winner.equals("1")){
                p1Wins++;
            }
            else if (winner.equals("2")){
                p2Wins++;
            }
        }

        // Prints all win percentages.
        System.out.println("\n\n\nAll 42 games are now done!");
        System.out.printf("Player 1 Win Percentage: %.2f \n", p1Wins / 42.0);
        System.out.printf("Player 2 Win Percentage: %.2f \n", p2Wins / 42.0);
        
    }

    // Plays one game of piraten karpen.
    public static String playOneGame(Dice myDice){

        int p1Points = 0, p2Points = 0;

        while (p1Points < 6000 && p2Points < 6000){

            // Eachs player takes their turn rolling.
            p1Points += takeTurn(myDice, "1");
            System.out.printf("\nPlayer 1 has %d points total.\n\n", p1Points);
            p2Points += takeTurn(myDice, "2");
            System.out.printf("\nPlayer 2 has %d points total.\n\n", p2Points);

            // Lets p1 go again if p2 got more than 6000 points.
            if (p2Points >= 6000){
                p1Points += takeTurn(myDice, "1");
                System.out.printf("\nPlayer 1 has %d points total.\n\n", p1Points);
            }

        }

        System.out.printf("\n\nThe game is now over. \nPlayer 1 Points: %d \nPlayer 2 Points: %d\n", p1Points, p2Points);
        // Declares the winner of the game.
        if (p1Points > p2Points){
            System.out.println("Player 1 wins!!!");
            return "1";
        }
        else if (p1Points < p2Points){
            System.out.println("Player 2 wins!!!");
            return "2";
        }
        else{
            System.out.println("The game is a tie!!!");
            return "";
        }

    }

    // Rolls dice based on how many rolls are left until all 8 dice have been rolled.
    public static void rollDice(Dice myDice, ArrayList<Faces> rolls){

        for (int i = 0; i < 8; i++){
            // Checks to make sure that the arraylist is not full.
            if (rolls.size() > i){
                // Rerolls any non skull dice.
                if (rolls.get(i) != Faces.SKULL){
                    rolls.set(i, myDice.roll());
                }
            }
            // Rolls new dice and adds to arraylist if the list is not full yet.
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
    public static int takeTurn(Dice myDice, String player){

        // Does the first roll for the user.
        System.out.printf("\nPlayer %s is now rolling... \n", player);
        ArrayList<Faces> rolls = new ArrayList<>();
        rollDice(myDice, rolls);

        // Checks how many skulls there are, exits method if 3 or more.
        int numSkulls = countFace(rolls, Faces.SKULL);
        if (numSkulls > 2){
            System.out.printf("Sorry, your turn is over!! You rolled %d skulls... \n", numSkulls);
            return 0;
        }
        
        String choice;
        int points = 100 * (countFace(rolls, Faces.DIAMOND) + countFace(rolls, Faces.GOLD));
        System.out.printf("You have %d points this turn! \n", points);
        System.out.printf("You rolled %d skulls! \n", numSkulls);

        // Comment out later, keep for simulation.
        Random bag = new Random();

        // Lets the user keep rolling until satisfied or 3 skulls have been gotten.
        while (true){
            /**
                Commented out user input to aid in 42 game simulation.
             */ 
            // System.out.println("\nEnter 'y' or 'Y' if you would like to continue rolling, or anything else if you want to end your turn: ");
            // choice = ui.nextLine();

            if (bag.nextInt(2) == 0){
                choice = "y";
            }
            else{
                choice = "";
            }

            // Ends turn.
            if (!choice.equalsIgnoreCase("y")){
                System.out.printf("Player %s has finished rolling! \n", player);
                return points;
            }

            // Rerolls all non skull dice.
            rollDice(myDice, rolls);
            numSkulls = countFace(rolls, Faces.SKULL);
            points = 100 * (countFace(rolls, Faces.DIAMOND) + countFace(rolls, Faces.GOLD));
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

    // Returns the number of skulls in the list.
    public static int countFace(ArrayList<Faces> rolls, Faces face){
        int counter = 0;
        for (int i = 0; i < rolls.size(); i++){
            if (rolls.get(i) == face)
                counter++;
        }
        return counter;
    }
    
}
