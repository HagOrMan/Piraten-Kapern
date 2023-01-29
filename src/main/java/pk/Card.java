package pk;

public class Card {

    private String name;
    private String modifier;
    private String type;

    public Card(String name){
        this.name = name;
        modifier = decideModifier();
        type = decideType();
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

    private String decideType(){
        return switch (name) {
            case "Sea Battle 2", "Sea Battle 3", "Sea Battle 4" -> "Sea Battle";
            case "Monkey Business" -> "Monkey Business";
            default -> "nop";
        };
    }

    public String getModifier(){ return modifier; }

    public String getType(){ return type; }

}
