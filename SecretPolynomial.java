import org.json.JSONObject;  // Import JSON library
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.math.BigInteger;

public class SecretPolynomial {
    public static void main(String[] args) {
        try {
            // Read JSON file
            String jsonContent = new String(Files.readAllBytes(Paths.get("testcase.json")));  
            JSONObject jsonObject = new JSONObject(jsonContent);
            
            // Extracting keys
            JSONObject keys = jsonObject.getJSONObject("keys");
            int n = keys.getInt("n");  // Total roots given
            int k = keys.getInt("k");  // Minimum roots required

            // Extract first two root values
            JSONObject firstRoot = jsonObject.getJSONObject("1");
            int x1 = 1;
            int y1 = Integer.parseInt(firstRoot.getString("value"), Integer.parseInt(firstRoot.getString("base")));

            JSONObject secondRoot = jsonObject.getJSONObject("2");
            int x2 = 2;
            int y2 = Integer.parseInt(secondRoot.getString("value"), Integer.parseInt(secondRoot.getString("base")));

            // Solve for the constant term 'c' (simplified for linear case)
            int c = y1 - ((y2 - y1) / (x2 - x1)) * x1;

            // Output the secret
            System.out.println("Constant Term (Secret c): " + c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
