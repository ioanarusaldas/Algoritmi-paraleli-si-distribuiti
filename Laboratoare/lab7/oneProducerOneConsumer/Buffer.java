package oneProducerOneConsumer;
/**
 * @author gabriel.gutu@upb.ro
 *
 */
public class Buffer {
	int a = -1;

	void put(int value) {
		synchronized(this) {
			while (a >= 0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			a = value;
			this.notify();
		}
	}

	int get() {
		synchronized(this) {
			while (a == -1) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//resetare buffer
			int a2 = a;
			a = -1;
			this.notify();
			return  a2;
		}
	}

}
