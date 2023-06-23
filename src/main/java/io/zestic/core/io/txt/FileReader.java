package io.zestic.core.io.txt;

import io.zestic.core.util.IBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileReader implements Closeable {

    private final Reader reader;
    private final Subscriber subscriber;
    private List<String> list;

    public FileReader(Builder builder) {
        this.reader = builder.reader;
        this.subscriber = builder.subscriber;
        this.list = new LinkedList<>();
    }

    protected void readAll() {
        if (reader == null) {
            if (subscriber != null) subscriber.onError("internal error, unable to create linked list");
            return;
        }
        if (list == null) {
            if (subscriber != null) subscriber.onError("internal error, unable to create linked list");
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            while (bufferedReader.ready()) {
                list.add(bufferedReader.readLine());
            }
        } catch (IOException exception) {
            if (subscriber != null) {
                subscriber.onError(exception.getMessage());
            }
        }
    }

    protected void process() {
        list.forEach(new LineReader(subscriber));
        //lets call garbage collector and release unused objects
        Runtime.getRuntime().gc();
        subscriber.onComplete();
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    public static class Builder implements IBuilder<FileReader> {

        private String uri;
        private Subscriber subscriber;
        private Reader reader;

        public Builder path(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder subscriber(Subscriber subscriber) {
            this.subscriber = subscriber;
            return this;
        }

        @Override
        public FileReader build() {
            return build(uri, StandardCharsets.UTF_8);
        }

        public FileReader build(final String uri, final Charset charset) {
            //Objects.requireNonNull(this.uri, "path must not be null");
            if (uri == null) {
                if (subscriber != null) subscriber.onError("path must not be null");
            }
            final Path path = Paths.get(uri);
            FileReader fileReader = null;
            try {
//                this.reader = (Reader) Proxy.newProxyInstance(
//                        FileReader.class.getClassLoader(),
//                        new Class[]{Reader.class},
//                        new TimingDynamicInvocationHandler(new InputStreamReader(Files.newInputStream(path), charset)));
                this.reader = new InputStreamReader(Files.newInputStream(path), charset);
                fileReader = new FileReader(this);
                fileReader.readAll();
                fileReader.process();
            } catch (IOException e) {
                if (subscriber != null) this.subscriber.onError(e.getMessage());
            }
            return fileReader;
        }
    }

    public interface Subscriber {

        void onNext(String line);
        void onError(String description);
        void onComplete();
    }
}
