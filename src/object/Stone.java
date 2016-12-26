package object;


public class Stone {
    int price;
    char symbol;

    public Stone() {
        price = 5;
        symbol = 'S';
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPrice() {
        return price;
    }
}
