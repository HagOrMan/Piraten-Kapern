package pk;

public class Games {

    // Simulates 42 games.
    public static void playGames(Dice myDice, Player p1, Player p2, int numGames){
        String winner = "";

        // Simulates 42 games and increments winner counters depending on who won.
        for (int i = 0; i < numGames; i++){
            p1.resetPoints(); p2.resetPoints();
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

        while (p1.getPoints() < 6000 && p2.getPoints() < 6000){

            // Eachs player takes their turn rolling.
            p1.addPoints(Strategies.rerollAll(myDice, p1));
            System.out.printf("\nPlayer 1 has %d points total.\n\n", p1.getPoints());
            p2.addPoints(Strategies.rerollAll(myDice, p2));
            System.out.printf("\nPlayer 2 has %d points total.\n\n", p2.getPoints());

            // Lets p1 go again if p2 got more than 6000 points but p1 hasn't this turn.
            if (p2.getPoints() >= 6000 && p1.getPoints() < 6000){
                p1.addPoints(Strategies.rerollAll(myDice, p1));
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
    
    
}
