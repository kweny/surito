/*
 * Copyright (C) 2018 Apenk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apenk.surito.aide;

import java.lang.reflect.Array;

/**
 * <p>操作数组的工具类。</p>
 *
 * <p>直接搬运 Apache Commons Lang3 的 ArrayUtils 类的代码。
 * 部分方法的签名和逻辑有所调整或重新实现。
 * 并在此基础上扩展新了的方法。</p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class ArrayAide {

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static <T> boolean isEmpty(final T[] array) {
        return getLength(array) == 0;
    }

    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }
}