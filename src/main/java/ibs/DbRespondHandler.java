package ibs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class DbRespondHandler {

    Statement statement;
    ResultSet resultSet;
    ResultSetMetaData metaData;

    private DbRespondHandler() {
//        array = executeSQLRequest(sqlRequest);
    }

    private static DbRespondHandler INSTANCE;

    public static DbRespondHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbRespondHandler();
        }
        return INSTANCE;
    }

    public JSONArray executeSQLRequest(String request) {

        JSONArray array = new JSONArray();
        try {
            statement = ConnectionClass.getInstance().getConnectionDB().createStatement();
            resultSet = statement.executeQuery(request);
            metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                JSONObject jobject = new JSONObject();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    convertDataToJson(i, jobject);
                }
                array.add(jobject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionClass.getInstance().close();
        }
        return array;
    }

    public void convertDataToJson(int i, JSONObject jsonObject) throws SQLException {
        switch (metaData.getColumnTypeName(i)) {
            case "INT":
            case "LONG":
            case "BYTE":
                jsonObject.put(metaData.getColumnName(i), resultSet.getLong(i));
                break;
            case "BOOLEAN":
                jsonObject.put(metaData.getColumnName(i), resultSet.getBoolean(i));
                break;
            default:
                jsonObject.put(metaData.getColumnName(i), resultSet.getString(i));
                break;
        }
    }

//
//    public void writeRespondToFile(String fileName) {
//        try (FileWriter fw = new FileWriter(fileName)) {
//            fw.write(array.toJSONString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
