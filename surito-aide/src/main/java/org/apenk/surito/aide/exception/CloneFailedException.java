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
package org.apenk.surito.aide.exception;

/**
 * 无法创建克隆时引发的异常
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CloneFailedException extends RuntimeException {
    private static final long serialVersionUID = -1604814898529431366L;

    public CloneFailedException() {
        super();
    }

    public CloneFailedException(String message) {
        super(message);
    }

    public CloneFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloneFailedException(Throwable cause) {
        super(cause);
    }

    protected CloneFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}