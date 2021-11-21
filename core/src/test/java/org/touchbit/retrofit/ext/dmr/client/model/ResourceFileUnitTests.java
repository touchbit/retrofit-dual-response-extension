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

package org.touchbit.retrofit.ext.dmr.client.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.MissingResourceException;

import static internal.test.utils.ThrowableAsserter.assertThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("ResourceFile tests")
public class ResourceFileUnitTests {

    @Test
    @DisplayName("Successfully instantiating class where resource file exists")
    public void test1637486628431() {
        new ResourceFile("test/data/test1637486628431.txt");
    }

    @Test
    @DisplayName("Successfully instantiating class where resource exists")
    public void test1637486925349() {
        new ResourceFile("test/data");
    }

    @Test
    @DisplayName("Failed to instantiate class where resource not exists")
    public void test1637486983022() {
        assertThrow(() -> new ResourceFile("test/data/test1637486983022.txt"))
                .assertClass(MissingResourceException.class)
                .assertMessageIs("Resource not exists: test/data/test1637486983022.txt");
    }

    @Test
    @DisplayName("getBytes() successful read of an existing resource file")
    public void test1637487087922() throws Exception {
        final byte[] bytes = new ResourceFile("test/data/test1637487087922.txt").getBytes();
        assertThat("", new String(bytes), is("test1637487087922"));
    }

    @Test
    @DisplayName("getBytes() error reading non-existent file")
    public void test1637487260260() throws IOException {
        final ResourceFile resourceFile = mock(ResourceFile.class);
        when(resourceFile.getResourceRelativePath()).thenReturn("test/data/test1637487260260");
        when(resourceFile.getClassLoader()).thenCallRealMethod();
        when(resourceFile.getBytes()).thenCallRealMethod();
        assertThrow(resourceFile::getBytes)
                .assertClass(MissingResourceException.class)
                .assertMessageIs("Resource not exists: test/data/test1637487260260");
    }

}