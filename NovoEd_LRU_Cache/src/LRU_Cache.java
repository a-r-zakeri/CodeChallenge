import java.util.HashMap;

public class LRU_Cache<KeyType, ValueType> implements Cache<KeyType, ValueType> {

	private HashMap<KeyType, CacheInfo<ValueType>> map;
	private MyLinkedList<KeyType> queue;
	private int cacheSize;

	public LRU_Cache(int cacheSize) {
		this.cacheSize = cacheSize;
		map = new HashMap<KeyType, CacheInfo<ValueType>>();
		queue = new MyLinkedList<KeyType>();
	}

	@Override
	public ValueType get(KeyType key) throws Exception {

		CacheInfo<ValueType> cacheInfo = map.get(key);

		if (cacheInfo == null) {
			throw new Exception("This key is not available in cache");
		}

		touch(cacheInfo.getQueueElement());

		return cacheInfo.getValue();
	}

	@Override
	public void set(KeyType key, ValueType value) {
		if (map.containsKey(key))
			removeKey(key);
			
		if (queue.size() == cacheSize) {
			removeLeastRecentlyUsed();
		}
	
		LinkedListElement<KeyType> queueElement = queue.addFirst(key);
	
		CacheInfo<ValueType> cacheInfo = new CacheInfo<ValueType>(value,
				queueElement);
	
		map.put(key, cacheInfo);
	}

	@Override
	public void clearAll() {
		map.clear();
		queue.clear();
	}

	private void touch(LinkedListElement<KeyType> element) {
		queue.remove(element);
		queue.addFirst(element);
	}

	private void removeLeastRecentlyUsed() {
		map.remove(queue.getLast().getValue());
		queue.remove(queue.getLast());
	}
	
	private void removeKey(KeyType key){
		if (map.containsKey(key) == false)	return;
		queue.remove(map.get(key).getQueueElement());
		map.remove(key);
	}

}

class CacheInfo<ValueType> {
	private LinkedListElement queueElement;
	private ValueType value;

	public CacheInfo(ValueType value, LinkedListElement queueElement) {
		this.value = value;
		this.queueElement = queueElement;
	}

	public void setValue(ValueType value) {
		this.value = value;
	}

	public ValueType getValue() {
		return value;
	}

	public LinkedListElement getQueueElement() {
		return queueElement;
	}

	public void setQueueElement(LinkedListElement queueElement) {
		this.queueElement = queueElement;
	}

}
