package pk;

public class Player{
    private int wins;
    private int points;
    private int games;
    private String name;

    public Player(String name){
        wins = 0;
        points = 0;
        games = 0;
        this.name = name;
    }

    public double calcWinPerc(){
        return wins/games;
    }

    public int getPoints(){ return points; }

    public void addPoints(int points){
        this.points += points;
    }

    public String getName(){ return name; }
}