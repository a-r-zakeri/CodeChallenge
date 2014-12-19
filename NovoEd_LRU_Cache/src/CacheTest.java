import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

public class CacheTest {

	private Random random;
	private final int minCacheSize = 5000;
	private final int maxCacheSize = 100000;
	private final int memorySize = maxCacheSize * 10;
	private final int minValue = -1000 * 1000;
	private final int maxValue = 1000 * 1000;

	@Test
	public void test() {

		random = new Random(System.currentTimeMillis());

		int cacheSize = getRandom(minCacheSize, maxCacheSize);

		LRU_Cache<Integer, Integer> myCache = new LRU_Cache<Integer, Integer>(cacheSize);

		ArrayList<Integer> addedKeys = new ArrayList<Integer>();
		ArrayList<Integer> addedValues = new ArrayList<Integer>();
		
		Integer LRUKey = getRandom(0, memorySize - 1);
		Integer LRUValue = getRandom(minValue, maxValue);
		addedKeys.add(LRUKey);	addedValues.add(LRUValue);
		myCache.set(LRUKey, LRUValue);

		for (int i = 0; i < cacheSize; i++) {
			Integer key, value;
			while (true) {
				key = getRandom(0, memorySize - 1);
				if (addedKeys.contains(key) == false)
					break;
			}
			value = getRandom(minValue, maxValue);
			addedKeys.add(key);	addedValues.add(value);
			myCache.set(key, value);
			// checking the added (key, value) is inserted properly in the cache
			shouldBeAvailable(key, value, myCache);
		}

		// now after LRUKey "cacheSize" number of distinct elements has been added, so LRUkey should be remove from the cache
		shouldNotBeAvailable(LRUKey, myCache);
		
		// but all others should be in the cache
		addedValues.remove(0);
		addedKeys.remove(0);
		shouldBeAvailable(addedKeys, addedValues, myCache);
		
		// now the cache is full and passed some tests
		// I read some value, then read others such that the first read value becomes Least Recently Used
		int index = getRandom(0, addedKeys.size() - 1);
		LRUKey = addedKeys.remove(index);
		LRUValue = addedValues.remove(index);
		shouldBeAvailable(LRUKey, LRUValue, myCache);
		
		
		// read some elements randomly from the cache
		for(int i = 0; i < addedKeys.size() * 2; i++){
			index = getRandom(0, addedKeys.size() - 1);
			int key = addedKeys.get(index);
			int value = addedValues.get(index);
			
			shouldBeAvailable(key, value, myCache);	
		}
		
		// to make sure all elements has been used at least once
		shouldBeAvailable(addedKeys, addedValues, myCache);
		
		// now insert another element to the cache, so LRUKey should be removed from the cache
		int key;
		while(true){
			key = getRandom(0, memorySize - 1);
			if (key != LRUKey && addedKeys.contains(key) == false) break;
		}
		int value = getRandom(minValue, maxValue);
		
		myCache.set(key, value);
			
		//LRU should be removed now
		shouldNotBeAvailable(LRUKey, myCache);
		
		shouldBeAvailable(key, value, myCache);
		
		// all others should be still in the cache
		shouldBeAvailable(addedKeys, addedValues, myCache);

	}

	private int getRandom(int a, int b) { // returns a random number in range[a, b]
		return a + random.nextInt(b - a + 1);
	}


	private void shouldBeAvailable(Integer key, Integer value, Cache<Integer, Integer> cache){
		try {
			int cacheValue = cache.get(key);
			assertTrue(cacheValue == value);
		} catch (Exception e) {
			fail("It should be available in the cache: " + key);
		}
	}
	
	private void shouldBeAvailable(ArrayList<Integer> keys,
			ArrayList<Integer> values, Cache<Integer, Integer> cache) {
		assertTrue(keys.size() == values.size());
		for (int i = 0; i < keys.size(); i++){
			int key = keys.get(i);
			int value = values.get(i);
			shouldBeAvailable(key, value, cache);
		}
	}

	private void shouldNotBeAvailable(ArrayList<Integer> keys, Cache<Integer, Integer> cache) {
		for (Integer key : keys) {
			shouldNotBeAvailable(key, cache);
		}
	}
	
	private void shouldNotBeAvailable(Integer key, Cache<Integer, Integer> cache) {
		try {
			cache.get(key);
			fail("It should not be available in the cache: " + key);
		} catch (Exception e) {
			
		}
	}

}
