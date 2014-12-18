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
		if (queue.size() == cacheSize) {
			removeLeastRecentlyUsed();
		}

		CacheInfo<ValueType> cacheInfo = new CacheInfo<ValueType>(value,
				queue.addFirst(key));
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
		map.remove(queue.getLast());
		queue.remove(queue.getLast());
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
