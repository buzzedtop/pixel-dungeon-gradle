
package com.watabou.utils;

import java.util.ArrayList;
import java.util.List;

public class SparseArray<T> {

    private int[] mKeys;
    private Object[] mValues;
    private int mSize;

    public SparseArray() {
        this(10);
    }

    public SparseArray(int initialCapacity) {
        if (initialCapacity == 0) {
            mKeys = new int[0];
            mValues = new Object[0];
        } else {
            mKeys = new int[initialCapacity];
            mValues = new Object[initialCapacity];
        }
        mSize = 0;
    }

    public void put(int key, T value) {
        int i = binarySearch(mKeys, mSize, key);

        if (i >= 0) {
            mValues[i] = value;
        } else {
            i = ~i;

            if (mSize >= mKeys.length) {
                int n = mSize + (mSize >> 1) + 1;

                int[] nkeys = new int[n];
                Object[] nvalues = new Object[n];

                System.arraycopy(mKeys, 0, nkeys, 0, mKeys.length);
                System.arraycopy(mValues, 0, nvalues, 0, mValues.length);

                mKeys = nkeys;
                mValues = nvalues;
            }

            if (mSize - i != 0) {
                System.arraycopy(mKeys, i, mKeys, i + 1, mSize - i);
                System.arraycopy(mValues, i, mValues, i + 1, mSize - i);
            }

            mKeys[i] = key;
            mValues[i] = value;
            mSize++;
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int key) {
        return get(key, null);
    }

    @SuppressWarnings("unchecked")
    public T get(int key, T valueIfKeyNotFound) {
        int i = binarySearch(mKeys, mSize, key);

        if (i < 0 || mValues[i] == null) {
            return valueIfKeyNotFound;
        } else {
            return (T) mValues[i];
        }
    }

    public void delete(int key) {
        int i = binarySearch(mKeys, mSize, key);

        if (i >= 0) {
            if (mValues[i] != null) {
                System.arraycopy(mKeys, i + 1, mKeys, i, mSize - (i + 1));
                System.arraycopy(mValues, i + 1, mValues, i, mSize - (i + 1));
                mValues[mSize - 1] = null;
                mSize--;
            }
        }
    }

    public void remove(int key) {
        delete(key);
    }

    public int size() {
        return mSize;
    }

    public int keyAt(int index) {
        return mKeys[index];
    }

    @SuppressWarnings("unchecked")
    public T valueAt(int index) {
        return (T) mValues[index];
    }

    public void clear() {
        for (int i = 0; i < mSize; i++) {
            mValues[i] = null;
        }
        mSize = 0;
    }

    private static int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }

	public int[] keyArray() {
		int size = size();
		int[] array = new int[size];
		for (int i=0; i < size; i++) {
			array[i] = keyAt( i );
		}
		return array;
	}
	
	public List<T> values() {
		int size = size();
		ArrayList<T> list = new ArrayList<T>( size );
		for (int i=0; i < size; i++) {
			list.add( i, valueAt( i ) );
		}
		return list;
	}
}
