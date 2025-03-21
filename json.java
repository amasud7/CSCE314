import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/*
 * Robert Lightfoot
 * CSCE 314
 * In Class starter code
 * spring 2025
 * 
 * Your name: Ayad Masud
 * Your UIN: 733009045
 */

 /*
  * The following starter code reads in a json file of people with other attributes.
  * Then, it prints them to the screen.
  * Your goal is to replace the printing each record to the screen with adding them to an
  * array list of authors.
  * Authors should be a class type with attributes: name, language, id, bio, version. There should 
  * also be counter of how many authors.
  * Next, you will print out a summary, showing how many authors you have.
  * finally, create a list of unique languages, print out the languages, and the number of languages.
  */
public class json {
    public static void main(String[] args) {
        String filename = "bios.txt";
        ArrayList<Authors> authors = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line.trim()).append(" ");
            }
            
            String json = jsonContent.toString().trim();
            json = json.substring(json.indexOf("[") + 1, json.lastIndexOf("]")).trim();
            String[] records = json.split("},");
            
            for (String record : records) {
                record = record.replace("{", "").replace("}", "").trim();
                String[] pairs = record.split(",");
                String name = "", language = "", id = "", bio = "";
                double version = 0.0;
                
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":", 2);
                    if (keyValue.length < 2) continue;
                    
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim().replace("\"", "");
                    
                    switch (key) {
                        case "name": name = value; break;
                        case "language": language = value; break;
                        case "id": id = value; break;
                        case "bio": bio = value; break;
                        case "version": 
                            try {
                                version = Double.parseDouble(value);
                            } catch (NumberFormatException e) {
                                System.err.println("Warning: Invalid version number for record: " + name);
                                version = -1.0; // Default or error value
                            }
                            break;
                    }
                }

                // To Do: convert this to building instances of an Author in a class called Author.java
                // then comment out this printing, print out the number of authors, a list of unique languages, and the number of languages.
                authors.add(new Authors(name, language, id, bio, version));
        

                // System.out.println("Name: " + name);
                // System.out.println("Language: " + language);
                // System.out.println("ID: " + id);
                // System.out.println("Bio: " + bio);
                // System.out.println("Version: " + version);
                // System.out.println("----------------------------");
            }
            System.out.println("Number of authors: " + Authors.countAuthor);

            // create set
            Set<String> languages = new HashSet<>();
            for (Authors author : authors) {
                languages.add(author.language);
            }

            System.out.println("Here are the langauges spoken: " + languages);
            System.out.println("Number of unique languages: " + languages.size());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
