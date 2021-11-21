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

package org.touchbit.retrofit.ext.dmr.client.converter.defaults;

import internal.test.utils.OkHttpUtils;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.touchbit.retrofit.ext.dmr.client.model.ResourceFile;
import org.touchbit.retrofit.ext.dmr.exception.ConvertCallException;
import org.touchbit.retrofit.ext.dmr.exception.ConverterUnsupportedTypeException;

import static internal.test.utils.ThrowableAsserter.assertThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ConstantConditions")
@DisplayName("ResourceFileConverter tests")
public class ResourceFileConverterUnitTests {

    @Test
    @DisplayName("Successful conversion ResourceFile->RequestBody if body instanceof File.class")
    public void test1637468946514() {
        final String expected = "test1637468946514";
        final ResourceFile body = new ResourceFile("test/data/test1637468946514.txt");
        final RequestBody requestBody = new ResourceFileConverter()
                .requestBodyConverter(null, null, null, null)
                .convert(body);
        assertThat("RequestBody", requestBody, notNullValue());
        final String actual = OkHttpUtils.requestBodyToString(requestBody);
        assertThat("Body", actual, is(expected));
    }

    @Test
    @DisplayName("Error converting ResourceFile->RequestBody if body == null")
    public void test1637468949268() {
        final Runnable runnable = () -> new ResourceFileConverter()
                .requestBodyConverter(null, null, null, null)
                .convert(null);
        assertThrow(runnable).assertClass(NullPointerException.class).assertMessageIs("Parameter 'body' required");
    }

    @Test
    @DisplayName("Error converting Object->RequestBody")
    public void test1637468952767() {
        final Runnable runnable = () -> new ResourceFileConverter()
                .requestBodyConverter(null, null, null, null)
                .convert(new Object());
        assertThrow(runnable).assertClass(ConverterUnsupportedTypeException.class);
    }

    @Test
    @DisplayName("Error converting ResponseBody->ResourceFile")
    public void test1637468955934() throws Exception {
        final String expected = "test1637463929423";
        final ResponseBody responseBody = mock(ResponseBody.class);
        when(responseBody.bytes()).thenReturn(expected.getBytes());
        final Runnable runnable = () -> new ResourceFileConverter()
                .responseBodyConverter(null, null, null)
                .convert(null);
        assertThrow(runnable)
                .assertClass(ConvertCallException.class)
                .assertMessageIs("It is forbidden to use the " + ResourceFile.class + " model in a response body.");
    }

}