package otros;

import model.CreateConnection;

import java.sql.*;

public class CrudOperations {
    static CreateConnection createConn;

    public static void main(String[] args) {
        createConn = new CreateConnection();
        //performCreate("12.10","Test","Insercion de prueba");
        //performRead();
        //System.out.println("-- Final State --");
        //read();
        //makeTransactionExample();
        //insert("12.0","test","sws");
        //read();
        showScrollableResults();
    }

    private static boolean insert(String recipeNumber, String title, String description) {
        String sql = "INSERT INTO RECIPES VALUES(" +
                "null, ?,?,?)";
        /**
         * Cambiamos los Statement objects por PreparedStament objects
         * Statement stmt = conn.createStatement();) {
         */
        try (Connection conn = createConn.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, recipeNumber);
                pstmt.setString(2, title);
                pstmt.setString(3, description);
                int result = pstmt.executeUpdate();
                // Returns row-count or 0 if not successful
                if (result == 1) {
                    System.out.println("-- Record created --");
                    return true;
                } else {
                    System.err.println("!! Record NOT Created !!");
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static void read() {
        String qry = "select recipe_number, recipe_name, description from apressbooks.recipes";
        try (Connection conn = createConn.getConnection();
             Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(qry);
            while (rs.next()) {
                String recipe = rs.getString("RECIPE_NUMBER");
                String name = rs.getString("RECIPE_NAME");
                String desc = rs.getString("DESCRIPTION");
                System.out.println(recipe + "\t" + name + "\t" + desc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean update(String recipeNumber, String title, String description) {
        String sql = "UPDATE RECIPES " +
                "SET RECIPE_NUMBER = '12-5' " +
                "WHERE RECIPE_NUMBER = '12-4'";
        try (Connection conn = createConn.getConnection();
             Statement stmt = conn.createStatement();) {
            int result = stmt.executeUpdate(sql);
            if (result == 1) {
                System.out.println("-- Record Updated --");
                return true;
            } else {
                System.out.println("!! Record NOT Updated !!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean delete(String recipeNumber) {
        //TO-DO Preguntar si conviene dejar asi el delete o si deberiamos usar un PreparedStament
        String sql = "DELETE FROM RECIPES WHERE RECIPE_NUMBER = '" + recipeNumber + "'";
        try (Connection conn = createConn.getConnection();
             Statement stmt = conn.createStatement();) {
            int result = stmt.executeUpdate(sql);
            if (result > 0) {
                System.out.println("-- Record Deleted --");
                return true;
            } else {
                System.out.println("!! Record NOT Deleted!!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void makeTransactionExample() {
        boolean successFlag = false;
        try {
            Connection conn = createConn.getConnection();
            conn.setAutoCommit(false);
            read();
            successFlag = insert("12.11", "TEST", "test stament");
            if (successFlag) {
                successFlag = insert(
                        "12.12",
                        "TEST",
                        "test statement"
                );
            }

            if (successFlag) {
                conn.commit();
            } else {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    private static void showScrollableResults() {
        String sql = "SELECT ID, RECIPE_NUMBER, RECIPE_NAME, DESCRIPTION " +
                "FROM RECIPES";
        try (Connection conn = createConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pstmt.executeQuery()) {
            rs.first();
            System.out.println(rs.getString(2) + ": " + rs.getString(3) +
                    " - " + rs.getString(4));
            rs.next();
            System.out.println(rs.getString(2) + ": " + rs.getString(3) +
                    " - " + rs.getString(4));
            rs.previous();
            System.out.println(rs.getString(2) + ": " + rs.getString(3) +
                    " - " + rs.getString(4));
            rs.last();
            System.out.println(rs.getString(2) + ": " + rs.getString(3) +
                    " - " + rs.getString(4));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void queryAndUpdate(String recipeNumber) {
        String sql = "SELECT ID, RECIPE_NUMBER, RECIPE_NAME, DESCRIPTION " +
                "FROM RECIPES " +
                "WHERE RECIPE_NUMBER = ?";
        ResultSet rs = null;
        try (Connection conn = createConn.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);) {
            pstmt.setString(1, recipeNumber);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String desc = rs.getString(4);
                System.out.println("Updating row" + desc);
                rs.updateString(4, desc + " -- More to come");
                rs.updateRow();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }


}