import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private int size = 0;
	private Node<Item> first;
	private Node<Item> last;

	private static class Node<Item> {
		private Item value;
		private Node<Item> nextItem;
		private Node<Item> previousItem;
	}

	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the deque
	public int size() {
		return size;
	}

	// add the item to the front
	public void addFirst(Item item) {
		checkException(item);

		if (first == null) {
			Node<Item> onlyItem = new Node<>();
			onlyItem.value = item;
			first = onlyItem;
			last = onlyItem;
		} else {
			Node<Item> newItem = first;
			first = new Node<>();
			first.value = item;
			first.nextItem = newItem;
			newItem.previousItem = first;
		}

		size++;
	}

	// add the item to the end
	public void addLast(Item item) {
		checkException(item);
		
		if (last == null) {
			Node<Item> onlyItem = new Node<>();
			onlyItem.value = item;
			first = onlyItem;
			last = onlyItem;
		} else {
			Node<Item> newItem = last;
			last = new Node<>();
			last.value = item;
			last.previousItem = newItem;
			newItem.nextItem = last;
		}

		size++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (first == null) {
			throw new NoSuchElementException("Can't remove an Item from the empty deque");
		}

		Node<Item> newItem = first;
		first = first.nextItem;
		if (first == null) {
			last = null;
		} else {
			first.previousItem = null;
		}

		size--;

		return newItem.value;
	}

	// remove and return the item from the end
	public Item removeLast() {
		if (last == null) {
			throw new NoSuchElementException("Can't remove an Item from the empty deque");
		}

		Node<Item> newItem = last;
		last = last.previousItem;
		if (last == null) {
			first = null;
		} else {
			last.nextItem = null;
		}

		size--;

		return newItem.value;
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new StraightIterator();
	}

	private class StraightIterator implements Iterator<Item> {

		private Node<Item> current = first;

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Reached end of deque");
			}

			Item returnValue = current.value;
			current = current.nextItem;
			return returnValue;
		}

		public void remove() {
			throw new UnsupportedOperationException("Remove is not supported");
		}
	}

	private void checkException(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Can't add null to the deque");
		}
	}
}
