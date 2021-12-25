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

package org.touchbit.retrofit.veslo.example.client.transport;

import io.qameta.allure.Allure;
import org.touchbit.retrofit.veslo.asserter.HeadersAsserter;
import org.touchbit.retrofit.veslo.asserter.ResponseAsserter;
import org.touchbit.retrofit.veslo.client.response.BaseDualResponse;
import org.touchbit.retrofit.veslo.client.response.DualResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ExampleCustomResponse<SUC_DTO, ERR_DTO> extends DualResponse<SUC_DTO, ERR_DTO> {

    public ExampleCustomResponse(@Nullable SUC_DTO sucDTO,
                                 @Nullable ERR_DTO errDTO,
                                 @Nonnull okhttp3.Response response,
                                 @Nonnull String endpointInfo,
                                 @Nonnull Annotation[] callAnnotations) {
        super(sucDTO, errDTO, response, endpointInfo, callAnnotations);
    }

    @Override
    public BaseDualResponse<SUC_DTO, ERR_DTO, ResponseAsserter<SUC_DTO, ERR_DTO, HeadersAsserter>>
    assertResponse(Consumer<ResponseAsserter<SUC_DTO, ERR_DTO, HeadersAsserter>> respAsserter) {
        return step("Check response: " + getEndpointInfo(), () -> super.assertResponse(respAsserter));
    }

    @Override
    public BaseDualResponse<SUC_DTO, ERR_DTO, ResponseAsserter<SUC_DTO, ERR_DTO, HeadersAsserter>>
    assertErrResponse(BiConsumer<ResponseAsserter<SUC_DTO, ERR_DTO, HeadersAsserter>, ERR_DTO> respAsserter,
                      ERR_DTO expected) {
        return step("Check error response: " + getEndpointInfo(),
                () -> super.assertErrResponse(respAsserter, expected));
    }

    @Override
    public BaseDualResponse<SUC_DTO, ERR_DTO, ResponseAsserter<SUC_DTO, ERR_DTO, HeadersAsserter>>
    assertSucResponse(BiConsumer<ResponseAsserter<SUC_DTO, ERR_DTO, HeadersAsserter>, SUC_DTO> respAsserter,
                      SUC_DTO expected) {
        return step("Check success response: " + getEndpointInfo(),
                () -> super.assertSucResponse(respAsserter, expected));
    }

    private static <T> T step(final String name, final Allure.ThrowableRunnable<T> runnable) {
        return Allure.step(name, () -> {
            try {
                return runnable.run();
            } catch (Throwable throwable) {
                Allure.addAttachment("ERROR", throwable.getMessage());
                throw throwable;
            }
        });
    }

}
