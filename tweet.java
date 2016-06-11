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
		
		HashMap <String, WordsAndUsers> tweets = new HashMap(); //�ܾ key�� �ϴ� �ؽ�
	    HashMap <String, UsersAndWords> users = new HashMap();  //����id�� key�� �ϴ� �ؽ�
       
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
	        	//�ش� word�� �����ϴ� key�� ���ٸ� �߰�
	        	if(!tweets.containsKey(word)){
	        		WordsAndUsers tmp = new WordsAndUsers(id);
	        		tweets.put(word, tmp);
	        	}
	        	//�ִٸ� �����߰��� �ο�Ƚ�� +1
	        	else{
	        		tweets.get(word).addUser(id);
	        		tweets.get(word).plusOne();
	        	}
	        	//�ش� id�� �����ϴ� key�� ���ٸ� �߰�
	        	if(!users.containsKey(id)){
	        		TotalUsers++;//���ο� �����̹Ƿ� ������ +1
	        		UsersAndWords tmp = new UsersAndWords(word);
	        		users.put(id, tmp);
	        	}
	        	//�ִٸ� �ܾ��߰��� tweetȽ�� +1
	        	else{
	        		users.get(id).addWord(word);
	        		users.get(id).plusOne();
	        	}
	        	//Ʈ�� ���� +1
        		TotalTweets++;
	        }
			br.close();
			
		}catch(Exception ex){
			
		}
		
		hashArray ret = new hashArray(tweets, users);
		return ret;
	}
	//� �ܾ ����� �������� ���հ� �� �ܾ��� �ο�Ƚ���� ��� �ִ� class
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
	//�� ������ ����� �ܾ���� ���հ� �� ������ Ʈ�� Ƚ���� ��� �ִ� class
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
