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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.touchbit.retrofit.ext.dmr.client.converter.api.ExtensionConverter;
import org.touchbit.retrofit.ext.dmr.exception.ConverterUnsupportedTypeException;
import org.touchbit.retrofit.ext.dmr.util.ConverterUtils;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileConverter implements ExtensionConverter<File> {

    @Override
    @EverythingIsNonNull
    public RequestBodyConverter requestBodyConverter(final Type type,
                                                     final Annotation[] parameterAnnotations,
                                                     final Annotation[] methodAnnotations,
                                                     final Retrofit retrofit) {
        return new RequestBodyConverter() {

            @Override
            @EverythingIsNonNull
            public RequestBody convert(Object body) {
                Objects.requireNonNull(body, "Parameter 'body' required");
                if (body instanceof File) {
                    File file = (File) body;
                    final byte[] data = wrap(() -> Files.readAllBytes(file.toPath()));
                    final MediaType mediaType = ConverterUtils.getMediaType(methodAnnotations);
                    return RequestBody.create(mediaType, data);
                }
                throw new ConverterUnsupportedTypeException(FileConverter.class, File.class, body.getClass());
            }

        };
    }

    @Override
    @EverythingIsNonNull
    public ResponseBodyConverter<File> responseBodyConverter(final Type type,
                                                             final Annotation[] methodAnnotations,
                                                             final Retrofit retrofit) {
        return new ResponseBodyConverter<File>() {

            @Override
            @Nullable
            public File convert(@Nullable ResponseBody value) {
                if (value == null || value.contentLength() == 0) {
                    return null;
                }
                final Path tempFile = wrap(() -> Files.createTempFile(null, null));
                return wrap(() -> Files.write(tempFile, value.bytes()).toFile());
            }

        };
    }
}