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
import java.util.*;

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

    /**
     * <p>{@link ObjectAide} 的实例不应该在标准编程中构造，而应该直接使用该类的静态方法，
     * 如 {@link ObjectAide#defaultIfNull(Object, Object) ObjectAide.defaultIfNull(object, object)}。</p>
     *
     * <p>该构造方法是 public 的，允许需要对 JavaBean 进行操作的工具使用。</p>
     *
     */
    public ObjectAide() {
        super();
    }



    // ----- Null checks ----- beginning
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
     * <p>判断对象是否 {@code non-null}。</p>
     *
     * @param object 一个对象
     * @return 参数不为 {@code null} 时返回 {@code true}，否则 {@code false}
     * @see Objects#nonNull(Object)
     */
    public static boolean isNotNull(Object object) {
        return Objects.nonNull(object);
    }

    /**
     * <p>返回数组中第一个 {@code non-null} 元素。</p>
     *
     * <p>如果数组为 {@code null} 或 {@code empty} 或数组中没有 {@code non-null} 的值，则返回 {@code null}。</p>
     *
     * @param values 要检验的数组，可以为 {@code null} 或 {@code empty}
     * @param <T> 数组元素的类型
     * @return {@code values} 中第一个 {@code non-null} 的元素
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
     * <p>检查指定数组中是否包含 {@code non-null} 的元素。</p>
     *
     * <p>如果数组中包含至少一个 {@code non-null} 的元素，则返回 {@code true}，
     * 如果数组为 {@code null} 或 {@code empty} 或数组中的所有元素都是 {@code null} 则返回 {@code false}。</p>
     *
     * @param values 要检查的数组，可以为 {@code null} 或 {@code empty}
     * @return 如果数组中包含至少一个 {@code non-null} 的元素，则返回 {@code true}；如果数组为 {@code null} 或 {@code empty} 或数组中的所有元素都是 {@code null} 则返回 {@code false}
     */
    public static boolean anyNonNull(final Object... values) {
        return firstNonNull(values) != null;
    }

    /**
     * <p>检查数组中的元素是否都 {@code non-null}。</p>
     *
     * <p>如果数组为 {@code null} 或 {@code empty} 或者数组中的所有元素都是 {@code null} 时返回 {@code true}，否则返回 {@code false}。</p>
     *
     * @param values 受检数组，可以为 {@code null}
     * @return 如果数组为 {@code null} 或 {@code empty} 或者数组中的所有元素都是 {@code null} 时返回 {@code true}；如果数组中包含 {@code non-null} 的元素则返回 {@code false}
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
     * <p>如果数组即不是 {@code null} 也不是 {@code empty}，且所有元素都 {@code non-null} 时返回 {@code true}，否则返回 {@code false}。</p>
     *
     * @param values 受检数组，可以为 {@code null}
     * @return 如果数组即不是 {@code null} 也不是 {@code empty}，且所有元素都 {@code non-null} 时返回 {@code true}；如果数组为 {@code null} 或 {@code empty} 或者包含 {@code null} 元素则返回 {@code false}。
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
    // ----- Null checks ----- ending



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
     * <p>检查一个对象是否不为空：{@code non-null} 且 {@code non-empty}。</p>
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



    // ----- Null-safe equals/hashCode ----- beginning
    /**
     * 比较两个对象是否相等。
     *
     * @param a 第一个对象，可以为 {@code null}
     * @param b 第二个对象，可以为 {@code null}
     * @return 如果两个对象相等时返回 {@code true}
     * @see Objects#equals(Object, Object)
     */
    public static boolean equals(final Object a, final Object b) {
        return Objects.equals(a, b);
    }

    /**
     * 比较两个对象是否不相等。
     *
     * @param a 第一个对象，可以为 {@code null}
     * @param b 第二个对象，可以为 {@code null}
     * @return 如果两个对象不相等时返回 {@code true}
     */
    public static boolean notEquals(final Object a, final Object b) {
        return !equals(a, b);
    }

    /**
     * <p>比较多个对象是否相等</p>
     *
     * @param values 要比较的对象
     * @return 若所有对象均相等，则返回 {@code true}，否则返回 {@code false}，
     * 			values 为 {@code null} 或  empty 或只有一个对象时返回 {@code true}
     */
    public static boolean allEquals(final Object... values) {
        if (values == null || values.length <= 1) {
            return true;
        }
        for (int i = 0; i < values.length - 1; i ++) {
            if (notEquals(values[i], values[i + 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>如果参数深层相等则返回 {@code true}，否则返回 {@code false}。两个 null 值比较时返回 {@code true}。</p>
     *
     * <p>
     *     如果两个参数为数组，则使用 {@link java.util.Arrays#deepEquals(Object[], Object[]) Arrays.deepEquals} 方法比较。
     *     否则使用第一个参数的 {@link Object#equals equals} 方法比较。
     * </p>
     *
     * @param a 第一个对象，可以为 {@code null}
     * @param b 第二个对象，可以为 {@code null}
     * @return 深层相等为 {@code true}，否则 {@code false}
     * @see Objects#deepEquals(Object, Object)
     */
    public static boolean deepEquals(Object a, Object b) {
        return Objects.deepEquals(a, b);
    }

    /**
     * <p>与 {@link #deepEquals(Object, Object)} 相反。</p>
     *
     * @param a 第一个对象，可以为 {@code null}
     * @param b 第二个对象，可以为 {@code null}
     * @return 非深层相等为 {@code true}，深层相等为 {@code false}
     * @see #deepEquals(Object, Object)
     */
    public static boolean notDeepEquals(Object a, Object b) {
        return !deepEquals(a, b);
    }

    /**
     * <p>参数为 {@code null} 时返回 0，否则返回其 hash code。</p>
     *
     * @param object 一个对象
     * @return hash code 或 0
     * @see Objects#hashCode(Object)
     */
    public static int hashCode(Object object) {
        return Objects.hashCode(object);
    }

    /**
     * <p>为多个对象生成一个 hash code。</p>
     *
     * @param objects 一个或多个对象
     * @return hash code
     * @see Objects#hash(Object...)
     */
    public static int hash(Object... objects) {
        return Objects.hash(objects);
    }
    // ----- Null-safe equals/hashCode ----- ending



    // ----- To string ----- beginning
    /**
     * <p>参数为 {@code null} 时返回字符串 {@code "null"}，否则调用其 {@code toString} 方法返回。</p>
     *
     * @param object 一个对象
     * @return {@code "null"} 或 参数的 {@code toString} 方法返回值
     * @see Objects#toString(Object)
     */
    public static String toString(Object object) {
        return Objects.toString(object);
    }

    /**
     * <p>第一个参数为 {@code null} 时返回第二个参数指定的值，否则使用第一个参数的 {@code toString} 方法返回。</p>
     *
     * @param object 一个对象
     * @param nullDefault 当 {@code object} 为 {@code null} 时的默认值
     * @return {@code nullDefault} 或 {@code object} 的 {@code toString}
     * @see Objects#toString(Object, String)
     */
    public static String toString(Object object, String nullDefault) {
        return Objects.toString(object, nullDefault);
    }

    /**
     * <p>获取对象默认的 {@code toString} 方法计算的文本，当参数为 {@code null} 时返回 {@code null}。</p>
     *
     * 格式为：
     * <pre>
     *     object.getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre>
     *
     * 如：
     * <pre>
     *     ObjectAide.identityString(null)         = null
     *     ObjectAide.identityString("")           = "java.lang.String@4ee285c6"
     *     ObjectAide.identityString(Boolean.TRUE) = "java.lang.Boolean@5b80350b"
     * </pre>
     *
     * @param object 一个对象
     * @return 对象默认 {@code toString} 文本
     */
    public static String identityString(final Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getName() + AT_SIGN + Integer.toHexString(System.identityHashCode(object));
    }
    // ----- To string ----- ending



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



    // ------ Compare ------ beginning
    /**
     * <p>比较两个对象的大小，{@code null} 小于 {@code non-null}。</p>
     *
     * @param a 一个可比较的对象
     * @param b 另一个可比较的对象
     * @param <T> 参数类型
     * @return 如果 a &lt; b 返回负数；如果 a = b 返回 0；如果 a &gt; b 返回正数
     */
    public static <T extends Comparable<? super T>> int compare(final T a, final T b) {
        return compare(a, b, false);
    }

    /**
     * <p>比较两个对象的大小。</p>
     *
     * @param a 一个可比较的对象
     * @param b 另一个可比较的对象
     * @param nullGreater 如果为 {@code true} 则认为 {@code null} 大于 {@code non-null} ，反之 {@code non-null} 大于 {@code null}。
     * @param <T> 参数类型
     * @return 如果 a &lt; b 返回负数；如果 a = b 返回 0；如果 a &gt; b 返回正数
     */
    public static <T extends Comparable<? super T>> int compare(final T a, final T b, boolean nullGreater) {
        if (a == b) {
            return 0;
        } else if (a == null) {
            return nullGreater ? 1 : -1;
        } else if (b == null) {
            return nullGreater ? -1 : 1;
        }
        return a.compareTo(b);
    }

    /**
     * <p>返回参数中的最小对象。</p>
     *
     * @param values 可比较的对象集
     * @param <T> 参数类型
     * @return
     *  <ul>
     *      <li>如果所有对象都 {@code non-null} 且不相等，返回最小对象</li>
     *      <li>如果所有对象都 {@code non-null} 且相等，返回第一个对象</li>
     *      <li>如果存在 {@code null} 对象，则返回最小的 {@code non-null} 对象</li>
     *      <li>如果所有对象都为 {@code null}，则返回 {@code null}</li>
     *  </ul>
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> T min(final T... values) {
        T result = null;
        if (values != null && values.length > 0) {
            for (final T value : values) {
                if (compare(value, result, true) < 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    /**
     * <p>返回参数中的最大对象。</p>
     *
     * @param values 可比较的对象集
     * @param <T> 参数类型
     * @return
     *  <ul>
     *      <li>如果所有对象都 {@code non-null} 且不相等，返回最大对象</li>
     *      <li>如果所有对象都 {@code non-null} 且相等，返回第一个对象</li>
     *      <li>如果存在 {@code null} 对象，则返回最大的 {@code non-null} 对象</li>
     *      <li>如果所有对象都为 {@code null}，则返回 {@code null}</li>
     *  </ul>
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> T max(final T... values) {
        T result = null;
        if (values != null && values.length > 0) {
            for (final T value : values) {
                if (compare(value, result, false) > 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    // TODO-Kweny go on
//    public static <T extends Comparable<? super T>> T median(final T... values) {
//        final TreeSet<T> sort = new TreeSet<>();
//        "".equalsIgnoreCase()
//        sort.add(null);
//    }

    // TODO-Kweny comments
    @SafeVarargs
    public static <T extends Comparable<? super T>> T medianIgnoreNull(final T... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        final TreeSet<T> sort = new TreeSet<>();
        for (T value : values) {
            if (value == null) {
                continue;
            }
            sort.add(value);
        }
        @SuppressWarnings("unchecked")
        final T result = (T) sort.toArray()[(sort.size() - 1) / 2];
        return result;
    }

    /**
     * <p>如果参数 {@code a} 和 {@code b} 相等（==），则返回 0，否则使用参数 {@code comparator} 指定的比较器进行运算并返回。</p>
     *
     * <p>注意，当参数为 {@code null} 时，是否会抛出 {@link NullPointerException}
     * 以及 {@code null} 与 {@code non-null} 的大小关系取决于所指定的比较器。</p>
     *
     * @param a 一个对象
     * @param b 一个与 {@code a} 进行比较的对象
     * @param comparator 比较器
     * @param <T> {@code a} 和 {@code b} 的类型
     * @return 0 或 使用 {@code c} 的比较结果
     * @see Objects#compare(Object, Object, Comparator)
     */
    public static <T> int compare(T a, T b, Comparator<? super T> comparator) {
        return Objects.compare(a, b ,comparator);
    }

    /**
     * <p>返回参数中的最小对象。</p>
     *
     * @param values 可比较的对象集
     * @param <T> 参数类型
     * @return 最小的对象，比较策略取决于指定的比较器
     */
    @SafeVarargs
    public static <T> T min(Comparator<? super T> comparator, final T... values) {
        T result = null;
        if (values != null && values.length > 0) {
            for (final T value : values) {
                if (compare(value, result, comparator) < 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    /**
     * <p>返回参数中的最大对象。</p>
     *
     * @param values 可比较的对象集
     * @param <T> 参数类型
     * @return 最大的对象，比较策略取决于指定的比较器
     */
    @SafeVarargs
    public static <T> T max(Comparator<? super T> comparator, final T... values) {
        T result = null;
        if (values != null && values.length > 0) {
            for (final T value : values) {
                if (compare(value, result, comparator) > 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    // TODO-Kweny go on
//    public static <T> T median(Comparator<? super T> comparator, final T... values) {
//        final TreeSet<T> sort = new TreeSet<>();
//        "".equalsIgnoreCase()
//        sort.add(null);
//    }

    // TODO-Kweny comments
    @SafeVarargs
    public static <T> T medianIgnoreNull(Comparator<? super T> comparator, final T... values) {
        if (values == null || values.length == 0) {
            return null;
        }
        final TreeSet<T> sort = new TreeSet<>(comparator);
        for (T value : values) {
            if (value == null) {
                continue;
            }
            sort.add(value);
        }
        @SuppressWarnings("unchecked")
        final T result = (T) sort.toArray()[(sort.size() - 1) / 2];
        return result;
    }
    // ------ Compare ------ ending



    // ----- Inner classes ----- beginning
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
    // ----- Inner classes ----- ending
}