package a;

import java.util.HashMap;
import java.util.Iterator;

import a.FileRead.HashAndTotal;

public class Heap {
    private int minHeapSize;
    private int maxHeapSize;
    private ob minHeap[];
    private ob maxHeap[];
    
    public Heap(HashMap h) {
    	int size = h.size();
        minHeapSize = size;
        maxHeapSize = size;
        minHeap = new ob[size + 1];
        maxHeap = new ob[size + 1];
        input(h);
        buildMinHeap();
        buildMaxHeap();
    }
    
    public void input(HashMap h){
    	int a = 0;
    	Iterator <String> i = h.keySet().iterator();
        while(i.hasNext()){
        	String key = i.next();
        	minHeap[++a] = new ob();
        	maxHeap[a] = new ob();
        	minHeap[a].s = maxHeap[a].s = key;
        	minHeap[a].total = maxHeap[a].total = ((HashAndTotal) h.get(key)).getTotal();
        }
    }
    
    public class ob{
    	String s;
    	int total;
    }
    
    public int left(int i){
    	return i*2;
    }
    public int right(int i){
    	return i*2 + 1;
    }
    public int parent(int i){
    	return i / 2;
    }
    public void minFixUp(int i){
    	int smallest;
    	int l = left(i);
    	int r = right(i);
    	if(l <= minHeapSize && minHeap[l].total < minHeap[i].total)
    		smallest = l;
    	else smallest = i;
    	if(r <= minHeapSize && minHeap[r].total < minHeap[smallest].total)
    		smallest = r;
    	if(smallest != i){
    		ob tmp = minHeap[i];
    		minHeap[i] = minHeap[smallest];
    		minHeap[smallest] = tmp;
    		minFixUp(smallest);
    	}
    }
    public void maxFixUp(int i){
    	int largest;
    	int l = left(i);
    	int r = right(i);
    	if(l <= maxHeapSize && maxHeap[l].total > maxHeap[i].total)
    		largest = l;
    	else largest = i;
    	if(r <= maxHeapSize && maxHeap[r].total > maxHeap[largest].total)
    		largest = r;
    	if(largest != i){
    		ob tmp = maxHeap[i];
    		maxHeap[i] = maxHeap[largest];
    		maxHeap[largest] = tmp;
    		maxFixUp(largest);
    	}
    }
    public void buildMinHeap(){
    	for(int i = minHeapSize; i >= 1; i--){
    		minFixUp(i);
    	}
    }
    public void buildMaxHeap(){
    	for(int i = maxHeapSize; i >= 1; i--){
    		maxFixUp(i);
    	}
    }
    public ob extractMin(){
    	ob tmp = minHeap[minHeapSize];
    	minHeap[minHeapSize--] = minHeap[1];
    	minHeap[1] = tmp;
    	minFixUp(1);
    	return minHeap[minHeapSize + 1];
    }
    public ob extractMax(){
    	ob tmp = maxHeap[maxHeapSize];
    	maxHeap[maxHeapSize--] = maxHeap[1];
    	maxHeap[1] = tmp;
    	maxFixUp(1);
    	return maxHeap[maxHeapSize + 1];
    }
}