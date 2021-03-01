package multipleProducersMultipleConsumers;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Buffer {
	//int value;

	ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(4);

	void put(int value) {
		//this.value = value;
		try {
			q.put(value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	int get() {
		//return value;
		try {
			return q.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
