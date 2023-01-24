import pk.Dice;
import pk.Faces;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PiratenKarpen {

    static Scanner ui = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        Dice myDice = new Dice();
        
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

    
    // Allows user to take one turn until they stop rolling dice.
    public static int takeTurn(Dice myDice, String player){

        // Does the first roll for the user.
        System.out.printf("\nPlayer %s is now rolling... \n", player);
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

            if (bag.nextInt(2) == 0){
                choice = 0;
            }
            else{
                choice = 1;
            }

            // Ends turn.
            if (choice == 0){
                System.out.printf("Player %s has finished rolling! \n", player);
                return points;
            }

            // Rerolls all non skull dice.
            myDice.rollDice();
            numSkulls = countFace(myDice.getRolls(), Faces.SKULL);
            points = 100 * (countFace(myDice.getRolls(), Faces.DIAMOND) + countFace(myDice.getRolls(), Faces.GOLD));
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
