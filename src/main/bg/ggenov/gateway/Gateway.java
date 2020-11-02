package bg.ggenov.gateway;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gateway {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter your payment command (payment -auth <auth config> -body <request body> -response <response code>) or EXIT");
            String line = scanner.nextLine();
            if (line == null || line.contains("exit") || line.contains("EXIT")) {
                System.out.println("Thank you. Bye bye!!!");
                return;
            }

            // Parse the input and check the parameters validity
            Pattern pattern = Pattern.compile("payment -auth ([a-zA-Z0-9._]+) -body ([a-zA-Z0-9._]+) -response ([0-9]{3})");
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                System.err.println("Invalid command, please try again!\n");
                continue;
            }

            // Execute the payment
            try {
                String auth = getFileContent("src/resources/" + matcher.group(1));
                String body = getFileContent("src/resources/" + matcher.group(2));
                int responseCode = Integer.parseInt(matcher.group(3));

                // Check the test result
                int paymentResp = makePayment(auth, body);
                assert paymentResp == responseCode;
            } catch (IOException e) {
                System.err.println("Invalid command parameters, please try again!\n");
            }
        }
    }

    private static String getFileContent(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }

    public static int makePayment(String auth, String body) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:3001/payment_transactions");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth);
        httpPost.setEntity(new StringEntity(body));

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        return response.getStatusLine().getStatusCode();
    }
}