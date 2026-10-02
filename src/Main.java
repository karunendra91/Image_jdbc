import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "root";
        String password = "root";
        String image_path = "E:\\image\\img1.jpg";
        String INSERT_DB = "INSERT INTO image_table(image_data) VALUES(?)";
        String folder_path="E:\\image\\";
        String INSERT_FILE="select image_data from image_table where image_id =(?)";


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Driver loaded Successfully....");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

           /*=========================insert img folder to db============================*/
        /*
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected Successfully....");
            FileInputStream fis = new FileInputStream(image_path);
            byte[] image = new byte[fis.available()];
            fis.read(image);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DB);
            preparedStatement.setBytes(1, image);
            int res = preparedStatement.executeUpdate();

            if (res > 0) {
                System.out.println("Insertion success...");
            } else {
                System.out.println("Insertion failed...");
            }
            fis.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

         */
        /*=========================END============================*/
        /*============================db to folder insert img===================*/
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected Successfully....");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILE);
            preparedStatement.setInt(1, 1);
            ResultSet resultSet =preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] data= resultSet.getBytes("image_data");
                String image_pat =folder_path+"extracted_img.jpg";
                OutputStream outputStream = new FileOutputStream(image_pat);
                outputStream.write(data);
            } else {
                System.out.println("image not  found...");
            }

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /*=============================END======================================*/
    }
}