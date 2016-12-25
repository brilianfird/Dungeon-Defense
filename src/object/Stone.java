package object;

/**
 * Created by Brilian on 22/12/2016.
 */
public class Stone {
    int price;

    public char getSymbol() {
        return symbol;
    }

    char symbol;

    public int getPrice() {
        return price;
    }

    public Stone(){
        price = 5;
        symbol = 'S';
    }
}
