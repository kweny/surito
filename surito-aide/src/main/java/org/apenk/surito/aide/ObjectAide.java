/*
 * Copyright (C) 2020 Apenk.
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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * <p>操作 {@link Object} 的工具类。</p>
 *
 * <p>直接搬运 Apache Commons Lang3 的 ObjectUtils 类的代码。
 * 部分方法的签名和逻辑有所调整或重新实现。
 * 并在此基础上扩展新了的方法。</p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class ObjectAide {

    private static final char AT_SIGN = '@';

    /**
     * <p>当 {@code null} 有多个意义时，用于表示 {@code null} 的占位符。</p>
     *
     * <p>例如，在 {@link java.util.HashMap} 中，
     * 当使用 {@link java.util.HashMap#get(java.lang.Object)} 方法返回 {@code null} 时，
     * 可能是这个 Map 中包含了一个 {@code null}，也可能是没有匹配的键
     * 而 {@code NULL} 占位符则可用于区分这两种情况。</p>
     *
     * <p>此外，如 {@link java.util.Hashtable}、{@link java.util.concurrent.ConcurrentHashMap} 等无法存储 {@code null}，
     * 也可以使用该实例作为占位符。</p>
     *
     * <p>该实例可以被序列化。</p>
     */
    public static final Null NULL = new Null();

    public ObjectAide() {
        super();
    }



    // ----- null checks ----- beginning
    /**
     * <p>判断对象是否为 {@code null}。</p>
     *
     * @param object 一个对象
     * @return 参数为 {@code null} 时返回 {@code true}，否则 {@code false}
     * @see Objects#isNull(Object)
     */
    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    /**
     * <p>判断对象是否非 {@code null}。</p>
     *
     * @param object 一个对象
     * @return 参数不为 {@code null} 时返回 {@code true}，否则 {@code false}
     * @see Objects#nonNull(Object)
     */
    public static boolean isNotNull(Object object) {
        return Objects.nonNull(object);
    }

    /**
     * <p>返回数组中第一个非 {@code null} 的元素。</p>
     *
     * <p>如果数组为 {@code null} 或 {@code empty} 或数组中没有非 {@code null} 的值，则返回 {@code null}。</p>
     *
     * @param values 要检验的数组，可以为 {@code null} 或 {@code empty}
     * @param <T> 数组元素的类型
     * @return {@code values} 中第一个非 {@code null} 的值，或 {@code null}
     */
    @SafeVarargs
    public static <T> T firstNonNull(final T... values) {
        if (values != null && values.length > 0) {
            for (final T value : values) {
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * <p>检查指定数组中是否包含 {@code null} 元素。</p>
     *
     * <p>如果数组为 {@code null} 或 {@code empty} 或数组中包含 {@code null} 时返回 {@code true}，否则返回 {@code false}。</p>
     *
     * @param values 要检查的数组，可以为 {@code null} 或 {@code empty}
     * @return 如果数组为 {@code null} 或 {@code empty} 或数组中包含 {@code null} 时返回 {@code true}，否则返回 {@code false}
     */
    public static boolean anyNull(final Object... values) {
        if (values == null || values.length == 0) {
            return true;
        }
        for (final Object value : values) {
            if (value == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>检查指定数组中是否包含不为 {@code null} 的元素。</p>
     *
     * <p>如果数组中包含至少一个非 {@code null} 的元素，则返回 {@code true}，
     * 如果数组为 {@code null} 或 {@code empty} 或数组中的所有元素都是 {@code null} 则返回 {@code false}。</p>
     *
     * <p>如果数组为 {@code null} 或 {@code empty} 或数组中包含 {@code null} 时返回 {@code true}，否则返回 {@code false}。</p>
     *
     * @param values 要检查的数组，可以为 {@code null} 或 {@code empty}
     * @return 如果数组中包含至少一个非 {@code null} 的元素，则返回 {@code true}；如果数组为 {@code null} 或 {@code empty} 或数组中的所有元素都是 {@code null} 则返回 {@code false}
     */
    public static boolean anyNonNull(final Object... values) {
        return firstNonNull(values) != null;
    }

    /**
     * <p>检查数组中的元素是否都不是 {@code null}。</p>
     *
     * <p>如果数组为 {@code null} 或 {@code empty} 或者数组中的所有元素都是 {@code null} 时返回 {@code true}，否则返回 {@code false}。</p>
     *
     * @param values 受检数组，可以为 {@code null}
     * @return 如果数组为 {@code null} 或 {@code empty} 或者数组中的所有元素都是 {@code null} 时返回 {@code true}；如果数组中包含非 {@code null} 的元素则返回 {@code false}
     */
    public static boolean allNull(final Object... values) {
        if (values == null || values.length == 0) {
            return true;
        }
        for (final Object value : values) {
            if (value != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>检查数组中的所有元素是否都不是 {@code null}。</p>
     *
     * <p>如果数组即不是 {@code null} 也不是 {@code empty}，且所有元素都不是 {@code null} 时返回 {@code true}，否则返回 {@code false}。</p>
     * <p>如果数组为 {@code null} 或 {@code empty} 或者数组中的所有元素都是 {@code null} 时返回 {@code true}，否则返回 {@code false}。</p>
     *
     * @param values 受检数组，可以为 {@code null}
     * @return 如果数组即不是 {@code null} 也不是 {@code empty}，且所有元素都不是 {@code null} 时返回 {@code true}；如果数组为 {@code null} 或 {@code empty} 或者包含 {@code null} 元素则返回 {@code false}。
     */
    public static boolean allNonNull(final Object... values) {
        if (values == null || values.length == 0) {
            return false;
        }
        for (final Object value : values) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }
    // ----- null checks ----- ending



    // ----- Empty checks ----- beginning
    /**
     * <p>检查一个对象是否为空：{@code null} 或 {@code empty}。</p>
     *
     * <p>
     * 支持以下类型：
     * <ul>
     *     <li>{@link CharSequence}：如果长度为 0，则视为空。</li>
     *     <li>{@code array}：如果长度为 0，则视为空。</li>
     *     <li>{@link Collection}：如果元素数为 0，则视为空。</li>
     *     <li>{@link Map}：如果 key-value 映射数为 0，则视为空。</li>
     * </ul>
     * </p>
     *
     * @param object 要检查的对象，可以为 {@code null}。
     * @return 如果受检对象是被支持的类型且为空，则返回 {@code true}；否则返回 {@code false}
     */
    public static boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }
        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }
        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        return false;
    }

    /**
     * <p>检查一个对象是否不为空：非 {@code null} 且非 {@code empty}。</p>
     *
     * <p>
     * 支持以下类型：
     * <ul>
     *     <li>{@link CharSequence}：如果长度为 0，则视为空。</li>
     *     <li>{@code array}：如果长度为 0，则视为空。</li>
     *     <li>{@link Collection}：如果元素数为 0，则视为空。</li>
     *     <li>{@link Map}：如果 key-value 映射数为 0，则视为空。</li>
     * </ul>
     * </p>
     *
     * @param object 要检查的对象，可以为 {@code null}
     * @return 如果受检对象是被支持的类型且不为空，或者受检对象不是被支持的类型，则返回 {@code true}；否则返回 {@code false}
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }
    // ----- Empty checks ----- ending



    // ----- Defaulting ----- beginning
    /**
     * <p>当第一个参数 {@code object} 为 {@code null} 时，则返回指定的第二个参数 {@code defaultValue} 作为默认值。</p>
     *
     * @param object 受检对象，可以为 {@code null}
     * @param defaultValue 默认值
     * @param <T> 对象的类型
     * @return 如果 {@code object} 为 {@code null}，则返回 {@code defaultValue}；否则返回 {@code object}。
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }
    // ----- Defaulting ----- ending

    /**
     * <p>当 {@code null} 有多个意义时，用于表示 {@code null} 的占位符。</p>
     *
     * <p>例如，在 {@link java.util.HashMap} 中，
     * 当使用 {@link java.util.HashMap#get(java.lang.Object)} 方法返回 {@code null} 时，
     * 可能是这个 Map 中包含了一个 {@code null}，也可能是没有匹配的键
     * 而 {@link Null} 占位符则可用于区分这两种情况。</p>
     *
     * <p>此外，如 {@link java.util.Hashtable}、{@link java.util.concurrent.ConcurrentHashMap} 等无法存储 {@code null}，
     * 也可以使用该实例作为占位符。</p>
     */
    public static class Null implements Serializable {
        private static final long serialVersionUID = -493756490864193159L;

        /**
         * <p>返回单例 {@link ObjectAide#NULL ObjectAide.NULL}。</p>
         *
         * @return {@link ObjectAide#NULL ObjectAide.NULL}
         */
        public static Null singleton() {
            return ObjectAide.NULL;
        }

        /**
         * <p>创建一个新的实例。</p>
         *
         * @return 一个新的 {@link Null} 实例
         */
        public static Null newInstance() {
            return new Null();
        }

        /**
         * 构造方法
         */
        public Null() {
            super();
        }
    }
}