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

import io.zestic.core.util.IBuilder;

/**
 * This queue will not block a thread instead it returns null. since its unbounded, it will throw
 * java.lang.OutOfMemoryError if there is no extra memory to add new elements.
 * <p>
 * Apart from being non-blocking, a ConcurrentLinkedQueue has additional functionality. In any producer-consumer
 * scenario, consumers will not contend with producers; however, multiple producers will contend with one another
 */
public class QueueBuilder implements IBuilder<Queue> {

    @Override
    public Queue build() {
        Queue queue = new Queue();
        queue.create();
        return queue;
    }
}
