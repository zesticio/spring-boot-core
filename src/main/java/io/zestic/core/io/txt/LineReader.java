package io.zestic.core.io.txt;

import java.util.function.Consumer;

public final class LineReader implements Consumer<String> {

    private final FileReader.Subscriber subscriber;

    public LineReader(final FileReader.Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void accept(String s) {
        subscriber.onNext(s);
    }
}
