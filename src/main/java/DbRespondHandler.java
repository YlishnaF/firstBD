import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class DbRespondHandler {
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    ResultSetMetaData metaData;
    JSONArray array;

    public DbRespondHandler(String sqlRequest, Connection connection) {
        this.connection = connection;
        array = executeSQLRequest(sqlRequest);
    }

    private JSONArray executeSQLRequest(String request) {
        JSONArray array = new JSONArray();
        try {
            statement = connection.createStatement();
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

    public JSONArray getArray(){
        return array;
    }

    public void writeRespondToFile(String fileName) {
        try(FileWriter fw = new FileWriter(fileName)) {
            fw.write(array.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
