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

package org.touchbit.retrofit.ext.dmr.util;

import okhttp3.Headers;
import okhttp3.MediaType;
import org.touchbit.retrofit.ext.dmr.client.header.ContentType;
import org.touchbit.retrofit.ext.dmr.client.response.IDualResponse;
import retrofit2.internal.EverythingIsNonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by Oleg Shaburov on 08.11.2021
 * shaburov.o.a@gmail.com
 */
public class ConverterUtils {

    private ConverterUtils() {
        throw new UnsupportedOperationException("Utility class. Instantiation is prohibited.");
    }

    /**
     * @param data - {@link String}
     * @return {@link Byte[]}
     */
    public static Byte[] toObjectByteArray(String data) {
        return toObjectByteArray(data.getBytes());
    }

    /**
     * @param bytes - byte[]
     * @return {@link Byte[]}
     */
    public static Byte[] toObjectByteArray(byte[] bytes) {
        Byte[] result = new Byte[bytes.length];
        Arrays.setAll(result, n -> bytes[n]);
        return result;
    }

    /**
     * @param bytes - {@link Byte[]}
     * @return byte[]
     */
    public static byte[] toPrimitiveByteArray(Byte[] bytes) {
        byte[] primiteveArray = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            primiteveArray[i] = bytes[i];
        }
        return primiteveArray;
    }

    /**
     * @param type - model type
     * @return true if {@param type} is implements the interface {@link IDualResponse}
     */
    @EverythingIsNonNull
    public static boolean isIDualResponse(final Type type) {
        return type instanceof ParameterizedType &&
                IDualResponse.class.isAssignableFrom((Class<?>) ((ParameterizedType) type).getRawType());
    }

    public static ContentType getContentType(@Nonnull final Annotation[] methodAnnotations) {
        final MediaType mediaType = getMediaType(methodAnnotations);
        return new ContentType(mediaType);
    }

    /**
     * Method to extract {@link okhttp3.Headers} from the list of request method annotations
     *
     * @param methodAnnotations - list of request method annotations
     * @return {@link Headers}
     */
    @Nonnull
    public static Headers getAnnotationHeaders(@Nullable final Annotation[] methodAnnotations) {
        Headers.Builder headersBuilder = new Headers.Builder();
        if (methodAnnotations != null) {
            for (Annotation annotation : methodAnnotations) {
                if (annotation instanceof retrofit2.http.Headers) {
                    for (String header : ((retrofit2.http.Headers) annotation).value()) {
                        String[] split = header.split(":");
                        String name = split[0].trim();
                        String value = split[1].trim();
                        headersBuilder.add(name, value);
                    }
                }
            }
        }
        return headersBuilder.build();
    }

    /**
     * Method to extract MediaType from the list of request method annotations
     *
     * @param methodAnnotations - list of request method annotations
     * @return {@link MediaType} or null
     */
    @Nullable
    public static MediaType getMediaType(@Nonnull final Annotation[] methodAnnotations) {
        Headers headers = getAnnotationHeaders(methodAnnotations);
        String contentType = headers.get("Content-Type");
        if (contentType == null) {
            return null;
        }
        return MediaType.parse(contentType);
    }

}
