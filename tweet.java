package a;

import java.util.*;
import java.io.*;

import a.AdjGraph.Vertex;
import a.FileRead.*;
import a.Heap.*;

public class tweet {
	public static void main(String args[]){
		Scanner scan = new Scanner(System.in);
		String menu = "";
        
		File userprofile = new File("c:\\users.txt");
		File userfriend = new File("c:\\friends.txt");
		File userword = new File("c:\\words.txt");
		
		FileRead f = new FileRead();
		
		AdjGraph friends = new AdjGraph();
		HashMap <String, String> userProfile = new HashMap();
        HashMap <String, WordHashAndTotal> users = new HashMap();
	    HashMap <String, UserHashAndTotal> words = new HashMap();
	    
	    ArrayList<String> res = new ArrayList<String>();
	    
	    while(!menu.equals("99")){
	    	System.out.print("\n" + "******menu******" + "\n"
	    		+ "0. Read data files" + "\n"
				+ "1. display statistics" + "\n"
				+ "2. Top 5 most tweeted words" + "\n"
				+ "3. Top 5 most tweeted users" + "\n"
				+ "4. Find users who tweeted a word" + "\n"
				+ "5. Find all people who are friends of the above users" + "\n"
				+ "6. Delete all mentions of a word" + "\n"
				+ "7. Delete all users who mentioned a word" + "\n"
				+ "8. Find strongly connected components" + "\n"
				+ "9. Find shortest path from a given user" + "\n"
				+ "99. Quit" + "\n"
				+ "Select Menu: ");
	    	
	    	menu = scan.nextLine();
	    	System.out.println();
	    	
	    	if(menu.equals("0")){
	    		userProfile = f.ReadUserProfile(userprofile);
	    		friends = f.ReadFriend(userfriend);
	    		hashArray tmp = f.ReadWordTweet(userword);
	            words = tmp.words;
	            users = tmp.users;
	            
	           
	            System.out.println("Total users: " + f.getTotalUsers());
	            System.out.println("Total friendship records: " + f.getTotalFriend());
	            System.out.println("Total tweets: " + f.getTotalTweets());
	    	} 	
	    	else if(menu.equals("1")){
	    		Heap heap = new Heap(users);
	    		
	    		System.out.println("Average number of friends: " + (f.getTotalTweets() / f.getTotalUsers()));
		        System.out.println("Minimum friends: " + f.minf);
		        System.out.println("Maximum friends: " + f.maxf);
	    		System.out.println("Average tweets per user: " +(f.getTotalTweets() / f.getTotalUsers()));
	    		System.out.println("Minimum tweets per user: " + heap.extractMin().total);
	    		System.out.println("Maximum tweets per user: " + heap.extractMax().total);
		    	
	    	}
	    	else if(menu.equals("2")){
	    		Heap heap = new Heap(words);
	    		for(int i = 0; i < 5; i++){
	    			System.out.println(heap.extractMax().s);
	    		}
	    	}
	    	else if(menu.equals("3")){
	    		Heap heap = new Heap(users);
	    		for(int i = 0; i < 5; i++){
	    			System.out.println(userProfile.get(heap.extractMax().s));
	    		}
	    	}
	    	else if(menu.equals("4")){
	    		System.out.print("Write a word you want to find: ");
	    		String word = scan.nextLine();
	    		if(words.containsKey(word)){
	    			Iterator<String> i = words.get(word).username().iterator();
	    			while(i.hasNext()){
	    				String userID = i.next();
	    				res.add(userID);
	    				System.out.println(userProfile.get(userID));
	    			}
	    		}
	    		else{
	    			System.out.println("No user tweeted this word");
	    		}
	    	}
	    	else if(menu.equals("5")){
	    		
	    		Iterator<String> i = res.iterator();
	    		while(i.hasNext()){
	    			String key = i.next();
	    			System.out.print("Friends of " + userProfile.get(key) + " are [");
	    			Iterator<String> j = friends.findFriend(key).iterator();
	    			while(j.hasNext()){
	    				String friend = j.next();
	    				System.out.print(userProfile.get(friend) + " ");
	    			}
	    			System.out.println("]");
	    		}
	    		
	    	}
	    	else if(menu.equals("6")){
	    		System.out.print("Wirte a word you want to delete: ");
	    		String word = scan.nextLine();
	    		if(words.containsKey(word)){
	    			Set key = words.get(word).username();
	    			
	    			Iterator<String> i = key.iterator();
	    			while(i.hasNext()){
	    				String user = i.next();
	    				users.get(user).delete(word);
	    			}
	    			words.remove(word);
	    			System.out.println("done");
	    		}
	    		else{
	    			System.out.println("There isn't such word");
	    		}
	    	}
	    	else if(menu.equals("7")){
	    		System.out.print("Wirte a word you want to delete all users used that word: ");
	    		String word = scan.nextLine();
	    		if(words.containsKey(word)){
	    			Iterator<String> i = users.keySet().iterator();
	    			
	    			while(i.hasNext()){
	    				String user = i.next();
	    				if(users.get(user).containsKey(word)){
	    					Set key = users.get(user).usedwords();
	    					Iterator<String> j = key.iterator();
	    					while(j.hasNext()){
	    						String dword = j.next();
	    						words.get(dword).delete(user);
	    						if(words.get(dword).isEmpty()){
	    							words.remove(dword);
	    						}
	    					}
	    					users.get(user).minusUser();
	    					f.TotalFriend -= friends.numFriend(user);
	    					userProfile.remove(user);
	    					friends.deleteVertex(user);
	    					i.remove();
	    				}
	    			}
	    			System.out.println("done");
	    		}
	    		else{
	    			System.out.println("There isn't such word");
	    		}
	    	
	    	}
	    	else if(menu.equals("8")){
	    		ArrayList<ArrayList<String>> pr = friends.fiveSCC();
	    		for(int i = 0; i < 5; i++){
	    			for(int j = 0; j < pr.get(i).size(); i++){
						System.out.println(userProfile.get(pr.get(i).get(j)));
	    			}
	    		}
	    	}
	    	else if(menu.equals("9")){
	    		System.out.print("Write a user id(in number): ");
	    		String dijid = scan.nextLine();
	    		Dijsktra d = new Dijsktra(friends);
	    		ArrayList<Vertex> a = d.dij(dijid);
	    		Collections.sort(a, new VertexDescen());
	    		for(int i = 0; i < a.size(); i++){
	    			System.out.println(userProfile.get(a.get(i).id) + ": " + a.get(i).d);
	    		}
	    		for(int i = 0; i < 5; i++){
	    			Vertex tmp = a.get(i);
	    			System.out.print(userProfile.get(tmp.id));
	    			tmp = tmp.p;
	    			while(tmp!=null){
	    				System.out.print("->" + userProfile.get(tmp.id));
	    				tmp = tmp.p;
	    			}
	    			System.out.println();
	    		}
	    	}
	    }
	}
	public static class VertexDescen implements Comparator<Vertex>{
		@Override
		public int compare(Vertex a, Vertex b){
			if(a.d > b.d) return -1;
			else if(a.d < b.d) return 1;
			return 0;
		}
	}
}
