package a;

public class Queue {
	private int front;
	private int rear;
	private int size;
	private String[] bf;
	
	Queue(int size){
		this.front = 0;
		this.rear = 0;
		this.size = size;
		this.bf = new String[size];
	}
	
	void enqueue(String id){
		this.bf[this.rear] = id;
		this.rear = (this.rear + 1) % this.size;
	}
	
	String dequeue(){
		String tmp = this.bf[this.front];
		this.front = (this.front + 1) % this.size;
		return tmp;
	}
	
	boolean isEmpty(){
		return this.front == this.rear;
	}
}
