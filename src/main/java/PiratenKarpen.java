import pk.Dice;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");
        Dice myDice = new Dice();
        System.out.println(myDice.roll());
        System.out.println("That's all folks!");
        System.out.println("\nHaha, tricked you!! We're back, rolling 8 dice this time.");
        roll8Dice(myDice);
    }

    public static void roll8Dice(Dice myDice){
        for (int i = 0; i < 8; i++){
            if (i != 7)
                System.out.print(myDice.roll() + ", ");
            else
                System.out.print(myDice.roll());
        }
    }
    
}
