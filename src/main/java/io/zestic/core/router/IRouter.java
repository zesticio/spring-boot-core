package io.zestic.core.router;

import io.zestic.core.annotation.NotNull;

import java.util.List;

@FunctionalInterface
public interface IRouter<T> {

    @NotNull
    T selectPeer(List<T> list);
}
