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

package org.touchbit.retrofit.ext.dmr.exaple;

import okhttp3.OkHttpClient;
import org.touchbit.retrofit.ext.dmr.asserter.HeadersAsserter;
import org.touchbit.retrofit.ext.dmr.asserter.ResponseAsserter;
import org.touchbit.retrofit.ext.dmr.asserter.SoftlyAsserter;
import org.touchbit.retrofit.ext.dmr.client.adapter.UniversalCallAdapterFactory;
import org.touchbit.retrofit.ext.dmr.client.converter.ExtensionConverterFactory;
import org.touchbit.retrofit.ext.dmr.client.inteceptor.CompositeInterceptor;
import org.touchbit.retrofit.ext.dmr.client.inteceptor.LoggingInterceptAction;
import org.touchbit.retrofit.ext.dmr.exaple.ExampleClient.ErrDTO;
import org.touchbit.retrofit.ext.dmr.exaple.ExampleClient.SucDTO;
import org.touchbit.retrofit.ext.dmr.util.ExcludeFromJacocoGeneratedReport;
import retrofit2.Retrofit;

import static org.touchbit.retrofit.ext.dmr.asserter.AssertionMatcher.is;
import static org.touchbit.retrofit.ext.dmr.exaple.ExampleApiClientAssertions.Assertions.*;

/**
 * Response assertion examples
 * <p>
 *
 * @author Oleg Shaburov (shaburov.o.a@gmail.com)
 * Created: 12.12.2021
 */
@ExcludeFromJacocoGeneratedReport()
public class ExampleApiClientAssertions {

    protected static ExampleClient apiClient = new Retrofit.Builder()
            .client(new OkHttpClient.Builder()
                    .addInterceptor(new CompositeInterceptor()
                            .withRequestInterceptActionsChain(LoggingInterceptAction.INSTANCE)
                            .withResponseInterceptActionsChain(LoggingInterceptAction.INSTANCE))
                    .build())
            .baseUrl("http://localhost")
            .addCallAdapterFactory(new UniversalCallAdapterFactory())
            .addConverterFactory(new ExtensionConverterFactory())
            .build()
            .create(ExampleClient.class);

    @ExcludeFromJacocoGeneratedReport()
    public static class AssertResponseMethodTests {

        public void example1639328754881() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body")
                    .assertResponse(respAsserter -> respAsserter
                            .assertHttpStatusCodeIs(200)
                            .assertHttpStatusMessageIs("OK")
                            .assertHeaders(headersAsserter -> headersAsserter
                                    .contentTypeIs("application/json")
                                    .assertHeaderIsPresent("X-Request-Id")
                                    .accessControlAllowOriginIs("*"))
                            .assertSucBody(actual -> {
                                actual.assertConsistency();
                                is("DTO.message", actual.msg, expected.msg);
                            }));
        }

