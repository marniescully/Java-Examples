import java.util.*;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class CountWords {
	// master() 
	// 		* Reads a file from the file system
	// 		* Passes the file to map() one line at a time
	//		* Receives the results of map() --a list of k,v pairs--
	//		* Passes the results of map() to reduce()
	//		* Receives and displays the aggregated results of reduce() 
	public static void main(String[] args) {
		try {
			String path = "/Users/marnie/Desktop/gettysburg.txt";
			FileReader file_reader = new FileReader(path);
			BufferedReader buff_reader = new BufferedReader(file_reader);
			String line =  " ";
			ArrayList<Map<String,Integer>> wordMapsList = new ArrayList<Map<String,Integer>>();
			ArrayList<Map<String,Integer>> wordCountList = new ArrayList<Map<String,Integer>>();
			while ((line = buff_reader.readLine()) != null) {
				// For every line of text Send it to map()
				wordMapsList= map(line);
				// Then send that to reduce()
				wordCountList.add(reduce(wordMapsList));
			}
			buff_reader.close();
			// Create a new TreeMap of sorted words and total counts
			TreeMap<String, Integer> wordCount = new TreeMap<String, Integer>();
			for (Map<String,Integer> wordMap : wordCountList){	
				for (Object  key : wordMap.keySet()) {
			    		String word = key.toString();
			    		Integer val = (Integer) wordMap.get(word);
			    		if (wordCount.containsKey(word)){
			    			val = (Integer) wordCount.get(word);
			    			wordCount.remove(word);
			    			wordCount.put(word, (val+ 1));
			    		} else {
			    			wordCount.put(word,val);
			    		}
			    	 }
		     }
			int totalWords= 0;
			for (Object  key : wordCount.keySet()) {
		    		System.out.println(key.toString() + ", " + wordCount.get(key.toString()));
		    		totalWords = totalWords + wordCount.get(key.toString());
			}
			System.out.println("Total Words counted: " + totalWords);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// map()
	// 		* Receives a line of text from master()
	// 		* Splits the file up on "" and places each word
	// 		* Into a HashMap <word,1> all HashMaps into a List
	// 		* Return this list of HashMaps back to master()
	public static ArrayList<Map<String, Integer>> map(String line){
		//	* Splits the line up on "" and places each word
		// 	  into a HashMap <word,1> all HashMaps into a List
	  	List<String> list = Arrays.asList(line.split("\\W+"));
		ArrayList<Map<String, Integer>> wordMapList = new ArrayList<Map<String,Integer>>();
		for (String word : list){
			// creating new HashMap
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            word = word.toLowerCase();
            map.put(word,1);
            wordMapList.add(map);
		}
		return wordMapList;
	}
	
	// reduce()
	// 		* Receives a List of HashMaps from master()
	// 		* Parses list and counts occurrences of words
	// 		* Return HashMap of words with counts to master()
	public static Map<String,Integer> reduce(ArrayList<Map<String,Integer>> wordList){
	    Map<String, Integer> wordCount = new HashMap<String, Integer>();
	 	// Loop through the list
	 	// For each HashMap in List
	     for (Map<String,Integer> wordMap : wordList) {	
		    	 for (Object  key : wordMap.keySet()) {
		    		String word = key.toString();
		    		Integer val = (Integer) wordMap.get(word);
		    		// Is this word in HashMap already?
				// If it already exists, then add 1 to the value
		    		if (wordCount.containsKey(word)){
		    			val = (Integer) wordCount.get(word);
		    			wordCount.remove(word);
			    		wordCount.put(word, (val+ 1));
			    	// if not then add to a local HashMap as key,value
		    		} else {
			   	 		wordCount.put(word,val);
		    		}
		    	 }
	     }
	// Return HashMap to master()     
	return wordCount;
	}
}
