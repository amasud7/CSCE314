public class games {
    public static void main (String[] args) { // basically like main function
        String[] dominoes;
        dominoes = MyDominoes.buildSet(10);

        for (String tile : dominoes) {
            System.out.println(tile);
        }
    }
}
