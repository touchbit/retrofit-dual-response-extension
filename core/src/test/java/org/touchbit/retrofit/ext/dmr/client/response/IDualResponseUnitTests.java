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

package org.touchbit.retrofit.ext.dmr.client.response;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@DisplayName("IDualResponse tests")
public class IDualResponseUnitTests {

    @Test
    @DisplayName("Check default methods if all objects is present")
    public void test1639065948557() {
        final ResponseBody responseBody = mock(ResponseBody.class);
        when(responseBody.contentType()).thenReturn(MediaType.get("application/json; charset=utf-8"));
        final okhttp3.Response rawResponse = mock(okhttp3.Response.class);
        when(rawResponse.body()).thenReturn(responseBody);
        final Response<String> response = mock(Response.class);
        when(response.code()).thenReturn(200);
        when(response.message()).thenReturn("TEST");
        when(response.isSuccessful()).thenReturn(true);
        when(response.body()).thenReturn("test1637488565492");
        when(response.headers()).thenReturn(Headers.of("testHeader", "test1637488565492"));
        when(response.raw()).thenReturn(rawResponse);

        final IDualResponse<String, String> iDualResponse = getIDualResponse(response);
        assertThat("", iDualResponse, notNullValue());
        assertThat("", iDualResponse.getSucDTO(), is("test1637488565492"));
        assertThat("", iDualResponse.getHttpStatusCode(), is(200));
        assertThat("", iDualResponse.getHttpStatusMessage(), is("TEST"));
        assertThat("", iDualResponse.getHeaders(), notNullValue());
        assertThat("", iDualResponse.getHeaders().get("Content-Type"), is("application/json; charset=utf-8"));
        assertThat("", iDualResponse.getHeaders().get("testHeader"), is("test1637488565492"));
        assertThat("", iDualResponse.isSuccessful(), is(true));
    }

    @Test
    @DisplayName("#getHeaders() successfully fetching headers if okhttp3.ResponseBody is null")
    public void test1639065948583() {
        final okhttp3.Response rawResponse = mock(okhttp3.Response.class);
        when(rawResponse.body()).thenReturn(null);
        final Response<String> response = mock(Response.class);
        when(response.code()).thenReturn(200);
        when(response.message()).thenReturn("TEST");
        when(response.isSuccessful()).thenReturn(true);
        when(response.body()).thenReturn("test1637488565492");
        when(response.headers()).thenReturn(Headers.of("testHeader", "test1637488565492"));
        when(response.raw()).thenReturn(rawResponse);

        final IDualResponse<String, String> iDualResponse = getIDualResponse(response);
        assertThat("", iDualResponse, notNullValue());
        assertThat("", iDualResponse.getSucDTO(), is("test1637488565492"));
        assertThat("", iDualResponse.getHttpStatusCode(), is(200));
        assertThat("", iDualResponse.getHttpStatusMessage(), is("TEST"));
        assertThat("", iDualResponse.getHeaders(), notNullValue());
        assertThat("", iDualResponse.getHeaders().get("Content-Type"), nullValue());
        assertThat("", iDualResponse.getHeaders().get("testHeader"), is("test1637488565492"));
        assertThat("", iDualResponse.isSuccessful(), is(true));
    }

    @Test
    @DisplayName("#getHeaders() successfully fetching headers if okhttp3.ResponseBody contentType is null")
    public void test1639065948607() {
        final ResponseBody responseBody = mock(ResponseBody.class);
        when(responseBody.contentType()).thenReturn(null);
        final okhttp3.Response rawResponse = mock(okhttp3.Response.class);
        when(rawResponse.body()).thenReturn(responseBody);
        final Response<String> response = mock(Response.class);
        when(response.code()).thenReturn(200);
        when(response.message()).thenReturn("TEST");
        when(response.isSuccessful()).thenReturn(true);
        when(response.body()).thenReturn("test1637488565492");
        when(response.headers()).thenReturn(Headers.of("testHeader", "test1637488565492"));
        when(response.raw()).thenReturn(rawResponse);

        final IDualResponse<String, String> iDualResponse = getIDualResponse(response);
        assertThat("", iDualResponse, notNullValue());
        assertThat("", iDualResponse.getSucDTO(), is("test1637488565492"));
        assertThat("", iDualResponse.getHttpStatusCode(), is(200));
        assertThat("", iDualResponse.getHttpStatusMessage(), is("TEST"));
        assertThat("", iDualResponse.getHeaders(), notNullValue());
        assertThat("", iDualResponse.getHeaders().get("Content-Type"), nullValue());
        assertThat("", iDualResponse.getHeaders().get("testHeader"), is("test1637488565492"));
        assertThat("", iDualResponse.isSuccessful(), is(true));
    }

    private IDualResponse<String, String> getIDualResponse(Response<String> response) {
        return new IDualResponse<String, String>() {
            @Nonnull
            @Override
            public okhttp3.Response getResponse() {
                return null;
            }

            @Nullable
            @Override
            public String getErrorDTO() {
                return null;
            }

            @Nullable
            @Override
            public String getSucDTO() {
                return null;
            }

            @Nonnull
            @Override
            public String getEndpointInfo() {
                return null;
            }

            @Nonnull
            @Override
            public Annotation[] getCallAnnotations() {
                return new Annotation[0];
            }
        };
    }

}