public interface Cache<KeyType, DataType> {
	
	public DataType get(KeyType key) throws Exception;
	
	public void set(KeyType key, DataType data);

	public void clearAll();

}