package io.zestic.core.io;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.concurrent.Flow;
import java.util.function.Supplier;

/**
 * Represents a file reading subscription.
 *
 * @author Alexey Zhytnik
 */
public interface ReadSubscription extends Flow.Subscription {

    /**
     * Installs a path to file for reading.
     *
     * @param path the path to file
     */
    void setPath(Path path);

    /**
     * Installs memory allocator which provides a memory for file reading.
     * Each invocation of memory allocator should return a ByteBuffer whose bytes
     * from position to limit (exclusive) will be used for writing file content,
     * but not all that bytes could be used, limit position could be decreased.
     *
     * @param allocator the memory allocator
     */
    void setAllocator(Supplier<ByteBuffer> allocator);

    /**
     * Adds bytes for reading. Needs installed path and memory allocator,
     * otherwise throws {@link IllegalStateException}.
     * A value of {@code Long.MAX_VALUE} is request to read all file,
     * in other cases if requested byte count is negative or greater
     * than the file's size then {@link IllegalArgumentException} will be thrown.
     *
     * @param bytes the additional count of bytes for read
     */
    @Override
    void request(long bytes);

    /**
     * Stops reading, all used resources will be released after invoking.
     */
    @Override
    void cancel();
}