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
package org.apenk.surito.aide.mutable;

/**
 * <p>可变的 int 类型包装。</p>
 *
 * <p>注 1，MutableInt 并没有继承和扩展 {@link Integer} 类型，因此 {@link String#format(String, Object...) 不会将其视为 {@link Integer} 参数}。</p>
 *
 * <p>注 2，该类非线程安全，并非 {@link java.util.concurrent.atomic.AtomicInteger} 原子操作。</p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class MutableInt extends MutableNumber<Number> {
    private static final long serialVersionUID = -6579174585762474609L;

    /**
     * 可变的值
     */
    private int value;

    // ----- 构造方法 ----- beginning
    /**
     * 使用默认值 0 创建一个 MutableInt 实例。
     */
    public MutableInt() {
        super();
    }

    /**
     * 使用指定值创建一个 MutableInt 实例。
     *
     * @param value 初始值
     */
    public MutableInt(final int value) {
        super();
        this.value = value;
    }

    /**
     * 使用 {@link Number} 类型的指定值创建一个 MutableInt 实例。
     *
     * @param value 初始值，不能为 {@code null}
     * @throws NullPointerException 如果指定的值为 {@code null} 将抛出此异常
     */
    public MutableInt(final Number value) {
        super();
        this.value = value.intValue();
    }

    /**
     * 使用 {@link Number} 类型的指定值创建一个 MutableInt 实例，若指定值为 {@code null}，则使用第二个参数作为值。
     *
     * @param value 初始值，若为 {@code null} 则使用第二个参数作为默认值
     * @param defaultValue 当第一个参数为 {@code null} 时的默认值
     */
    public MutableInt(final Number value, final int defaultValue) {
        super();
        this.value = value != null ? value.intValue() : defaultValue;
    }

    /**
     * 通过指定的字符串解析为初始值创建一个 MutableInt 实例。
     *
     * @param value 要解析的字符串，不能为 {@code null}
     * @throws NumberFormatException 如果指定的字符串无法解析为 int 值时抛出此异常
     */
    public MutableInt(final String value) {
        super();
        this.value = Integer.parseInt(value);
    }

    /**
     * 通过指定的字符串解析为初始值创建一个 MutableInt 实例，若字符串无法解析为 int 值，则使用第二个参数作为值。
     *
     * @param value 要解析的字符串，若无法解析为 int 值时则使用第二个参数作为默认值
     * @param defaultValue 当指定字符串无法解析为 int 值时的默认值
     */
    public MutableInt(final String value, final int defaultValue) {
        super();
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.value = defaultValue;
        }
    }
    // ----- 构造方法 ----- ending



    // ----- Set/Get ----- beginning
    /**
     * 使用一个 {@link Number} 实例设置值。
     *
     * @param value 要设置的值，不能为 {@code null}
     * @throws NullPointerException 当指定的值为 {@code null} 时抛出此异常
     */
    @Override
    public void setValue(final Number value) {
        this.value = value.intValue();
    }

    /**
     * 使用一个 {@link Number} 实例设置值，若为 {@code null}，则使用第二个参数作为默认值。
     *
     * @param value 要设置的值，若为 {@code null} 则使用第二个参数作为默认值
     * @param defaultValue 当指定值为 {@code null} 时的默认值
     */
    public void setValue(final Number value, final int defaultValue) {
        this.value = value != null ? value.intValue() : defaultValue;
    }

    /**
     * 设置值
     *
     * @param value 要设置的值
     */
    public void setValue(final int value) {
        this.value = value;
    }

    /**
     * 以 {@link Integer} 实例的形式获取值。
     *
     * @return 值得 {@link Integer} 实例，不会为 {@code null}
     */
    @Override
    public Integer getValue() {
        return Integer.valueOf(this.value);
    }

    /**
     * 以 int 类型返回该 MutableInt 实例的值
     *
     * @return int 类型的值
     */
    @Override
    public int intValue() {
        return value;
    }

    /**
     * 以 long 类型返回该 MutableInt 实例的值
     *
     * @return long 类型的值
     */
    @Override
    public long longValue() {
        return value;
    }

    /**
     * 以 float 类型返回该 MutableInt 实例的值
     *
     * @return float 类型的值
     */
    @Override
    public float floatValue() {
        return value;
    }

    /**
     * 以 double 类型返回该 MutableInt 实例的值
     *
     * @return double 类型的值
     */
    @Override
    public double doubleValue() {
        return value;
    }
    // ----- Set/Get ----- ending



    // ----- Increment/Decrement ----- beginning
    /**
     * 实例值递增 1
     */
    public void increment() {
        value++;
    }

    /**
     * 对实例值递增 1 并返回。
     *
     * @return 递增之后的实例值
     */
    public int incrementAndGet() {
        value++;
        return value;
    }

    /**
     * 返回当前实例值，并对实例值递增 1。
     *
     * @return 递增之前的实例值
     */
    public int getAndIncrement() {
        final int last = value;
        value++;
        return last;
    }

    /**
     * 实例值递减 1
     */
    public void decrement() {
        value--;
    }

    /**
     * 对实例值递减 1 并返回。
     *
     * @return 递减之后的实例值
     */
    public int decrementAndGet() {
        value++;
        return value;
    }

    /**
     * 返回当前实例值，并对实例值递减 1。
     *
     * @return 递减之前的实例值
     */
    public int getAndDecrement() {
        final int last = value;
        value--;
        return last;
    }
    // ----- Increment/Decrement ----- ending



    // ----- Add ----- beginning
    /**
     * 加法：使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数
     * @return 当前实例
     */
    public MutableInt add(final int operand) {
        this.value += operand;
        return this;
    }

    /**
     * 加法：使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当加数为 {@code null} 时抛出此异常
     */
    public MutableInt add(final Number operand) {
        this.value += operand.intValue();
        return this;
    }

    /**
     * 加法：使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，若为 {@code null} 则使用第二个参数作为加数
     * @param defaultOperand 当加数为 {@code null} 时，使用该值作为加数
     * @return 当前实例
     */
    public MutableInt add(final Number operand, final int defaultOperand) {
        this.value += (operand != null ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 加法：使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当加数为 {@code null} 时抛出此异常
     */
    public MutableInt add(final MutableNumber<?> operand) {
        this.value += operand.intValue();
        return this;
    }

    /**
     * 加法：使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，若为 {@code null} 则使用第二个参数作为加数
     * @param defaultOperand 当加数为 {@code null} 时，使用该值作为加数
     * @return 当前实例
     */
    public MutableInt add(final MutableNumber<?> operand, final int defaultOperand) {
        this.value += (operand != null ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 对实例值使用指定加数进行加法运算后返回。
     *
     * @param operand 加数
     * @return 加法运算后的实例值
     */
    public int addAndGet(final int operand) {
        this.value += operand;
        return value;
    }

    /**
     * 对实例值使用指定加数进行加法运算后返回。
     *
     * @param operand 加数，不能为 {@code null}
     * @return 加法运算后的实例值
     * @throws NullPointerException 当加数为 {@code null} 时抛出此异常
     */
    public int addAndGet(final Number operand) {
        this.value += operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定加数进行加法运算后返回。
     *
     * @param operand 加数，若为 {@code null} 则使用第二个参数作为加数
     * @param defaultOperand 当加数为 {@code null} 时，使用该值作为加数
     * @return 加法运算后的实例值
     */
    public int addAndGet(final Number operand, final int defaultOperand) {
        this.value += (operand != null ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 对实例值使用指定加数进行加法运算后返回。
     *
     * @param operand 加数，不能为 {@code null}
     * @return 加法运算后的实例值
     * @throws NullPointerException 当加数为 {@code null} 时抛出此异常
     */
    public int addAndGet(final MutableNumber<?> operand) {
        this.value += operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定加数进行加法运算后返回。
     *
     * @param operand 加数，若为 {@code null} 则使用第二个参数作为加数
     * @param defaultOperand 当加数为 {@code null} 时，使用该值作为加数
     * @return 加法运算后的实例值
     */
    public int addAndGet(final MutableNumber<?> operand, final int defaultOperand) {
        this.value += (operand != null ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 返回当前实例值，并使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数
     * @return 加法运算之前的实例值
     */
    public int getAndAdd(final int operand) {
        final int last = value;
        this.value += operand;
        return last;
    }

    /**
     * 返回当前实例值，并使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，不能为 {@code null}
     * @return 加法运算之前的实例值
     * @throws NullPointerException 当加数为 {@code null} 时抛出此异常
     */
    public int getAndAdd(final Number operand) {
        final int last = value;
        this.value += operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，若为 {@code null} 则使用第二个参数作为加数
     * @param defaultOperand 若加数为 {@code null} 则使用该值作为加数
     * @return 加法运算之前的实例值
     */
    public int getAndAdd(final Number operand, final int defaultOperand) {
        final int last = value;
        this.value += (operand != null ? operand.intValue() : defaultOperand);
        return last;
    }

    /**
     * 返回当前实例值，并使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，不能为 {@code null}
     * @return 加法运算之前的实例值
     * @throws NullPointerException 当加数为 {@code null} 时抛出此异常
     */
    public int getAndAdd(final MutableNumber<?> operand) {
        final int last = value;
        this.value += operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定加数对实例值进行加法运算。
     *
     * @param operand 加数，若为 {@code null} 则使用第二个参数作为加数
     * @param defaultOperand 若加数为 {@code null} 则使用该值作为加数
     * @return 加法运算之前的实例值
     */
    public int getAndAdd(final MutableNumber<?> operand, final int defaultOperand) {
        final int last = value;
        this.value += (operand != null ? operand.intValue() : defaultOperand);
        return last;
    }
    // ----- Add ----- ending



    // ----- Subtract ----- beginning
    /**
     * 减法：使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数
     * @return 当前实例
     */
    public MutableInt subtract(final int operand) {
        this.value -= operand;
        return this;
    }

    /**
     * 减法：使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当减数为 {@code null} 时抛出此异常
     */
    public MutableInt subtract(final Number operand) {
        this.value -= operand.intValue();
        return this;
    }

    /**
     * 减法：使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，若为 {@code null} 则使用第二个参数作为减数
     * @param defaultOperand 当减数为 {@code null} 时，使用该值作为减数
     * @return 当前实例
     */
    public MutableInt subtract(final Number operand, final int defaultOperand) {
        this.value -= (operand != null ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 减法：使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当减数为 {@code null} 时抛出此异常
     */
    public MutableInt subtract(final MutableNumber<?> operand) {
        this.value -= operand.intValue();
        return this;
    }

    /**
     * 减法：使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，若为 {@code null} 则使用第二个参数作为减数
     * @param defaultOperand 当减数为 {@code null} 时，使用该值作为减数
     * @return 当前实例
     */
    public MutableInt subtract(final MutableNumber<?> operand, final int defaultOperand) {
        this.value -= (operand != null ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 对实例值使用指定减数进行减法运算后返回。
     *
     * @param operand 减数
     * @return 减法运算后的实例值
     */
    public int subtractAndGet(final int operand) {
        this.value -= operand;
        return value;
    }

    /**
     * 对实例值使用指定减数进行减法运算后返回。
     *
     * @param operand 减数，不能为 {@code null}
     * @return 减法运算后的实例值
     * @throws NullPointerException 当减数为 {@code null} 时抛出此异常
     */
    public int subtractAndGet(final Number operand) {
        this.value -= operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定减数进行减法运算后返回。
     *
     * @param operand 减数，若为 {@code null} 则使用第二个参数作为减数
     * @param defaultOperand 当减数为 {@code null} 时，使用该值作为减数
     * @return 减法运算后的实例值
     */
    public int subtractAndGet(final Number operand, final int defaultOperand) {
        this.value -= (operand != null ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 对实例值使用指定减数进行减法运算后返回。
     *
     * @param operand 减数，不能为 {@code null}
     * @return 减法运算后的实例值
     * @throws NullPointerException 当减数为 {@code null} 时抛出此异常
     */
    public int subtractAndGet(final MutableNumber<?> operand) {
        this.value -= operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定减数进行减法运算后返回。
     *
     * @param operand 减数，若为 {@code null} 则使用第二个参数作为减数
     * @param defaultOperand 当减数为 {@code null} 时，使用该值作为减数
     * @return 减法运算后的实例值
     */
    public int subtractAndGet(final MutableNumber<?> operand, final int defaultOperand) {
        this.value -= (operand != null ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 返回当前实例值，并使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数
     * @return 减法运算之前的实例值
     */
    public int getAndSubtract(final int operand) {
        final int last = value;
        this.value -= operand;
        return last;
    }

    /**
     * 返回当前实例值，并使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，不能为 {@code null}
     * @return 减法运算之前的实例值
     * @throws NullPointerException 当减数为 {@code null} 时抛出此异常
     */
    public int getAndSubtract(final Number operand) {
        final int last = value;
        this.value -= operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，若为 {@code null} 则使用第二个参数作为减数
     * @param defaultOperand 若减数为 {@code null} 则使用该值作为减数
     * @return 减法运算之前的实例值
     */
    public int getAndSubtract(final Number operand, final int defaultOperand) {
        final int last = value;
        this.value -= (operand != null ? operand.intValue() : defaultOperand);
        return last;
    }

    /**
     * 返回当前实例值，并使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，不能为 {@code null}
     * @return 减法运算之前的实例值
     * @throws NullPointerException 当减数为 {@code null} 时抛出此异常
     */
    public int getAndSubtract(final MutableNumber<?> operand) {
        final int last = value;
        this.value -= operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定减数对实例值进行减法运算。
     *
     * @param operand 减数，若为 {@code null} 则使用第二个参数作为减数
     * @param defaultOperand 若减数为 {@code null} 则使用该值作为减数
     * @return 减法运算之前的实例值
     */
    public int getAndSubtract(final MutableNumber<?> operand, final int defaultOperand) {
        final int last = value;
        this.value -= (operand != null ? operand.intValue() : defaultOperand);
        return last;
    }
    // ----- Subtract ----- ending



    // ----- Multiply ----- beginning
    /**
     * 乘法：使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数
     * @return 当前实例
     */
    public MutableInt multiply(final int operand) {
        this.value *= operand;
        return this;
    }

    /**
     * 乘法：使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当乘数为 {@code null} 时抛出此异常
     */
    public MutableInt multiply(final Number operand) {
        this.value *= operand.intValue();
        return this;
    }

    /**
     * 乘法：使用指定减数对实例值进行乘法运算。
     *
     * @param operand 乘数，若为 {@code null} 则使用第二个参数作为乘数
     * @param defaultOperand 当乘数为 {@code null} 时，使用该值作为乘数
     * @return 当前实例
     */
    public MutableInt multiply(final Number operand, final int defaultOperand) {
        this.value *= (operand != null ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 乘法：使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当乘数为 {@code null} 时抛出此异常
     */
    public MutableInt multiply(final MutableNumber<?> operand) {
        this.value *= operand.intValue();
        return this;
    }

    /**
     * 乘法：使用指定减数对实例值进行乘法运算。
     *
     * @param operand 乘数，若为 {@code null} 则使用第二个参数作为乘数
     * @param defaultOperand 当乘数为 {@code null} 时，使用该值作为乘数
     * @return 当前实例
     */
    public MutableInt multiply(final MutableNumber<?> operand, final int defaultOperand) {
        this.value *= (operand != null ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 对实例值使用指定乘数进行乘法运算后返回。
     *
     * @param operand 乘数
     * @return 乘法运算后的实例值
     */
    public int multiplyAndGet(final int operand) {
        this.value *= operand;
        return value;
    }

    /**
     * 对实例值使用指定乘数进行乘法运算后返回。
     *
     * @param operand 乘数，不能为 {@code null}
     * @return 乘法运算后的实例值
     * @throws NullPointerException 当乘数为 {@code null} 时抛出此异常
     */
    public int multiplyAndGet(final Number operand) {
        this.value *= operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定乘数进行乘法运算后返回。
     *
     * @param operand 乘数，若为 {@code null} 则使用第二个参数作为乘数
     * @param defaultOperand 当乘数为 {@code null} 时，使用该值作为乘数
     * @return 乘法运算后的实例值
     */
    public int multiplyAndGet(final Number operand, final int defaultOperand) {
        this.value *= (operand != null ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 对实例值使用指定乘数进行乘法运算后返回。
     *
     * @param operand 乘数，不能为 {@code null}
     * @return 乘法运算后的实例值
     * @throws NullPointerException 当乘数为 {@code null} 时抛出此异常
     */
    public int multiplyAndGet(final MutableNumber<?> operand) {
        this.value *= operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定乘数进行乘法运算后返回。
     *
     * @param operand 乘数，若为 {@code null} 则使用第二个参数作为乘数
     * @param defaultOperand 当乘数为 {@code null} 时，使用该值作为乘数
     * @return 乘法运算后的实例值
     */
    public int multiplyAndGet(final MutableNumber<?> operand, final int defaultOperand) {
        this.value *= (operand != null ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 返回当前实例值，并使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数
     * @return 乘法运算之前的实例值
     */
    public int getAndMultiply(final int operand) {
        final int last = value;
        this.value *= operand;
        return last;
    }

    /**
     * 返回当前实例值，并使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数，不能为 {@code null}
     * @return 乘法运算之前的实例值
     * @throws NullPointerException 当乘数为 {@code null} 时抛出此异常
     */
    public int getAndMultiply(final Number operand) {
        final int last = value;
        this.value *= operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数，若为 {@code null} 则使用第二个参数作为乘数
     * @param defaultOperand 若乘数为 {@code null} 则使用该值作为乘数
     * @return 乘法运算之前的实例值
     */
    public int getAndMultiply(final Number operand, final int defaultOperand) {
        final int last = value;
        this.value *= (operand != null ? operand.intValue() : defaultOperand);
        return last;
    }

    /**
     * 返回当前实例值，并使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数，不能为 {@code null}
     * @return 乘法运算之前的实例值
     * @throws NullPointerException 当乘数为 {@code null} 时抛出此异常
     */
    public int getAndMultiply(final MutableNumber<?> operand) {
        final int last = value;
        this.value *= operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定乘数对实例值进行乘法运算。
     *
     * @param operand 乘数，若为 {@code null} 则使用第二个参数作为乘数
     * @param defaultOperand 若乘数为 {@code null} 则使用该值作为乘数
     * @return 乘法运算之前的实例值
     */
    public int getAndMultiply(final MutableNumber<?> operand, final int defaultOperand) {
        final int last = value;
        this.value *= (operand != null ? operand.intValue() : defaultOperand);
        return last;
    }
    // ----- Multiply ----- ending



    // ----- Divide ----- beginning
    /**
     * 除法：使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数
     * @return 当前实例
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public MutableInt divide(final int operand) {
        this.value /= operand;
        return this;
    }

    /**
     * 除法：使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当除数为 {@code null} 时抛出此异常
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public MutableInt divide(final Number operand) {
        this.value /= operand.intValue();
        return this;
    }

    /**
     * 除法：使用指定减数对实例值进行除法运算。
     *
     * @param operand 除数，若为 {@code null} 或其 int 值为 {@code 0} 则使用第二个参数作为除数
     * @param defaultOperand 若除数为 {@code null} 或 {@code 0} 则使用该值作为除数
     * @return 当前实例
     * @throws ArithmeticException 当第一个参数为 {@code null} 或 {@code 0}，且第二个参数为 {@code 0} 时抛出此异常
     */
    public MutableInt divide(final Number operand, final int defaultOperand) {
        this.value /= (operand != null && operand.intValue() != 0 ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 除法：使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数，不能为 {@code null}
     * @return 当前实例
     * @throws NullPointerException 当除数为 {@code null} 时抛出此异常
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public MutableInt divide(final MutableNumber<?> operand) {
        this.value /= operand.intValue();
        return this;
    }

    /**
     * 除法：使用指定减数对实例值进行除法运算。
     *
     * @param operand 除数，若为 {@code null} 或其 int 值为 {@code 0} 则使用第二个参数作为除数
     * @param defaultOperand 若除数为 {@code null} 或 {@code 0} 则使用该值作为除数
     * @return 当前实例
     * @throws ArithmeticException 当第一个参数为 {@code null} 或 {@code 0}，且第二个参数为 {@code 0} 时抛出此异常
     */
    public MutableInt divide(final MutableNumber<?> operand, final int defaultOperand) {
        this.value /= (operand != null && operand.intValue() != 0 ? operand.intValue() : defaultOperand);
        return this;
    }

    /**
     * 对实例值使用指定除数进行除法运算后返回。
     *
     * @param operand 除数
     * @return 除法运算后的实例值
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public int divideAndGet(final int operand) {
        this.value /= operand;
        return value;
    }

    /**
     * 对实例值使用指定除数进行除法运算后返回。
     *
     * @param operand 除数，不能为 {@code null}
     * @return 除法运算后的实例值
     * @throws NullPointerException 当除数为 {@code null} 时抛出此异常
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public int divideAndGet(final Number operand) {
        this.value /= operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定除数进行除法运算后返回。
     *
     * @param operand 除数，若为 {@code null} 或其 int 值为 {@code 0} 则使用第二个参数作为除数
     * @param defaultOperand 若除数为 {@code null} 或 {@code 0} 则使用该值作为除数
     * @return 除法运算后的实例值
     * @throws ArithmeticException 当第一个参数为 {@code null} 或 {@code 0}，且第二个参数为 {@code 0} 时抛出此异常
     */
    public int divideAndGet(final Number operand, final int defaultOperand) {
        this.value /= (operand != null && operand.intValue() != 0 ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 对实例值使用指定除数进行除法运算后返回。
     *
     * @param operand 除数，不能为 {@code null}
     * @return 除法运算后的实例值
     * @throws NullPointerException 当除数为 {@code null} 时抛出此异常
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public int divideAndGet(final MutableNumber<?> operand) {
        this.value /= operand.intValue();
        return value;
    }

    /**
     * 对实例值使用指定除数进行除法运算后返回。
     *
     * @param operand 除数，若为 {@code null} 或其 int 值为 {@code 0} 则使用第二个参数作为除数
     * @param defaultOperand 若除数为 {@code null} 或 {@code 0} 则使用该值作为除数
     * @return 除法运算后的实例值
     * @throws ArithmeticException 当第一个参数为 {@code null} 或 {@code 0}，且第二个参数为 {@code 0} 时抛出此异常
     */
    public int divideAndGet(final MutableNumber<?> operand, final int defaultOperand) {
        this.value /= (operand != null && operand.intValue() != 0 ? operand.intValue() : defaultOperand);
        return value;
    }

    /**
     * 返回当前实例值，并使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数
     * @return 除法运算之前的实例值
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public int getAndDivide(final int operand) {
        final int last = value;
        this.value /= operand;
        return last;
    }

    /**
     * 返回当前实例值，并使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数，不能为 {@code null}
     * @return 除法运算之前的实例值
     * @throws NullPointerException 当除数为 {@code null} 时抛出此异常
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public int getAndDivide(final Number operand) {
        final int last = value;
        this.value /= operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数，若为 {@code null} 或其 int 值为 {@code 0} 则使用第二个参数作为除数
     * @param defaultOperand 若除数为 {@code null} 或 {@code 0} 则使用该值作为除数
     * @return 除法运算之前的实例值
     * @throws ArithmeticException 当第一个参数为 {@code null} 或 {@code 0}，且第二个参数为 {@code 0} 时抛出此异常
     */
    public int getAndDivide(final Number operand, final int defaultOperand) {
        final int last = value;
        this.value /= (operand != null && operand.intValue() != 0 ? operand.intValue() : defaultOperand);
        return last;
    }

    /**
     * 返回当前实例值，并使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数，不能为 {@code null}
     * @return 除法运算之前的实例值
     * @throws NullPointerException 当除数为 {@code null} 时抛出此异常
     * @throws ArithmeticException 当除数为 {@code 0} 时抛出此异常
     */
    public int getAndDivide(final MutableNumber<?> operand) {
        final int last = value;
        this.value /= operand.intValue();
        return last;
    }

    /**
     * 返回当前实例值，并使用指定除数对实例值进行除法运算。
     *
     * @param operand 除数，若为 {@code null} 或其 int 值为 {@code 0} 则使用第二个参数作为除数
     * @param defaultOperand 若除数为 {@code null} 或 {@code 0} 则使用该值作为除数
     * @return 除法运算之前的实例值
     * @throws ArithmeticException 当第一个参数为 {@code null} 或 {@code 0}，且第二个参数为 {@code 0} 时抛出此异常
     */
    public int getAndDivide(final MutableNumber<?> operand, final int defaultOperand) {
        final int last = value;
        this.value /= (operand != null && operand.intValue() != 0 ? operand.intValue() : defaultOperand);
        return last;
    }
    // ----- Divide ----- ending


    /**
     * <p>将此实例和另一个 {@link MutableNumber} 对象进行 int 值的升序比较。</p>
     *
     * @param o 另一个 {@link MutableNumber} 实现对象，不能为 {@code null}
     * @return 若此实例的 int 值较小则返回负数；若此实例的 int 值较大则返回正数；若 int 值相等则返回 0
     * @throws NullPointerException 当指定参数为 {@code null} 时抛出此异常
     */
    @Override
    public int compareTo(final MutableNumber o) {
        return this.value - o.intValue();
    }

    /**
     * <p>将此实例和指定值进行 int 值的升序比较。</p>
     *
     * @param o 要比较的值
     * @return 若此实例的 int 值较小则返回负数；若此实例的 int 值较大则返回正数；若 int 值相等则返回 0
     */
    public int compareTo(final int o) {
        return this.value - o;
    }

    /**
     * 以字符串类型返回实例值。
     *
     * @return 实例值的字符串类型
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * <p>返回一个适合此可变实例的哈希码。</p>
     *
     * @return 一个合适的哈希码
     */
    @Override
    public int hashCode() {
        return value;
    }

    /**
     * <p>将此实例与指定对象进行比较。当且仅当指定对象不为 {@code null} 并是一个包含与此实例 int 值相等的 MutableInt 对象时，比较结果才是 {@code true}。</p>
     *
     * @param obj 要比较的对象，若为 {@code null} 则返回 {@code false}
     * @return 若相等返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof MutableInt) {
            return value == ((MutableInt) obj).intValue();
        }
        return false;
    }
}