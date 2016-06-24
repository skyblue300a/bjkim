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
	

	//user�� id�� �г����� �д� ����
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
	
	
	//word tweet�� ������ �� ����� ����
	public class HashAndTotal{
		protected int total;
		protected HashMap<String, Integer> key;
		
		void add(String key) {
			// hashmap�� key�� ���ٸ� Ű �߰� �� ���� 1�� �ʱ�ȭ
			if (!this.key.containsKey(key)) {
				this.key.put(key, 1);
			}
			// Ű�� �ִٸ� hash�� key���Ƚ�� + 1
			else
				this.key.put(key, this.key.get(key) + 1);
			this.total++; // �� key�� ���Ƚ�� + 1
		}

		void delete(String key) {
			TotalTweets -= this.key.get(key);// ���� �� ��ü ��� ������ �� key�� ���Ƚ����ŭ ����
			this.total -= this.key.get(key);
			this.key.remove(key);
		}

		public int getTotal(){
			return this.total;
		}
	}
	// word hash�� value�� �� (����hash, word���Ƚ��)
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

	// user hash�� value�� �� (word hash, user�� mention��)
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
				TotalUsers--; //isempty�� �׻� delete������ ���������� ����ȴ�.
							  //��, delete���� ���Ŀ� �� user�� ����� �ܾ ���ٸ� �� user�� �����ʹ� ������Ƿ� ��ü���� - 1�� ���ش�
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

		HashMap<String, UserHashAndTotal> words = new HashMap(); // �ܾ key�� �ϴ� �ؽ�
		HashMap<String, WordHashAndTotal> users = new HashMap(); // ����id�� key�� �ϴ� �ؽ�

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
				// �ش� word�� �����ϴ� key�� ���ٸ� �߰�
				if (!words.containsKey(word)) {
					UserHashAndTotal tmp = new UserHashAndTotal(user);
					words.put(word, tmp);
				}
				// �ִٸ� �����߰�
				else {
					words.get(word).add(user);
				}
				// �ش� id�� �����ϴ� key�� ���ٸ� �߰�
				if (!users.containsKey(user)) {
					WordHashAndTotal tmp = new WordHashAndTotal(word);
					users.put(user, tmp);
				}
				// �ִٸ� �ܾ��߰�
				else {
					users.get(user).add(word);
				}
				// Ʈ�� ���� +1
				TotalTweets++;
			}
			br.close();

		} catch (Exception ex) {

		}

		hashArray ret = new hashArray(words, users);
		return ret;
	}
	
}
