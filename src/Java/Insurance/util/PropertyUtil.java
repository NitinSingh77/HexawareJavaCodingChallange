package Java.Insurance.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertyUtil {
	public static String getConnectionString(String fileName) {
		Properties props= new Properties();
		try(FileInputStream fis= new FileInputStream(fileName)){
			props.load(fis);
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
	String url= props.getProperty("db.url");
	String username = props.getProperty("db.username");
    String password = props.getProperty("db.password");

    return url + "?user=" + username + "&password=" + password;
	}
}
