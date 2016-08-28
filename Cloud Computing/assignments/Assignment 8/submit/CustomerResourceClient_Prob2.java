package com.rest.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerResourceClient_Prob2{
	
	public static void main(String[] args) {
		try {
			System.out.println("*** Create a new Customer ***");
			// Create a new customer
			String newCustomer = "<customer>" + "<first-name>Bill</first-name>" + "<last-name>Burke</last-name>"
					+ "<street>256 Clarendon Street</street>" + "<city>Boston</city>" + "<state>MA</state>"
					+ "<zip>02115</zip>" + "<country>USA</country>" + "</customer>";

			URL postUrl = new URL("http://localhost:8080/rest/customers");
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			System.out.println("opened connection...");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			OutputStream os = connection.getOutputStream();
			os.write(newCustomer.getBytes());
			os.flush();
			System.out.println(HttpURLConnection.HTTP_CREATED);
			System.out.println(connection.getResponseCode());
			System.out.println("Location: " + connection.getHeaderField("Location"));
			connection.disconnect();

			// Get the new customer
			System.out.println("*** GET Created Customer **");
			URL getUrl = new URL("http://localhost:8080/rest/customers/1");
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestMethod("GET");
			System.out.println("Content-Type: " + connection.getContentType());

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			System.out.println(HttpURLConnection.HTTP_OK);
			System.out.println(connection.getResponseCode());
			connection.disconnect();

			// Update the new customer. Change Bill's name to William
			String updateCustomer = "<customer>" + "<first-name>William</first-name>" + "<last-name>Burke</last-name>"
					+ "<street>256 Clarendon Street</street>" + "<city>Boston</city>" + "<state>MA</state>"
					+ "<zip>02115</zip>" + "<country>USA</country>" + "</customer>";
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/xml");
			os = connection.getOutputStream();
			os.write(updateCustomer.getBytes());
			os.flush();
			System.out.println(HttpURLConnection.HTTP_NO_CONTENT);
			System.out.println(connection.getResponseCode());
			connection.disconnect();
			
			// Get the updated customer
			System.out.println("*** GET Updated Customer **");
			getUrl = new URL("http://localhost:8080/rest/customers/1");
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestMethod("GET");
			System.out.println("Content-Type: " + connection.getContentType());

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			System.out.println(HttpURLConnection.HTTP_OK);
			System.out.println(connection.getResponseCode());
			connection.disconnect();

			// Create a new customer
			String newCustomer2 = "<customer>" + "<first-name>Sally</first-name>" + "<last-name>Burke</last-name>"
					+ "<street>256 Clarendon Street</street>" + "<city>Boston</city>" + "<state>MA</state>"
					+ "<zip>02115</zip>" + "<country>USA</country>" + "</customer>";

			postUrl = new URL("http://localhost:8080/rest/customers");
			connection = (HttpURLConnection) postUrl.openConnection();
			System.out.println("opened connection...");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			os = connection.getOutputStream();
			os.write(newCustomer2.getBytes());
			os.flush();
			System.out.println(HttpURLConnection.HTTP_CREATED);
			System.out.println(connection.getResponseCode());
			System.out.println("Location: " + connection.getHeaderField("Location"));
			connection.disconnect();

			// Get the new customer
			System.out.println("*** GET Created Customer 2**");
			getUrl = new URL("http://localhost:8080/rest/customers/2");
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestMethod("GET");
			System.out.println("Content-Type: " + connection.getContentType());

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			System.out.println(HttpURLConnection.HTTP_OK);
			System.out.println(connection.getResponseCode());
			connection.disconnect();
			
			// Create a new customer
			String newCustomer3 = "<customer>" + "<first-name>Jimmy</first-name>" + "<last-name>Smith</last-name>"
					+ "<street>123 Main Street</street>" + "<city>Boston</city>" + "<state>MA</state>"
					+ "<zip>02115</zip>" + "<country>USA</country>" + "</customer>";

			postUrl = new URL("http://localhost:8080/rest/customers");
			connection = (HttpURLConnection) postUrl.openConnection();
			System.out.println("opened connection...");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			os = connection.getOutputStream();
			os.write(newCustomer3.getBytes());
			os.flush();
			System.out.println(HttpURLConnection.HTTP_CREATED);
			System.out.println(connection.getResponseCode());
			System.out.println("Location: " + connection.getHeaderField("Location"));
			connection.disconnect();

			// Get the new customer
			System.out.println("*** GET Created Customer 3**");
			getUrl = new URL("http://localhost:8080/rest/customers/3");
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestMethod("GET");
			System.out.println("Content-Type: " + connection.getContentType());

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			System.out.println(HttpURLConnection.HTTP_OK);
			System.out.println(connection.getResponseCode());
			connection.disconnect();

			// Create a new customer
			String newCustomer4 = "<customer>" + "<first-name>Agatha</first-name>" + "<last-name>Edwards</last-name>"
					+ "<street>123 Pine Street</street>" + "<city>Boston</city>" + "<state>MA</state>"
					+ "<zip>02115</zip>" + "<country>USA</country>" + "</customer>";

			postUrl = new URL("http://localhost:8080/rest/customers");
			connection = (HttpURLConnection) postUrl.openConnection();
			System.out.println("opened connection...");
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			os = connection.getOutputStream();
			os.write(newCustomer4.getBytes());
			os.flush();
			System.out.println(HttpURLConnection.HTTP_CREATED);
			System.out.println(connection.getResponseCode());
			System.out.println("Location: " + connection.getHeaderField("Location"));
			connection.disconnect();

			// Get the new customer
			System.out.println("*** GET Created Customer 4**");
			getUrl = new URL("http://localhost:8080/rest/customers/4");
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestMethod("GET");
			System.out.println("Content-Type: " + connection.getContentType());

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			System.out.println(HttpURLConnection.HTTP_OK);
			System.out.println(connection.getResponseCode());
			connection.disconnect();
			
			// Get the new customer
			System.out.println("*** Verify Customer 1 Exists*");
			getUrl = new URL("http://localhost:8080/rest/customers/1");
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestMethod("GET");
			System.out.println("Content-Type: " + connection.getContentType());

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			System.out.println(HttpURLConnection.HTTP_OK);
			System.out.println(connection.getResponseCode());
			connection.disconnect();
			
			 //Delete a customer
			System.out.println("*** DELETE a Customer **");
			URL deleteURL = new URL("http://localhost:8080/rest/customers/1");
			connection = (HttpURLConnection) deleteURL.openConnection();
			System.out.println("opened connection...");
			connection.setRequestMethod("DELETE");
		
			System.out.println(connection.getResponseCode());
			connection.disconnect();

			// Get the customers with a starting ID and number of customers
			System.out.println("*** GET the customers with a starting ID and number of customers**");
			getUrl = new URL("http://localhost:8080/rest/customers/2/2");
			connection = (HttpURLConnection) getUrl.openConnection();
			connection.setRequestMethod("GET");
			System.out.println("Content-Type: " + connection.getContentType());

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			System.out.println(HttpURLConnection.HTTP_OK);
			System.out.println(connection.getResponseCode());
			connection.disconnect();
			
      } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
