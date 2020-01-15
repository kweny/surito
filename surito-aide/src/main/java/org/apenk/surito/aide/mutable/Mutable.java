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
 * <p>提供对值得可变访问。</p>
 *
 * <p>典型用例是可以将基本类型或字符串传递给一个方法，并允许该方法有效地更改基本类型或字符串的值。</p>
 *
 * <p>另一种用法是将需要频繁更改的基本类型存储在集合中，而无需创建新的包装对象。</p>
 *
 * @param <T> 要操作的数据类型
 * @author Kweny
 * @since 0.0.1
 */
public interface Mutable<T> {

    T getValue();

    void setValue(T value);
}