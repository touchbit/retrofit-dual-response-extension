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

package org.touchbit.retrofit.ext.dmr.asserter;

import okhttp3.Headers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.touchbit.retrofit.ext.dmr.BaseCoreUnitTest;
import org.touchbit.retrofit.ext.dmr.client.response.IDualResponse;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unchecked", "rawtypes", "ConstantConditions"})
@DisplayName("ResponseAsserter.class unit tests")
public class ResponseAsserterUnitTests extends BaseCoreUnitTest {

    private static final HeadersAsserter EMPTY_HEADER_ASSERTER = new HeadersAsserter(Headers.of());
    private static final IDualResponse<Object, Object> RESPONSE = mock(IDualResponse.class);

    @Nested
    @DisplayName("Constructor tests")
    public class ResponseAsserterConstructorTests {

        @Test
        @DisplayName("All parameters required")
        public void test1639065947424() {
            assertThrow(() -> new ResponseAsserter<>(null, EMPTY_HEADER_ASSERTER)).assertNPE("response");
            assertThrow(() -> new ResponseAsserter<>(RESPONSE, null)).assertNPE("headersAsserter");
        }

        @Test
        @DisplayName("Get constructor parameters")
        public void test1639253809035() {
            final ResponseAsserter asserter = new ResponseAsserter<>(RESPONSE, EMPTY_HEADER_ASSERTER);
            assertThat(asserter.getHeadersAsserter(), is(EMPTY_HEADER_ASSERTER));
            assertThat(asserter.getResponse(), is(RESPONSE));
        }

    }

    @Nested
    @DisplayName("#blame() method tests")
    public class BlameMethodTests {

        @Test
        @DisplayName("#blame() without AssertionError if no errors")
        public void test1639254058024() {
            assertThat(new ResponseAsserter<>(RESPONSE, EMPTY_HEADER_ASSERTER).blame(), notNullValue());
        }

        @Test
        @DisplayName("#blame() with AssertionError if has errors")
        public void test1639254060794() {
            final ResponseAsserter responseAsserter = new ResponseAsserter<>(RESPONSE, EMPTY_HEADER_ASSERTER);
            responseAsserter.addErrors(new RuntimeException("test1639254060794"));
            assertThrow(responseAsserter::blame)
                    .assertClass(AssertionError.class)
                    .assertMessageIs("Collected the following errors:\n\ntest1639254060794");
        }

    }

    @Nested
    @DisplayName("#assertHeaders(Consumer) method tests")
    public class AssertHeadersMethodTests {

        @Test
        @DisplayName("All parameters required")
        public void test1639065947466() {
            final ResponseAsserter responseAsserter = new ResponseAsserter<>(RESPONSE, EMPTY_HEADER_ASSERTER);
            assertThrow(() -> responseAsserter.assertHeaders(null)).assertNPE("headerAsserterConsumer");
        }

        @Test
        @DisplayName("#assertHeaders(Consumer) AssertionError is not thrown if headers no errors (Closeable)")
        public void test1639065947474() {
            new ResponseAsserter<>(RESPONSE, EMPTY_HEADER_ASSERTER)
                    .assertHeaders(HeadersAsserter::contentTypeNotPresent)
                    .blame();
        }

        @Test
        @DisplayName("#assertHeaders(Consumer) AssertionError is not thrown if headers has errors (Closeable)")
        public void test1639065947484() {
            final ResponseAsserter asserter = new ResponseAsserter<>(RESPONSE, EMPTY_HEADER_ASSERTER)
                    .assertHeaders(HeadersAsserter::connectionIsPresent); // not thrown
            assertThrow(asserter::blame) // explicit throw
                    .assertClass(AssertionError.class)
                    .assertMessageIs("Collected the following errors:\n\n" +
                            "Response header 'Connection'\n" +
                            "Expected: is present\n" +
                            "  Actual: null");
        }

    }

    @Test
    @DisplayName("#assertSuccessfulResponse(int, Consumer) positive")
    public void test1639065947494() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(200);
        when(response.getSucDTO()).thenReturn(new Object());
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertSucResponse(200, body -> assertThat("", body, notNullValue()));
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertSuccessfulResponse(int, Consumer) negative")
    public void test1639065947506() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(500);
        when(response.getSucDTO()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        assertThrow(() -> asserter.assertSucResponse(200, body -> assertThat("", body, notNullValue())))
                .assertClass(AssertionError.class)
                .assertMessageIs("Collected the following errors:\n\n" +
                        "HTTP status code\n" +
                        "Expected: is  200\n" +
                        "  Actual: was 500\n" +
                        "\n" +
                        "Successful body\n" +
                        "Expected: is not null\n" +
                        "  Actual: null");
    }

    @Test
    @DisplayName("#assertSuccessfulBody(Consumer) positive")
    public void test1639065947522() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(200);
        when(response.getSucDTO()).thenReturn(new Object());
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertSucBody(body -> assertThat("", body, notNullValue()));
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertSuccessfulBody(Consumer) negative")
    public void test1639065947534() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(500);
        when(response.getSucDTO()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        assertThrow(() -> asserter.assertSucBody(body -> assertThat("", body, notNullValue())))
                .assertClass(AssertionError.class)
                .assertMessageIs("Collected the following errors:\n\n" +
                        "Successful body\n" +
                        "Expected: is not null\n" +
                        "  Actual: null");
    }

