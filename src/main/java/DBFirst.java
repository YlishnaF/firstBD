
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.Properties;

public class DBFirst {

    public static void main(String[] args) {
        try{
            File dbFile = new File("src/main/resources/db.properties");
            Properties properties = new Properties();
            properties.load(new FileReader(dbFile));
            String url = properties.getProperty("url");
            String username = properties.getProperty("login");;
            String password = properties.getProperty("password");;
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            JSONObject object= new JSONObject();
            JSONArray array= new JSONArray();

            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT peopleandpets.people.name, peopleandpets.people.location, peopleandpets.people.birthday, peopleandpets.animals.pet_name, peopleandpets.animals.type,peopleandpets.animals.pet_birthday\n" +
                        "from peopleandpets.people\n" +
                        "join peopleandpets.peopleanimals on peopleandpets.people.id=peopleandpets.peopleanimals.id_people\n" +
                        "join peopleandpets.animals on peopleandpets.peopleanimals.id_animal=peopleandpets.animals.id");
                while(resultSet.next()){
                    JSONObject jobject= new JSONObject();
                    jobject.put("name",  resultSet.getString("name"));
                    jobject.put("location",  resultSet.getString("location"));
                    jobject.put("birthday",  resultSet.getDate("birthday").toString());
                    jobject.put("pet_name",  resultSet.getString("pet_name"));
                    jobject.put("type",  resultSet.getString("type"));
                    jobject.put("pet_birthday",  resultSet.getDate("pet_birthday").toString());
                    array.add(jobject);
                }
                object.put("People_and_animals", array);
                FileWriter fw =new FileWriter("src/main/resources/peopleAndPets.json");
                fw.write(object.toJSONString());
                fw.close();
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }
}