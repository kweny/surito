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

import org.apenk.surito.aide.exception.CloneFailedException;
import org.apenk.surito.aide.mutable.MutableInt;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     * @param items 可比较的对象集
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
    public static <T extends Comparable<? super T>> T max(final T... items) {
        T result = null;
        if (items != null && items.length > 0) {
            for (final T item : items) {
                if (compare(item, result, false) > 0) {
                    result = item;
                }
            }
        }
        return result;
    }

    /**
     * <p>在可比较的对象集中找到“最佳猜测”的中间值，如果总数为偶数，将返回两个中间值中的较低者。</p>
     *
     * @param items 可比较的对象集
     * @param <T> 参数类型
     * @return 中间位置的对象
     * @throws NullPointerException 当对象集为 {@code null} 时抛出此异常。
     * @throws IllegalArgumentException 当对象集为 {@code empty} 或包含 {@code null} 元素时抛出此异常。
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> T median(final T... items) {
        // TODO-Kweny Validate
//        Validate.notEmpty(items, "null/empty items");
//        Validate.noNullElements(items);
        final TreeSet<T> sort = new TreeSet<>();
        Collections.addAll(sort, items);
        @SuppressWarnings("unchecked")
        final T result = (T) sort.toArray()[(sort.size() - 1) / 2];
        return result;
    }

    /**
     * <p>在可比较的对象集中找到“最佳猜测”的中间值，如果总数为偶数，将返回两个中间值中的较低者。忽略 {@code null} 元素。</p>
     *
     * @param items 可比较的对象集
     * @param <T> 参数类型
     * @return 中间位置的对象
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> T medianIgnoreNull(final T... items) {
        if (ArrayAide.isEmpty(items)) {
            return null;
        }
        final TreeSet<T> sort = new TreeSet<>();
        for (T item : items) {
            if (item == null) {
                continue;
            }
            sort.add(item);
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
     * @param items 可比较的对象集
     * @param <T> 参数类型
     * @return 最小的对象，比较策略取决于指定的比较器
     */
    @SafeVarargs
    public static <T> T min(Comparator<? super T> comparator, final T... items) {
        T result = null;
        if (items != null && items.length > 0) {
            for (final T item : items) {
                if (compare(item, result, comparator) < 0) {
                    result = item;
                }
            }
        }
        return result;
    }

    /**
     * <p>返回参数中的最大对象。</p>
     *
     * @param items 可比较的对象集
     * @param <T> 参数类型
     * @return 最大的对象，比较策略取决于指定的比较器
     */
    @SafeVarargs
    public static <T> T max(Comparator<? super T> comparator, final T... items) {
        T result = null;
        if (items != null && items.length > 0) {
            for (final T item : items) {
                if (compare(item, result, comparator) > 0) {
                    result = item;
                }
            }
        }
        return result;
    }

    /**
     * <p>在可比较的对象集中找到“最佳猜测”的中间值，如果总数为偶数，将返回两个中间值中的较低者。</p>
     *
     * @param comparator 比较器
     * @param items 可比较的对象集
     * @param <T> 参数类型
     * @return 中间位置的对象
     * @throws NullPointerException 当对象集或者比较器为 {@code null} 时抛出此异常。
     * @throws IllegalArgumentException 当对象集为 {@code empty} 或包含 {@code null} 元素时抛出此异常。
     */
    @SafeVarargs
    public static <T> T median(Comparator<? super T> comparator, final T... items) {
        // TODO-Kweny Validate
//        Validate.notEmpty(items, "null/empty items");
//        Validate.noNullElements(items);
//        Validate.notNull(comparator, "null comparator");
        final TreeSet<T> sort = new TreeSet<>(comparator);
        Collections.addAll(sort, items);
        @SuppressWarnings("unchecked")
        final T result = (T) sort.toArray()[(sort.size() - 1) / 2];
        return result;
    }

    /**
     * <p>在可比较的对象集中找到“最佳猜测”的中间值，如果总数为偶数，将返回两个中间值中的较低者。忽略 {@code null} 元素。</p>
     *
     * @param comparator 比较器
     * @param items 可比较的对象集
     * @param <T> 参数类型
     * @return 中间位置的对象
     * @throws NullPointerException 当比较器为 {@code null} 时抛出此异常。
     */
    @SafeVarargs
    public static <T> T medianIgnoreNull(Comparator<? super T> comparator, final T... items) {
        // TODO-Kweny Validate
//        Validate.notNull(comparator, "null comparator");
        if (ArrayAide.isEmpty(items)) {
            return null;
        }
        final TreeSet<T> sort = new TreeSet<>(comparator);
        for (T item : items) {
            if (item != null) {
                sort.add(item);
            }
        }
        @SuppressWarnings("unchecked")
        final T result = (T) sort.toArray()[(sort.size() - 1) / 2];
        return result;
    }
    // ------ Compare ------ ending



    // ----- Mode ----- beginning
    /**
     * <p>从指定集合中查找最多出现的元素。如果集合为空，或有多个出现次数一样的元素，则返回 {@code null}。</p>
     *
     * @param items 指定集合
     * @param <T> 元素类型
     * @return 出现次数最多的元素，如果非唯一或集合为空，则返回 {@code null}
     */
    @SafeVarargs
    public static <T> T mode(final T... items) {
        if (ArrayAide.isNotEmpty(items)) {
            final HashMap<T, MutableInt> occurrences = new HashMap<>(items.length);
            for (final T item : items) {
                final MutableInt count = occurrences.computeIfAbsent(item, k -> new MutableInt(0));
                count.increment();
            }
            T result = null;
            int max = 0;
            for (final Map.Entry<T, MutableInt> entry : occurrences.entrySet()) {
                final int cmp = entry.getValue().intValue();
                if (cmp == max) {
                    result = null;
                } else if (cmp > max) {
                    max = cmp;
                    result = entry.getKey();
                }
            }
            return result;
        }
        return null;
    }
    // ----- Mode ----- ending



    // ----- Clone ----- beginning
    /**
     * <p>克隆对象</p>
     *
     * @param src 要克隆的源对象，当为 {@code null} 时返回 {@code null}
     * @param <T> 对象类型
     * @return 如果对象实现了 {@link Cloneable} 则返回其克隆，否则返回 {@code null}
     * @throws CloneFailedException 当对象可克隆（cloneable）但克隆失败时抛出此异常
     */
    public static <T> T clone(final T src) {
        if (src instanceof Cloneable) {
            final Object result;
            if (src.getClass().isArray()) {
                final Class<?> componentType = src.getClass().getComponentType();
                if (!componentType.isPrimitive()) {
                    result = ((Object[]) src).clone();
                } else {
                    int length = Array.getLength(src);
                    result = Array.newInstance(componentType, length);
                    while (length-- > 0) {
                        Array.set(result, length, Array.get(src, length));
                    }
                }
            } else {
                try {
                    final Method clone = src.getClass().getMethod("clone");
                    result = clone.invoke(src);
                } catch (NoSuchMethodException e) {
                    throw new CloneFailedException("Cloneable type " + src.getClass().getName() + " has no clone method", e);
                } catch (IllegalAccessException e) {
                    throw new CloneFailedException("Cannot clone Cloneable type " + src.getClass().getName(), e);
                } catch (InvocationTargetException e) {
                    throw new CloneFailedException("Exception cloning Cloneable type " + src.getClass().getName(), e.getCause());
                }
            }
            @SuppressWarnings("unchecked")
            final T checked = (T) result;
            return checked;
        }
        return null;
    }

    /**
     * <p>如果可能的话，则创建克隆。</p>
     *
     * <p>此方法类似于 {@link #clone(Object)}，不同的是，如果实例不可克隆，此方法将返回提供的实例，而不是 {@code null}。</p>
     *
     * @param src 要克隆的源对象，当为 {@code null} 时返回 {@code null}
     * @param <T> 对象类型
     * @return 如果对象实现了 {@link Cloneable} 则返回其克隆，否则返回对象本身
     * @throws CloneFailedException 当对象可克隆（cloneable）但克隆失败时抛出此异常
     */
    public static <T> T cloneIfPossible(final T src) {
        final T clone = clone(src);
        return clone == null ? src : clone;
    }
    // ----- Clone ----- ending



    // ----- Constants ----- beginning
    /*
        这些 CONST 方法用于确保 javac 在编译时不会对常量进行内联优化。
        例如开发人员通常会这样声明一个常量：

            public final static int MAGIC_NUMBER = 5;

        如果将来更改了 MAGIC_NUMBER 的值，那么其它引用了此常量的 jar 文件都需要重新编译。
        这是因为 javac 在编译时，通常会将基本类型或 String 类型的常量值直接内联到字节码中，并删除对 MAGIC_NUMBER 字段的引用。

        为了使得其它引用了常量的 jar 文件在常量值更改时无需重新编译，开发人员可以使用以下 CONST 方法来声明常量。
     */

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static String MAGIC_STRING = ObjectAide.CONST("abc");
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的常量值
     * @param <T> 常量的类型
     * @return 将指定的常量值原封不动的返回
     */
    public static <T> T CONST(final T value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static boolean MAGIC_BOOLEAN = ObjectAide.CONST(true);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 boolean 常量值
     * @return 将指定的 boolean 值原封不动的返回
     */
    public static boolean CONST(final boolean value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static byte MAGIC_BYTE = ObjectAide.CONST((byte) 127);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 byte 常量值
     * @return 将指定的 byte 值原封不动的返回
     */
    public static byte CONST(final byte value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static byte MAGIC_BYTE = ObjectAide.CONST(127);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 byte（作为 int 类型） 常量值
     * @return 将指定的 byte 值原封不动的返回
     * @throws IllegalArgumentException 当传入的值超出 byte 类型的范围，即小于 -128 或大于 127 时抛出此异常
     */
    public static byte CONST_BYTE(final int value) throws IllegalArgumentException {
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Supplied value must be a valid byte literal between -128 and 127: [" + value + "]");
        }
        return (byte) value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static byte MAGIC_CHAR = ObjectAide.CONST('a');
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 char 常量值
     * @return 将指定的 char 值原封不动的返回
     */
    public static char CONST(final char value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static byte MAGIC_SHORT = ObjectAide.CONST((short) 123);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 short 常量值
     * @return 将指定的 short 值原封不动的返回
     */
    public static short CONST(final short value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static byte MAGIC_SHORT = ObjectAide.CONST(123);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 short（作为 int 类型） 常量值
     * @return 将指定的 short 值原封不动的返回
     * @throws IllegalArgumentException 当传入的值超出 short 类型的范围，即小于 -32768 或大于 32767 时抛出此异常
     */
    public static short CONST_SHORT(final int value) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Supplied value must be a valid byte literal between -32768 and 32767: [" + value + "]");
        }
        return (short) value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static byte MAGIC_INT = ObjectAide.CONST(123);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 int 常量值
     * @return 将指定的 int 值原封不动的返回
     */
    public static int CONST(final int value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static long MAGIC_LONG = ObjectAide.CONST(123L);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 long 常量值
     * @return 将指定的 long 值原封不动的返回
     */
    public static long CONST(final long value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static float MAGIC_FLOAT = ObjectAide.CONST(0.5f);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 float 常量值
     * @return 将指定的 float 值原封不动的返回
     */
    public static float CONST(final float value) {
        return value;
    }

    /**
     * <p>此方法会原封不动地将提供的参数返回。用于防止 javac 编译时内联常量字段。例如：</p>
     *
     * <pre>
     *     public final static double MAGIC_DOUBLE = ObjectAide.CONST(0.5);
     * </pre>
     *
     * 这样当将来更改这个常量的值时，引用该字段的 jar 文件无需重新编译。
     *
     * @param value 要定义的 double 常量值
     * @return 将指定的 double 值原封不动的返回
     */
    public static double CONST(final double value) {
        return value;
    }
    // ----- Constants ----- ending



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