import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ShamirSecret {
    public static void main(String[] args) {
        try {
            // Load JSON file
            String content = new String(Files.readAllBytes(Paths.get("testcase.json")));
            JSONObject json = new JSONObject(content);

            // Extract n and k values
            JSONObject keys = json.getJSONObject("keys");
            int n = keys.getInt("n");
            int k = keys.getInt("k");

            // Extract x and y values
            List<BigInteger> xValues = new ArrayList<>();
            List<BigInteger> yValues = new ArrayList<>();

            for (String key : json.keySet()) {
                if (!key.equals("keys")) {
                    JSONObject root = json.getJSONObject(key);
                    int base = root.getInt("base");
                    String value = root.getString("value");

                    // Convert y value from given base
                    BigInteger y = new BigInteger(value, base);
                    BigInteger x = new BigInteger(key);

                    xValues.add(x);
                    yValues.add(y);
                }
            }

            // Find the constant term c using Lagrange Interpolation
            BigInteger secret = lagrangeInterpolation(xValues, yValues, BigInteger.ZERO, k);
            System.out.println("Secret constant term (c): " + secret);

        } catch (IOException | JSONException e) {
            System.out.println("Error reading JSON: " + e.getMessage());
        }
    }

    // Lagrange Interpolation Method to find f(0) (constant term c)
    public static BigInteger lagrangeInterpolation(List<BigInteger> x, List<BigInteger> y, BigInteger target, int k) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < k; i++) {
            BigInteger term = y.get(i);
            BigInteger numerator = BigInteger.ONE;
            BigInteger denominator = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    numerator = numerator.multiply(target.subtract(x.get(j)));
                    denominator = denominator.multiply(x.get(i).subtract(x.get(j)));
                }
            }

            // Compute term = y_i * (Lagrange basis polynomial)
            term = term.multiply(numerator).divide(denominator);
            result = result.add(term);
        }
        return result;
    }
}
