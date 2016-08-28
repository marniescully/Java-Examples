package com.rest.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LB {
	public static void main(String[] args) {
		try {
			Map<String, Integer> hashMap = new HashMap<String, Integer>();
	        int pings = 1000;
			URL url = new URL("http://HW9-LB-731092093.us-east-1.elb.amazonaws.com");
	        String ip = null;
	        Integer count = null;
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      
			// Iteration for IP addresses
		    //
		    for (int i = 0; i < pings; i++) {
		        connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("GET");
		        BufferedReader reader = new BufferedReader(
		                                    new InputStreamReader(connection.getInputStream()));
		        ip = "";
		        for (String line = reader.readLine(); line != null; line = reader.readLine())
		            ip += line;
		
		        count = hashMap.get(ip);
		        hashMap.put(ip, ((count == null) ? 0 : count.intValue()) + 1);
		
		        System.out.println (ip);
		    }
		
		    // Iteration IP Count
		    System.out.println("");
		    for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
		        System.out.println(entry.getKey() + ": " + entry.getValue());
		    }
		    System.out.println("");
		
		    connection.disconnect();
		
        } catch (Exception e) {
			e.printStackTrace();
		}
	}	        
}