        public void example1639329013867() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body")
                    .assertResponse(asserter -> assertSucResponse(asserter, expected));
        }

        public void example1639437048937() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000)
                    .assertResponse(asserter -> assertErrResponse(asserter, expected));
        }

    }

    @ExcludeFromJacocoGeneratedReport()
    public static class AssertHeadersMethodExamples {

        public void example1639329975511() {
            apiClient.exampleApiCall("body")
                    .assertResponse(respAsserter -> respAsserter
                            .assertHeaders(headersAsserter -> headersAsserter
                                    .contentTypeIs("application/json")
                                    .assertHeaderIsPresent("X-Request-Id")
                                    .assertHeaderIs("Custom-Header", "example value")
                                    .accessControlAllowOriginIs("*")));
        }

        public void example1639330184783() {
            apiClient.exampleApiCall("body")
                    .assertResponse(respAsserter -> respAsserter
                            .assertHeaders(Assertions::assertHeaders));
        }

    }

    @ExcludeFromJacocoGeneratedReport()
    public static class AssertSucResponseMethodExamples {

        public void example1639323053942() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, actual -> {
                        actual.assertConsistency();
                        is("DTO.message", actual.msg, expected.msg);
                    }));
        }

        public void example1639323439880() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, actual -> assertSucDTO(actual, expected)));
        }

        public void example1639324202096() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, actual -> actual.assertDTO(expected)));
        }

        public void example1639324398972() {
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, SucDTO::assertConsistency));
        }

        public void example1639323829528() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, Assertions::assertSucDTO, expected));
        }

        public void example1639325042327() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, SucDTO::assertDTO, expected));
        }

        public void example1639328323975() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, (asserter, actual) -> {
                        asserter.softly(actual::assertConsistency);
                        asserter.softly(() -> is("DTO.message", actual.msg, expected.msg));
                    }));
        }

        public void example1639328330398() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200,
                            (asserter, actual) -> Assertions.assertSoftlySucDTO(asserter, actual, expected)));
        }

        public void example1639327810398() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucResponse(200, Assertions::assertSoftlySucDTO, expected));
        }

    }

    @ExcludeFromJacocoGeneratedReport()
    public static class AssertSucBodyMethodExamples {

        public void example1639325312188() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody(actual -> {
                        actual.assertConsistency();
                        is("DTO.message", actual.msg, expected.msg);
                    }));
        }

        public void example1639325440427() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody(actual -> assertSucDTO(actual, expected)));
        }

        public void example1639325443017() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody(actual -> actual.assertDTO(expected)));
        }

        public void example1639325444984() {
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody(SucDTO::assertConsistency));
        }

        public void example1639325561761() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody(Assertions::assertSucDTO, expected));
        }

        public void example1639325563806() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody(SucDTO::assertDTO, expected));
        }

        public void example1639325807124() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody((asserter, actual) -> {
                        asserter.softly(actual::assertConsistency);
                        asserter.softly(() -> is("DTO.message", actual.msg, expected.msg));
                    }));
        }

        public void example1639326363134() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody((asserter, actual) -> Assertions.assertSoftlySucDTO(asserter, actual, expected)));
        }

        public void example1639326893023() {
            SucDTO expected = new SucDTO("example");
            apiClient.exampleApiCall("body").assertResponse(respAsserter -> respAsserter
                    .assertSucBody(Assertions::assertSoftlySucDTO, expected));
        }

    }

    @ExcludeFromJacocoGeneratedReport()
    public static class AssertErrResponseMethodExamples {

        public void example1639435639889() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, actual -> {
                        actual.assertConsistency();
                        is("DTO.message", actual.code, expected.code);
                    }));
        }

        public void example1639435652717() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, actual -> assertErrDTO(actual, expected)));
        }

        public void example1639435661467() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, actual -> actual.assertDTO(expected)));
        }

        public void example1639435669326() {
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, ErrDTO::assertConsistency));
        }

        public void example1639435677413() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, Assertions::assertErrDTO, expected));
        }

        public void example1639435684781() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, ErrDTO::assertDTO, expected));
        }

        public void example1639435692258() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, (asserter, actual) -> {
                        asserter.softly(actual::assertConsistency);
                        asserter.softly(() -> is("DTO.message", actual.code, expected.code));
                    }));
        }

        public void example1639435699849() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200,
                            (asserter, actual) -> Assertions.assertSoftlyErrDTO(asserter, actual, expected)));
        }

        public void example1639435708260() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrResponse(200, Assertions::assertSoftlyErrDTO, expected));
        }

    }

    @ExcludeFromJacocoGeneratedReport()
    public static class AssertErrBodyMethodExamples {

        public void example1639437244747() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody(actual -> {
                        actual.assertConsistency();
                        is("DTO.message", actual.code, expected.code);
                    }));
        }

        public void example1639437254775() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody(actual -> assertErrDTO(actual, expected)));
        }

        public void example1639437276238() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody(actual -> actual.assertDTO(expected)));
        }

        public void example1639437293672() {
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody(ErrDTO::assertConsistency));
        }

        public void example1639437371546() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody(Assertions::assertErrDTO, expected));
        }

        public void example1639437376687() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody(ErrDTO::assertDTO, expected));
        }

        public void example1639437383840() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody((asserter, actual) -> {
                        asserter.softly(actual::assertConsistency);
                        asserter.softly(() -> is("DTO.message", actual.code, expected.code));
                    }));
        }

        public void example1639437391538() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody((asserter, actual) -> Assertions.assertSoftlyErrDTO(asserter, actual, expected)));
        }

        public void example1639437397547() {
            ErrDTO expected = new ErrDTO(1000);
            apiClient.exampleApiCall(1000).assertResponse(respAsserter -> respAsserter
                    .assertErrBody(Assertions::assertSoftlyErrDTO, expected));
        }

    }

    @ExcludeFromJacocoGeneratedReport()
    public static class Assertions {

        public static void assertSucDTO(SucDTO actual, SucDTO expected) {
            try (final SoftlyAsserter asserter = SoftlyAsserter.get()) {
                asserter.softly(actual::assertConsistency)
                        .softly(() -> is("DTO.message", actual.msg, expected.msg));
            }
        }

        public static void assertSoftlySucDTO(SoftlyAsserter asserter, SucDTO actual, SucDTO expected) {
            asserter.softly(actual::assertConsistency)
                    .softly(() -> is("DTO.message", actual.msg, expected.msg));
        }

        public static void assertSucResponse(ResponseAsserter<SucDTO, ErrDTO, HeadersAsserter> asserter, SucDTO expected) {
            asserter.assertHttpStatusCodeIs(200)
                    .assertHttpStatusMessageIs("OK")
                    .assertHeaders(headersAsserter -> headersAsserter
                            .contentTypeIs("application/json")
                            .assertHeaderIsPresent("X-Request-Id")
                            .accessControlAllowOriginIs("*"))
                    .assertSucBody(actual -> {
                        actual.assertConsistency();
                        is("DTO.message", actual.msg, expected.msg);
                    });
        }

        public static void assertErrDTO(ErrDTO actual, ErrDTO expected) {
            try (final SoftlyAsserter asserter = SoftlyAsserter.get()) {
                asserter.softly(actual::assertConsistency)
                        .softly(() -> is("DTO.message", actual.code, expected.code));
            }
        }

        public static void assertSoftlyErrDTO(SoftlyAsserter asserter, ErrDTO actual, ErrDTO expected) {
            asserter.softly(actual::assertConsistency)
                    .softly(() -> is("DTO.message", actual.code, expected.code));
        }

        public static void assertErrResponse(ResponseAsserter<SucDTO, ErrDTO, HeadersAsserter> asserter, ErrDTO expected) {
            asserter.assertHttpStatusCodeIs(200)
                    .assertHttpStatusMessageIs("OK")
                    .assertHeaders(headersAsserter -> headersAsserter
                            .contentTypeIs("application/json")
                            .assertHeaderIsPresent("X-Request-Id")
                            .accessControlAllowOriginIs("*"))
                    .assertErrBody(actual -> {
                        actual.assertConsistency();
                        is("DTO.message", actual.code, expected.code);
                    });
        }

        public static void assertHeaders(HeadersAsserter asserter) {
            asserter.contentTypeIs("application/json")
                    .assertHeaderIsPresent("X-Request-Id")
                    .assertHeaderIs("Custom-Header", "example value")
                    .accessControlAllowOriginIs("*");
        }

    }

}
