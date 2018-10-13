import java.sql.*;

public class TestDB {
    public static void main(String args[]) {
        String USER = "root";
        String PASS = "qpalzm";
        String DB_URL = "jdbc:mysql://localhost/neu_javaweb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT ID,NAME,GENDER,JOB,EDUCATION,HOME  FROM customer_info";
            ResultSet rs = stmt.executeQuery(sql);

            //NOT FINISH READING YET!!!!!!!!!!!!!!!!!!!!!!!!


            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String gender = rs.getString("GENDER");
                String job = rs.getString("JOB");
                String edu = rs.getString("EDUCATION");
                String home = rs.getString("HOME");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Name: " + name);
                System.out.print(", Gender: " + gender);
                System.out.println(", job: " + job);
                System.out.print(", edu: " + edu);
                System.out.println(", Home: " + home);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }
}
