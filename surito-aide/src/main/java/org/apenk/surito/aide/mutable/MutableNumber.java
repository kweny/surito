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
 * <p>可变数字类型包装的基类</p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public abstract class MutableNumber<T extends Number> extends Number implements Comparable<MutableNumber<T>>, Mutable<T> {
    private static final long serialVersionUID = 1459064142182426277L;

    public static MutableInt newInt(final int value) {
        return new MutableInt(value);
    }

    public static MutableInt newInt(final Number value) {
        return new MutableInt(value);
    }

    public static MutableInt newInt(final Number value, final int defaultValue) {
        return new MutableInt(value, defaultValue);
    }

    public static MutableInt newInt(final String value) {
        return new MutableInt(value);
    }

    public static MutableInt newInt(final String value, final int defaultValue) {
        return new MutableInt(value, defaultValue);
    }
}