public class myDice {

    public static int roll() {
        return (int) (Math.random() * 6) + 1;
    }

    public static int roll(int num) {
        return (int) (Math.random() * num) + 1;
    }
}
