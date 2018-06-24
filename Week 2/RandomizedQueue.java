import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private static final int MIN = 2;
	private int size = 0;
	private Item[] items;

	// construct an empty randomized queue
	public RandomizedQueue() {
		items = (Item[]) new Object[MIN];
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Can't add null to the queue");
		}

		if (isFull()) {
			doubleArray();
		}

		items[size++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Can't remove an Item from the empty queue");
		}

		int index = StdRandom.uniform(size);
		Item returnValue = items[index];
		size--;
		items[index] = items[size];
		items[size] = null;

		if (isOversized()) {
			halveArray();
		}

		return returnValue;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("Can't sample an Item from the empty queue");
		}

		int index = StdRandom.uniform(size);
		return items[index];
	}

	private boolean isFull() {
		return items.length == size;
	}

	private boolean isOversized() {
		return items.length > MIN && size <= items.length / 4;
	}

	private void halveArray() {
		resize(items.length / 2);
	}

	private void doubleArray() {
		resize(items.length * 2);
	}

	private void resize(int newSize) {
		Item[] newItem = (Item[]) new Object[newSize];
		for (int i = 0; i < size; i++) {
			newItem[i] = items[i];
		}
		items = newItem;
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomIterator();
	}

	private class RandomIterator implements Iterator<Item> {

		private final Item[] randomItems;
		private int index;

		public RandomIterator() {
			randomItems = copy();
			StdRandom.shuffle(randomItems);
		}

		private Item[] copy() {
			Item[] copiedItems = (Item[]) new Object[size];
			for (int i = 0; i < size; i++) {
				copiedItems[i] = items[i];
			}
			return copiedItems;
		}

		public boolean hasNext() {
			return index < randomItems.length;
		}

		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Reached end of queue");
			} else {
				return randomItems[index++];
			}
		}

		public void remove() {
			throw new UnsupportedOperationException("Remove is not supported");
		}
	}
}
