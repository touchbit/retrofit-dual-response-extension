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

package org.touchbit.retrofit.ext.test.model;

import java.util.Arrays;

public class RawDTO {

    private final byte[] data;

    public RawDTO(String data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = data.getBytes();
        }
    }

    public RawDTO(byte[] data) {
        this.data = data;
    }

    public byte[] data() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawDTO testDTO = (RawDTO) o;
        return Arrays.equals(data, testDTO.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

}
