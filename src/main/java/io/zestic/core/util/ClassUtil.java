package io.zestic.core.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClassUtil {

    static Set<Class<?>> getImplementation(Class<?> clazz) {

        Set<Class<?>> res = new HashSet<>();
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            res.addAll(Arrays.asList(interfaces));
            for (Class<?> interfaze : interfaces) {
                res.addAll(getImplementation(interfaze));
            }
        }
        return res;
    }
}
