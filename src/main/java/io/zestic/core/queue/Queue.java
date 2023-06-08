/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zestic.core.queue;

import io.zestic.core.pdu.Pdu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This queue will not block a thread instead it returns null. since its unbounded, it will throw
 * java.lang.OutOfMemoryError if there is no extra memory to add new elements.
 * <p>
 * Apart from being non-blocking, a ConcurrentLinkedQueue has additional functionality. In any producer-consumer
 * scenario, consumers will not contend with producers; however, multiple producers will contend with one another
 */
public class Queue {

    private static final Logger logger = LoggerFactory.getLogger(Queue.class.getSimpleName());

    private final Integer _SIZE = 1024 * 16;
    private static Lock lock;
    private static Condition notEmpty;
    private static Condition notFull;
    //public static Condition notEmpty = lock.newCondition();

    private String path;
    private byte[] key = {
            (byte) 0x45,
            (byte) 0x83,
            (byte) 0x78,
            (byte) 0x33,
            (byte) 0x21,
            (byte) 0x95,
            (byte) 0xA5,
            (byte) 0xCA,
            (byte) 0x12,
            (byte) 0x44,
            (byte) 0xFF,
            (byte) 0xD3,
            (byte) 0x04,
            (byte) 0x9A,
            (byte) 0xB2,
            (byte) 0x77};
    private ConcurrentLinkedQueue<Pdu> queue;
    private Integer counter = 0;

    /**
     * The below function will create an IndexedChronicle which creates two RandomAccessFiles one for indexes
     * and one for data having names relatively
     * <p>
     * A Chronicle Queue is a logical view of a directory on the file-system. The queue data itself is split across
     * multiple files, each of which contains data belonging to a single cycle. The length of the cycle is determined
     * by the rollCycle parameter passed to the queue builder.
     * <p>
     * Any further instance ChronicleQueue configured to use the same path to use the same roll-cycle. Trying to set
     * this option twice will throw an exception.
     */
    public void create() {
        queue = new ConcurrentLinkedQueue<>();
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    /**
     * Messages are written or appended to a queue using an appender. The appender writes messages to the end of
     * the queue only, there is no way to insert messages at a specific location.
     */
    public void enqueue(Pdu pdu) {
        lock.lock(); // Acquire the lock
        try {
            while (queue.size() >= _SIZE) {
                logger.warn("Wait until there is room in the queue.");
                notFull.await();
            }
            queue.add(pdu);
            notEmpty.signal(); // Signal notEmpty condition
        } catch (InterruptedException ex) {
            logger.error("", ex);
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    /**
     * Reading from a queue is easiest done using a tailer namely ExcerptTailer. The term tailing stems from reading
     * from the queue's tail, i.e the end of the queue.
     */
    public Pdu dequeue() {
        Pdu result = null;
        lock.lock(); // Acquire the lock
        try {
            while (queue.isEmpty()) {
                logger.warn("Wait until there is room in the queue.");
                notEmpty.await();
            }
            result = queue.poll();
            notFull.signal(); // Signal notFull condition
        } catch (InterruptedException ex) {
            logger.error("", ex);
        } finally {
            lock.unlock(); // Release the lock
        }
        return result;
    }

    /**
     * Chronicle Queue stores its data off-heap, therefore it is recommended that you call .close()_ once you have
     * finished working with Chronicle Queue to free up resources.
     */
    public void close() {
        queue.clear();
    }
}
