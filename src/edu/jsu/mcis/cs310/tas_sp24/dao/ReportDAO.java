package edu.jsu.mcis.cs310.tas_sp24.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    private final Connection conn;

    public ReportDAO(Connection conn) {
        this.conn = conn;
    }

    public JsonArray getBadgeSummary(Integer departmentId) throws SQLException, IOException {
        String query = "SELECT b.badgeid, CONCAT(e.lastname, ', ', e.firstname, ' ', e.middlename) AS name, d.name AS department, "
                + "CASE WHEN b.type = 'T' THEN 'Temporary Employee' ELSE 'Full-Time Employee' END AS type "
                + "FROM badge b "
                + "JOIN employee e ON b.employeeid = e.employeeid "
                + "JOIN department d ON b.departmentid = d.departmentid ";

        List<JsonObject> jsonObjects = new ArrayList<>();

        if (departmentId != null) {
            query += "WHERE b.departmentid = ? ";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, departmentId);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.put("badgeid", resultSet.getString("badgeid"));
                    jsonObject.put("name", resultSet.getString("name"));
                    jsonObject.put("department", resultSet.getString("department"));
                    jsonObject.put("type", resultSet.getString("type"));

                    jsonObjects.add(jsonObject);
                }
            }
        } else {
            query += "ORDER BY name";

            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.put("badgeid", resultSet.getString("badgeid"));
                    jsonObject.put("name", resultSet.getString("name"));
                    jsonObject.put("department", resultSet.getString("department"));
                    jsonObject.put("type", resultSet.getString("type"));

                    jsonObjects.add(jsonObject);
                }
            }
        }

        JsonArray jsonArray = new JsonArray();
        jsonArray.addAll(jsonObjects);

        return jsonArray;
    }
}