    @Test
    @DisplayName("#assertIsSuccessfulResponse() positive")
    public void test1639065947550() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(200);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertIsSucHttpStatusCode();
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertIsSuccessfulResponse() negative")
    public void test1639065947560() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(500);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertIsSucHttpStatusCode();
        assertResponseAsserterContainErrors(asserter, 1);
    }

    @Test
    @DisplayName("#assertSuccessfulDtoNotNull() positive")
    public void test1639065947570() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getSucDTO()).thenReturn(new Object());
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertSucBodyNotNull();
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertSuccessfulDtoNotNull() negative")
    public void test1639065947580() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getSucDTO()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertSucBodyNotNull();
        assertResponseAsserterContainErrors(asserter, 1);
    }

    @Test
    @DisplayName("#assertErrorResponse(int, Consumer) positive")
    public void test1639065947590() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(400);
        when(response.getErrDTO()).thenReturn(new Object());
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertErrResponse(400, body -> assertThat("", body, notNullValue()));
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertErrorResponse(int, Consumer) negative")
    public void test1639065947602() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(200);
        when(response.getErrDTO()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        assertThrow(() -> asserter.assertErrResponse(500, body -> assertThat("", body, notNullValue())))
                .assertClass(AssertionError.class)
                .assertMessageIs("Collected the following errors:\n\n" +
                        "HTTP status code\n" +
                        "Expected: is  500\n" +
                        "  Actual: was 200\n" +
                        "\n" +
                        "Error body\n" +
                        "Expected: is not null\n" +
                        "  Actual: null");
    }

    @Test
    @DisplayName("#assertErrorBody(Consumer) positive")
    public void test1639065947618() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(400);
        when(response.getErrDTO()).thenReturn(new Object());
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertErrBody(body -> assertThat("", body, notNullValue()));
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertErrorBody(Consumer) negative")
    public void test1639065947630() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(200);
        when(response.getErrDTO()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        assertThrow(() -> asserter.assertErrBody(body -> assertThat("", body, notNullValue())))
                .assertClass(AssertionError.class)
                .assertMessageIs("Collected the following errors:\n\n" +
                        "Error body\n" +
                        "Expected: is not null\n" +
                        "  Actual: null");
    }

    @Test
    @DisplayName("#assertIsErrorResponse() positive")
    public void test1639065947646() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(500);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertIsErrResponse();
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertIsErrorResponse() negative")
    public void test1639065947656() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(200);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertIsErrResponse();
        assertResponseAsserterContainErrors(asserter, 1);
    }

    @Test
    @DisplayName("#assertErrorDtoNotNull() positive")
    public void test1639065947666() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getErrDTO()).thenReturn(new Object());
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertErrBodyNotNull();
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertErrorDtoNotNull() negative")
    public void test1639065947676() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getErrDTO()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertErrBodyNotNull();
        assertResponseAsserterContainErrors(asserter, 1);
    }

    @Test
    @DisplayName("#assertHttpStatusCodeIs() positive")
    public void test1639065947686() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(200);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertHttpStatusCodeIs(200);
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertHttpStatusCodeIs() negative")
    public void test1639065947696() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusCode()).thenReturn(500);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertHttpStatusCodeIs(200);
        assertResponseAsserterContainErrors(asserter, 1);
    }

    @Test
    @DisplayName("#assertHttpStatusMessageIs() positive")
    public void test1639065947706() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusMessage()).thenReturn("OK");
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertHttpStatusMessageIs("OK");
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertHttpStatusMessageIs() positive (null)")
    public void test1639065947716() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusMessage()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertHttpStatusMessageIs(null);
        assertResponseAsserterContainErrors(asserter, 0);
    }

    @Test
    @DisplayName("#assertHttpStatusMessageIs() negative (null)")
    public void test1639065947726() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusMessage()).thenReturn(null);
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertHttpStatusMessageIs("OK");
        assertResponseAsserterContainErrors(asserter, 1);
    }

    @Test
    @DisplayName("#assertHttpStatusMessageIs() negative (different)")
    public void test1639065947736() {
        final IDualResponse<Object, Object> response = mock(IDualResponse.class);
        when(response.getHttpStatusMessage()).thenReturn("FAIL");
        final ResponseAsserter asserter = new ResponseAsserter<>(response, EMPTY_HEADER_ASSERTER);
        asserter.assertHttpStatusMessageIs("OK");
        assertResponseAsserterContainErrors(asserter, 1);
    }

    private void assertResponseAsserterContainErrors(ResponseAsserter<?, ?, ?> ra, int errorsCount) {
        try (final SoftlyAsserter asserter = SoftlyAsserter.get()) {
            asserter.softly(() -> assertThat("ResponseAsserter.getErrors()", ra.getErrors(), notNullValue()));
            asserter.softly(() -> assertThat("ResponseAsserter.getErrors()", ra.getErrors().size(), is(errorsCount)));
        }
    }

}