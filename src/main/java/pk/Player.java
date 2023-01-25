package pk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player{
    private int wins;
    private int points;
    private int games;
    private String name, strategy;

    public Player(String name, String strategy){
        wins = 0;
        points = 0;
        games = 0;
        this.name = name;
        this.strategy = strategy;
    }

    public double calcWinPerc(){ return (double)wins/games; }

    public int getPoints(){ return points; }

    public void addPoints(int points){ this.points += points; }

    public void resetPoints(){ points = 0; }

    public String getName(){ return name; }

    public void won(){ wins++; }
    public void playedGame(){ games++; }

    public int roll(Dice myDice, boolean trace, Logger logger){
        if (strategy.equals("random")){
            return Strategies.randomRoller(myDice, this, trace, logger);
        }
        else if (strategy.equals("combo")){
            return Strategies.comboRoller(myDice, this, trace, logger);
        }
        else{
            return 0;
        }
    }
}