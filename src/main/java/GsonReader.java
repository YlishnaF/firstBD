import com.google.gson.Gson;
import model.Root;

import java.io.FileReader;

public class GsonReader {
    public static void main(String[] args) {
        try (FileReader fr = new FileReader("src/main/resources/peopleAndPets.json")) {
            Gson gson = new Gson();
            Root root = gson.fromJson(fr, Root.class);
            System.out.println(root.toString());
        } catch (Exception ex) {
            System.out.println("Parsing failed...");
            System.out.println(ex);
        }
    }
}
