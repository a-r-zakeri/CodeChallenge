public class MyLinkedList<T> {
	private LinkedListElement<T> firstElement = null, lastElement = null;
	private int size = 0;

	public LinkedListElement<T> getFirst() {
		return firstElement;
	}

	public LinkedListElement<T> getLast() {
		return lastElement;
	}

	public int size() {
		return size;
	}


	public LinkedListElement<T> addFirst(T value) {
		LinkedListElement<T> element = new LinkedListElement<T>(value);
		return addFirst(element);
	}

	public LinkedListElement<T> addFirst(LinkedListElement<T> element) {
		size++;

		element.setNext(firstElement);

		if (firstElement != null)
			firstElement.setPrev(element);
		firstElement = element;

		if (lastElement == null)
			lastElement = element;

		return element;
	}

	public void remove(LinkedListElement<T> element) {
		if (element.getPrev() != null)
			element.getPrev().setNext(element.getNext());
		if (element.getNext() != null)
			element.getNext().setPrev(element.getPrev());

		if (element == lastElement)
			lastElement = element.getPrev();
		if (element == firstElement)
			firstElement = element.getNext();
	}
	
	public void clear(){
		firstElement = null;
		lastElement = null;
		size = 0;
	}

}

class LinkedListElement<T> {

	private T value;
	private LinkedListElement<T> next = null, prev = null;

	public LinkedListElement(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public LinkedListElement<T> getNext() {
		return next;
	}

	public void setNext(LinkedListElement<T> next) {
		this.next = next;
	}

	public LinkedListElement<T> getPrev() {
		return prev;
	}

	public void setPrev(LinkedListElement<T> prev) {
		this.prev = prev;
	}
	
}
