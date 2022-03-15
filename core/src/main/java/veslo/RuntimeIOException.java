/*
 * Copyright 2021 Shaburov Oleg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package veslo;

/**
 * @author Oleg Shaburov (shaburov.o.a@gmail.com)
 * Created: 18.02.2022
 */
public class RuntimeIOException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message the detail message
     */
    public RuntimeIOException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param t the cause
     */
    public RuntimeIOException(String message, Throwable t) {
        super(message, t);
    }

}
