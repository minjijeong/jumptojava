package com.datastructure.thinkdatastructure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author downey
 * @param <T>
 * Solution :: http://thinkdast.com/myarraylist
 * redirect :: https://github.com/AllenDowney/ThinkDataStructures/blob/master/solutions/src/com/allendowney/thinkdast/MyArrayList.java
 */
public class MyArrayList<T> implements List<T> {
	int size;                    // keeps track of the number of elements
	private T[] array;           // stores the elements

	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public MyArrayList() {
		// You can't instantiate an array of T[], but you can instantiate an
		// array of Object and then typecast it.  Details at
		// http://www.ibm.com/developerworks/java/library/j-jtp01255/index.html
		array = (T[]) new Object[10];
		size = 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// run a few simple tests
		MyArrayList<Integer> mal = new MyArrayList<Integer>();
		mal.add(1);
		mal.add(2);
		mal.add(3);
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);

		mal.remove(new Integer(2));
		System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
	}

	@Override
	public boolean add(T element) {
		// TODO: FILL THIS IN!
		if(size >= array.length){
			// 큰 배열을 만들고 요소들을 복사합니다.
			// 책은 E라고 되어 있지만.. 답안지에는 T라고 되어 있음! E로 하면 클래스에서 받은 매개변수가 아니라서 에러남!
			T[] bigger = (T[]) new Object[array.length * 2];
			System.arraycopy(array, 0, bigger,0, array.length);
			array = bigger;
		}
		array[size] = element;
		size++;
		return true;
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		// add the element to get the resizing
		add(element);

		// shift the elements
		for (int i=size-1; i>index; i--) {
			array[i] = array[i-1];
		}
		// put the new one in the right place
		array[index] = element;
	}

	@Override
	public boolean addAll(Collection<? extends T> collection) {
		boolean flag = true;
		for (T element: collection) {
			flag &= add(element);
		}
		return flag;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		// note: this version does not actually null out the references
		// in the array, so it might delay garbage collection.
		size = 0;
	}

	@Override
	public boolean contains(Object obj) {
		return indexOf(obj) != -1;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		for (Object element: collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	@Override
	public int indexOf(Object target) {
		// https://docs.oracle.com/javase/8/docs/api/java/util/List.html#indexOf-java.lang.Object-
		// the index of the first occurrence of the specified element in this list
		// , or -1 if this list does not contain the element
		// TODO: FILL THIS IN!
		for(int i=0; i< size; i++){
			if(equals(target,array[i])){
				return i;
			}
		}
		return -1;
	}

	/** Checks whether an element of the array is the target.
	 *
	 * Handles the special case that the target is null.
	 *
	 * @param target
	 * @param object
	 */
	private boolean equals(Object target, Object element) {
		if (target == null) {
			return element == null;
		} else {
			return target.equals(element);
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).iterator();
	}

	@Override
	public int lastIndexOf(Object target) {
		// see notes on indexOf
		for (int i = size-1; i>=0; i--) {
			if (equals(target, array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// make a copy of the array
		T[] copy = Arrays.copyOf(array, size);
		// make a list and return an iterator
		return Arrays.asList(copy).listIterator(index);
	}

	@Override
	public boolean remove(Object obj) {
		int index = indexOf(obj);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	@Override
	public T remove(int index) {
		// https://docs.oracle.com/javase/8/docs/api/java/util/List.html#add-int-E-
		// TODO: FILL THIS IN!
		T element = get(index);
		for(int i=index;i< array.length-1;i++){
			array[i] = array[i+1];
		}
		size--;
		return element;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean flag = true;
		for (Object obj: collection) {
			flag &= remove(obj);
		}
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T set(int index, T element) {
		// 참조 문서 - https://docs.oracle.com/javase/8/docs/api/java/util/List.html#set-int-java.lang.Object-
		// TODO: FILL THIS IN!
		T old = get(index);
		array[index] = element;
		return old;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		T[] copy = Arrays.copyOfRange(array, fromIndex, toIndex);
		return Arrays.asList(copy);
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, size);
	}

	@Override
	public <U> U[] toArray(U[] array) {
		throw new UnsupportedOperationException();
	}
}
