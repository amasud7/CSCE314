public class MyDominoes {
    public static String[] buildSet(int size) {
        int tiles = (size * size + 3 * size + 2) / 2;
        String[] set = new String[tiles];

        int count = 0;
        for (int i = 0; i <= size; i++) {
            for (int j = i; j <= size; j++) {
                set[count++] = (i + " | " + j + " ");
            }
        }

        return set;
    }
}
