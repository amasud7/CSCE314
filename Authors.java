public class Authors {
    String name;
    String language;
    String id;
    String bio;
    double version;
    static int countAuthor;

    public Authors(String name, String language, String id, String bio, double version) {
        this.name = name;
        this.language = language;
        this.id = id;
        this.bio = bio;
        this.version = version;
        countAuthor++;
    }
}
