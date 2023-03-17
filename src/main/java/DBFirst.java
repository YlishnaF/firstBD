
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DBFirst {
    static File dbFile = new File("src/main/resources/db.properties");
    static Properties properties = new Properties();


    public static void main(String[] args) {
        try {

            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();
            array = executeSQLRequest("SELECT * FROM peopleandpets.people");
            System.out.println(array);

            for (int i = 0; i < array.size(); i++) {
                JSONObject object1 = (JSONObject) array.get(i);
                if ((object1.getOrDefault("name", "")).equals("Иванова С.К.")) {
                    System.out.println(object1);
                    Assert.assertEquals(object1.getOrDefault("birthday", ""), "1984-05-22");
                }
            }
//            try (Connection conn = DriverManager.getConnection(url, username, password)){
//                Statement statement = conn.createStatement();
//                ResultSet resultSet = statement.executeQuery("SELECT peopleandpets.people.name, peopleandpets.people.location, peopleandpets.people.birthday, peopleandpets.animals.pet_name, peopleandpets.animals.type,peopleandpets.animals.pet_birthday\n" +
//                        "from peopleandpets.people\n" +
//                        "join peopleandpets.peopleanimals on peopleandpets.people.id=peopleandpets.peopleanimals.id_people\n" +
//                        "join peopleandpets.animals on peopleandpets.peopleanimals.id_animal=peopleandpets.animals.id");
//
//                while(resultSet.next()){
//                    JSONObject jobject= new JSONObject();
//                    jobject.put("name",  resultSet.getString("name"));
//                    jobject.put("location",  resultSet.getString("location"));
//                    jobject.put("birthday",  resultSet.getDate("birthday").toString());
//
//                    JSONArray arrayPet= new JSONArray();
//                    JSONObject jobjectPet= new JSONObject();
//                    jobjectPet.put("pet_name",  resultSet.getString("pet_name"));
//                    jobjectPet.put("type",  resultSet.getString("type"));
//                    jobjectPet.put("pet_birthday",  resultSet.getDate("pet_birthday").toString());
//                    arrayPet.add(jobjectPet);
//                    jobject.put("pet",arrayPet);
//                    array.add(jobject);
//                }
//                object.put("people", array);
//                FileWriter fw =new FileWriter("src/main/resources/peopleAndPets.json");
//                fw.write(object.toJSONString());
//                fw.close();
//            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }


    }

    public static JSONArray executeSQLRequest(String request) throws SQLException {
        JSONArray array = new JSONArray();
        try {
            properties.load(new FileReader(dbFile));
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = properties.getProperty("url");
            String username = properties.getProperty("login");
            String password = properties.getProperty("password");
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(request);
//            структура столбцов
            ResultSetMetaData metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                JSONObject jobject = new JSONObject();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.println("type " + metaData.getColumnType(i));
                    System.out.println("type name " + metaData.getColumnTypeName(i));
//                    jobject.put(metaData.getColumnName(i), resultSet.getString(i));
                    convertDataToJson(resultSet, metaData,i ,jobject );
//                    switch (metaData.getColumnTypeName(i)){
//                        case "INT":
//                        case "LONG":
//                        case "BYTE":
//                            resultSet.getLong(i);
//                            resultSet.
//
//                    }
                    System.out.println(i);
                    System.out.println("columnName: " + metaData.getColumnName(i) + " valueColumn " + resultSet.getString(i));
                }
                array.add(jobject);
//                System.out.println(resultSet.);
            }
// jwaypass (аналог xml pass)!
//                while(resultSet.next()){
//                    JSONObject jobject= new JSONObject();
//                    jobject.put("name",  resultSet.getString("name"));
//                    jobject.put("location",  resultSet.getString("location"));
//                    jobject.put("birthday",  resultSet.getDate("birthday").toString());
//
//                    JSONArray arrayPet= new JSONArray();
//                    JSONObject jobjectPet= new JSONObject();
//                    jobjectPet.put("pet_name",  resultSet.getString("pet_name"));
//                    jobjectPet.put("type",  resultSet.getString("type"));
//                    jobjectPet.put("pet_birthday",  resultSet.getDate("pet_birthday").toString());
//                    arrayPet.add(jobjectPet);
//                    jobject.put("pet",arrayPet);
//                    array.add(jobject);
//                }
//                object.put("people", array);
//                FileWriter fw =new FileWriter("src/main/resources/peopleAndPets.json");
//                fw.write(object.toJSONString());
//                fw.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void convertDataToJson(ResultSet resultSet, ResultSetMetaData metaData, int i, JSONObject jsonObject) throws SQLException {

        switch (metaData.getColumnTypeName(i)) {
            case "INT":
            case "LONG":
            case "BYTE":
                jsonObject.put(metaData.getColumnName(i), resultSet.getLong(i));
                break;
            case "BOOLEAN":
                jsonObject.put(metaData.getColumnName(i), resultSet.getBoolean(i));
            default:
                jsonObject.put(metaData.getColumnName(i), resultSet.getString(i));

        }

    }

}