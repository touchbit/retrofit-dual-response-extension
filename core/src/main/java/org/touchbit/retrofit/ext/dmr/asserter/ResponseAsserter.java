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
import org.touchbit.retrofit.ext.dmr.client.response.IDualResponse;
import org.touchbit.retrofit.ext.dmr.util.Utils;
import retrofit2.internal.EverythingIsNonNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO>
        extends ResponseAsserterBase<SUCCESSFUL_DTO, ERROR_DTO, HeadersAsserter> {

    public ResponseAsserter(@Nonnull IDualResponse<SUCCESSFUL_DTO, ERROR_DTO> response) {
        super(response);
    }

    @Override
    @EverythingIsNonNull
    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertHeaders(Consumer<HeadersAsserter> consumer) {
        Utils.parameterRequireNonNull(consumer, "consumer");
        final Headers headers = getResponse().getHeaders();
        final HeadersAsserter headersAsserter = new HeadersAsserter(headers);
        consumer.accept(headersAsserter);
        addErrors(headersAsserter.getErrors());
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertSuccessfulResponse(final int expectedStatusCode,
                                                                                final Consumer<SUCCESSFUL_DTO> consumer) {
        return assertSuccessfulBody(consumer).assertHttpStatusCodeIs(expectedStatusCode);
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertSuccessfulBody(Consumer<SUCCESSFUL_DTO> consumer) {
        final SUCCESSFUL_DTO successfulDTO = assertSuccessfulDtoNotNull().blame().getResponse().getSuccessfulDTO();
        softly(() -> consumer.accept(successfulDTO));
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertIsSuccessfulResponse() {
        if (!getResponse().isSuccessful()) {
            addErrors(new AssertionError("Received unsuccessful HTTP status code.\n" +
                    "Expected: in range 200..299\n" +
                    "  Actual: was " + getResponse().getHttpStatusCode()));
        }
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertSuccessfulDtoNotNull() {
        if (getResponse().getSuccessfulDTO() == null) {
            addErrors(new AssertionError("Received a successful body\n" +
                    "Expected: true\n" +
                    "  Actual: false"));
        }
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertErrorResponse(final int expectedStatusCode,
                                                                           final Consumer<ERROR_DTO> consumer) {
        return assertErrorBody(consumer).assertHttpStatusCodeIs(expectedStatusCode);
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertErrorBody(Consumer<ERROR_DTO> consumer) {
        final ERROR_DTO errorDTO = assertErrorDtoNotNull().blame().getResponse().getErrorDTO();
        softly(() -> consumer.accept(errorDTO));
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertIsErrorResponse() {
        if (getResponse().isSuccessful()) {
            addErrors(new AssertionError("Received successful HTTP status code.\n" +
                    "Expected: in range 300..599\n" +
                    "  Actual: was " + getResponse().getHttpStatusCode()));
        }
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertErrorDtoNotNull() {
        if (getResponse().getErrorDTO() == null) {
            addErrors(new AssertionError("Error body received\n" +
                    "Expected: true\n" +
                    "  Actual: false"));
        }
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertHttpStatusCodeIs(int expected) {
        int actual = getResponse().getHttpStatusCode();
        if (actual != expected) {
            addErrors(new AssertionError("HTTP status code\n" +
                    "Expected: is  " + expected + "\n" +
                    "  Actual: was " + actual));
        }
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> assertHttpStatusMessageIs(String expected) {
        final String actual = getResponse().getHttpStatusMessage();
        if (actual == null && expected == null) {
            return this;
        }
        if (actual == null || !actual.equalsIgnoreCase(expected)) {
            addErrors(new AssertionError("HTTP status message\n" +
                    "Expected: is  " + expected + "\n" +
                    "  Actual: was " + actual));
        }
        return this;
    }

    public ResponseAsserter<SUCCESSFUL_DTO, ERROR_DTO> blame() {
        super.close();
        return this;
    }

}
