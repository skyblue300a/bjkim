import java.util.*;
import java.io.*;

public class tweet {
	static int TotalUsers;
	static int FriendshipRecords;
	static int TotalTweets;
	
	public static void main(String args[]){
		Scanner scan = new Scanner(System.in);
		String menu = "";

        tweet my = new tweet();
        
		File user = new File("c:\\asdf.txt");
		
        HashMap <String, WordsAndUsers> tweets = new HashMap();
	    HashMap <String, UsersAndWords> users = new HashMap();
	    
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
	    	
	    	if(menu.equals("0")){
	    		hashArray tmp = my.FileRead(user);
	            tweets = tmp.tweets;
	            users = tmp.users;
	            
	            System.out.println("Total users: " + TotalUsers);
	            System.out.println("Total firendship records: " + FriendshipRecords);
	            System.out.println("Total tweets" + TotalTweets);
	    	}
	    	
	    	
	    }
        
       
	    
        
         
	}
	public hashArray FileRead(File file){
		
		HashMap <String, WordsAndUsers> tweets = new HashMap(); //단어를 key로 하는 해쉬
	    HashMap <String, UsersAndWords> users = new HashMap();  //유저id를 key로 하는 해쉬
       
		try{ 
            FileReader fileReader = new FileReader(file);
	        BufferedReader br = new BufferedReader(fileReader);
	        
	        String id = null;
	        String date = null;
	        String word = null;
	        while((id = br.readLine()) != null){
	        	date = br.readLine();
	        	word = br.readLine();
	        	br.readLine();
	        	//해당 word에 대응하는 key가 없다면 추가
	        	if(!tweets.containsKey(word)){
	        		WordsAndUsers tmp = new WordsAndUsers(id);
	        		tweets.put(word, tmp);
	        	}
	        	//있다면 유저추가와 인용횟수 +1
	        	else{
	        		tweets.get(word).addUser(id);
	        		tweets.get(word).plusOne();
	        	}
	        	//해당 id에 대응하는 key가 없다면 추가
	        	if(!users.containsKey(id)){
	        		TotalUsers++;//새로운 유저이므로 유저수 +1
	        		UsersAndWords tmp = new UsersAndWords(word);
	        		users.put(id, tmp);
	        	}
	        	//있다면 단어추가와 tweet횟수 +1
	        	else{
	        		users.get(id).addWord(word);
	        		users.get(id).plusOne();
	        	}
	        	//트윗 갯수 +1
        		TotalTweets++;
	        }
			br.close();
			
		}catch(Exception ex){
			
		}
		
		hashArray ret = new hashArray(tweets, users);
		return ret;
	}
	//어떤 단어를 사용한 유저들의 집합과 그 단어의 인용횟수를 담고 있는 class
	class WordsAndUsers{
		private RedBlackTree user = new RedBlackTree();
		private int numUsed;
		
		WordsAndUsers(String id){
			numUsed = 1;
			addUser(id);
		}
		void plusOne(){
			numUsed++;
		}
		void addUser(String id){
			user.add(id);
		}
		int getNumUsed(){
			return numUsed;
		}
		
	}
	//한 유저가 사용한 단어들의 집합과 그 유저의 트윗 횟수를 담고 있는 class
	class UsersAndWords{
		private RedBlackTree word = new RedBlackTree();
		private int numMentioned;
		
		UsersAndWords(String tweet){
			numMentioned = 1;
			addWord(tweet);
		}
		void plusOne(){
			numMentioned++;
		}
		void addWord(String tweet){
			word.add(tweet);
		}
		int getNumMentioned(){
			return numMentioned;
		}
	}
	class hashArray{
		HashMap <String, WordsAndUsers> tweets;
	    HashMap <String, UsersAndWords> users;
	    
	    hashArray(HashMap <String, WordsAndUsers> tweets, HashMap <String, UsersAndWords> users){
	    	this.tweets = tweets;
	    	this.users = users;
	    }
	}
}
