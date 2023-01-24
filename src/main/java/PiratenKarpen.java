import pk.Dice;
import pk.Faces;
import pk.Player;
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
        Player p1 = new Player("1"), p2 = new Player("2");

        // Simulates 42 games and increments winner counters depending on who won.
        for (int i = 0; i < 42; i++){
            winner = playOneGame(myDice, p1, p2);
            if (winner.equals(p1.getName())){
                p1.won();
            }
            else if (winner.equals(p2.getName())){
                p2.won();
            }
        }

        // Prints all win percentages.
        System.out.println("\n\n\nAll 42 games are now done!");
        System.out.printf("Player 1 Win Percentage: %.2f \n", p1.calcWinPerc());
        System.out.printf("Player 2 Win Percentage: %.2f \n", p2.calcWinPerc());
        
    }

    // Plays one game of piraten karpen.
    public static String playOneGame(Dice myDice, Player p1, Player p2){

        p1.resetPoints(); p2.resetPoints();

        while (p1.getPoints() < 6000 && p2.getPoints() < 6000){

            // Eachs player takes their turn rolling.
            p1.addPoints(takeTurn(myDice, p1));
            System.out.printf("\nPlayer 1 has %d points total.\n\n", p1.getPoints());
            p2.addPoints(takeTurn(myDice, p2));
            System.out.printf("\nPlayer 2 has %d points total.\n\n", p2.getPoints());

            // Lets p1 go again if p2 got more than 6000 points.
            if (p2.getPoints() >= 6000){
                p1.addPoints(takeTurn(myDice, p1));
                System.out.printf("\nPlayer 1 has %d points total.\n\n", p1.getPoints());
            }

        }

        System.out.printf("\n\nThe game is now over. \nPlayer 1 Points: %d \nPlayer 2 Points: %d\n", p1.getPoints(), p2.getPoints());
        p1.playedGame(); p2.playedGame();
        // Declares the winner of the game.
        if (p1.getPoints() > p2.getPoints()){
            System.out.printf("Player %s wins!!!\n", p1.getName());
            return p1.getName();
        }
        else if (p1.getPoints() < p2.getPoints()){
            System.out.printf("Player %s wins!!!\n", p2.getName());
            return p2.getName();
        }
        else{
            System.out.println("The game is a tie!!!");
            return "";
        }

    }

    
    // Allows user to take one turn until they stop rolling dice.
    public static int takeTurn(Dice myDice, Player player){

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
