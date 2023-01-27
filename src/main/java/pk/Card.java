package pk;

public class Card {

    private String name;
    private String modifier;

    public Card(String name){
        this.name = name;
        modifier = decideModifier();
    }

    public String getName(){ return name; }

    // Decides which modifier the card gets.
    private String decideModifier(){
        return switch (name) {
            case "Sea Battle 2" -> "2";
            case "Sea Battle 3" -> "3";
            case "Sea Battle 4" -> "4";
            default -> "nop";
        };
    }

    public String getModifier(){ return modifier; }

}
