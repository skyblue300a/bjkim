package a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Set;
import a.AdjGraph.*;

public class FileRead {
	private static int TotalUsers;
	private static int TotalTweets;
	private static int TotalFriend;
	
	int getTotalUsers(){
		return TotalUsers;
	}
	int getTotalTweets(){
		return TotalTweets;
	}
	int getTotalFriend(){
		return TotalFriend;
	}
	
	
	public AdjGraph ReadFriend(File file){
		AdjGraph g = new AdjGraph();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String self;
			String friend;
			while((self = br.readLine()) != null){
				friend = br.readLine();
				br.readLine();
				if(!g.containsKey(self))
					g.putVertex(self);
				if(!g.containsKey(friend))
					g.putVertex(friend);
				if(!g.containsFriend(self, friend))
					g.addVertex(self, friend);
				TotalFriend++;
			}
			
			br.close();

		} catch (Exception ex) {

		}
		return g;
		
	}
	

	//user의 id와 닉네임을 읽는 파일
	public HashMap<String, String> ReadUserProfile(File file){
		
		HashMap<String, String> profile = new HashMap();
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String id;
			String name;
			while((id = br.readLine()) != null){
				br.readLine();
				name = br.readLine();
				br.readLine();
				if(!profile.containsKey(id))
					profile.put(id, name);
				TotalUsers++;
				
			}
			
			br.close();

		} catch (Exception ex) {

		}
		
		return profile;

	}
	
	
	//word tweet을 저장할 때 사용할 구조
	public class HashAndTotal{
		protected int total;
		protected HashMap<String, Integer> key;
		
		void add(String key) {
			// hashmap에 key가 없다면 키 추가 및 숫자 1로 초기화
			if (!this.key.containsKey(key)) {
				this.key.put(key, 1);
			}
			// 키가 있다면 hash의 key사용횟수 + 1
			else
				this.key.put(key, this.key.get(key) + 1);
			this.total++; // 이 key의 사용횟수 + 1
		}

		void delete(String key) {
			TotalTweets -= this.key.get(key);// 지울 때 전체 멘션 수에서 이 key의 사용횟수만큼 빼줌
			this.total -= this.key.get(key);
			this.key.remove(key);
		}

		public int getTotal(){
			return this.total;
		}
	}
	// word hash의 value로 들어갈 (유저hash, word사용횟수)
	public class UserHashAndTotal extends HashAndTotal{

		UserHashAndTotal() {
			this.key = new HashMap();
			this.total = 0;
		}

		UserHashAndTotal(String user) {
			this.key = new HashMap();
			this.total = 0;
			this.add(user);
		}

		boolean isEmpty(){
			if(this.key.isEmpty()) return true;
			return false;
		}

		Set username(){
			Set keyset = key.keySet();
			return keyset;
		}
		

	}

	// user hash의 value로 들어갈 (word hash, user의 mention수)
	public class WordHashAndTotal extends HashAndTotal{

		WordHashAndTotal() {
			this.key = new HashMap();
			this.total = 0;
		}

		WordHashAndTotal(String word) {
			this.key = new HashMap();
			this.total = 0;
			this.add(word);
		}

		boolean isEmpty(){
			if(this.key.isEmpty()) {
				TotalUsers--; //isempty는 항상 delete연산이 끝난다음에 실행된다.
							  //즉, delete연산 이후에 이 user가 사용한 단어가 없다면 이 user의 데이터는 사라지므로 전체유저 - 1을 해준다
				return true;	
			}
			return false;
		}
		Set usedwords(){
			Set keyset = this.key.keySet();
			return keyset;
		}
		boolean containsKey(String word){
			return this.key.containsKey(word);
		}
		void minusUser(){
			TotalUsers--;
		}

	}

	public class hashArray {
		HashMap<String, WordHashAndTotal> users;
		HashMap<String, UserHashAndTotal> words;

		hashArray(HashMap<String, UserHashAndTotal> words, HashMap<String, WordHashAndTotal> users) {
			this.words = words;
			this.users = users;
		}
	}
	public hashArray ReadWordTweet(File file) {

		HashMap<String, UserHashAndTotal> words = new HashMap(); // 단어를 key로 하는 해쉬
		HashMap<String, WordHashAndTotal> users = new HashMap(); // 유저id를 key로 하는 해쉬

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);

			String user = null;
			String date = null;
			String word = null;
			while ((user = br.readLine()) != null) {
				date = br.readLine();
				word = br.readLine();
				br.readLine();
				// 해당 word에 대응하는 key가 없다면 추가
				if (!words.containsKey(word)) {
					UserHashAndTotal tmp = new UserHashAndTotal(user);
					words.put(word, tmp);
				}
				// 있다면 유저추가
				else {
					words.get(word).add(user);
				}
				// 해당 id에 대응하는 key가 없다면 추가
				if (!users.containsKey(user)) {
					WordHashAndTotal tmp = new WordHashAndTotal(word);
					users.put(user, tmp);
				}
				// 있다면 단어추가
				else {
					users.get(user).add(word);
				}
				// 트윗 갯수 +1
				TotalTweets++;
			}
			br.close();

		} catch (Exception ex) {

		}

		hashArray ret = new hashArray(words, users);
		return ret;
	}
	
}
