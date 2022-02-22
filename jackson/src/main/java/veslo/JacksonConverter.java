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

package veslo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;
import veslo.client.converter.api.ExtensionConverter;
import veslo.util.ConvertUtils;
import veslo.util.Utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static veslo.constant.ParameterNameConstants.*;
import static veslo.constant.SonarRuleConstants.SONAR_TYPE_PARAMETER_NAMING;

/**
 * Jackson 2 converter
 * <p>
 *
 * @author Oleg Shaburov (shaburov.o.a@gmail.com)
 * Created: 08.11.2021
 */
@SuppressWarnings(SONAR_TYPE_PARAMETER_NAMING)
public class JacksonConverter<DTO> implements ExtensionConverter<DTO> {

    /**
     * {@link JacksonConverter} constant
     */
    public static final JacksonConverter<Object> INSTANCE = new JacksonConverter<>();

    /**
     * request body serializer
     */
    private final ObjectMapper requestObjectMapper;

    /**
     * response body deserializer
     */
    private final ObjectMapper responseObjectMapper;

    /**
     * Default constructor with default request/response jackson object mappers
     */
    public JacksonConverter() {
        this(new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).enable(INDENT_OUTPUT),
                new ObjectMapper().enable(FAIL_ON_NULL_FOR_PRIMITIVES));
    }

    /**
     * @param requestObjectMapper  - Jackson ObjectMapper for request conversation
     * @param responseObjectMapper - Jackson ObjectMapper for response conversation
     */
    public JacksonConverter(ObjectMapper requestObjectMapper, ObjectMapper responseObjectMapper) {
        this.requestObjectMapper = requestObjectMapper;
        this.responseObjectMapper = responseObjectMapper;
    }

    /**
     * @see ExtensionConverter#requestBodyConverter(Type, Annotation[], Annotation[], Retrofit)
     */
    @Override
    @EverythingIsNonNull
    public RequestBodyConverter requestBodyConverter(final Type type,
                                                     final Annotation[] parameterAnnotations,
                                                     final Annotation[] methodAnnotations,
                                                     final Retrofit retrofit) {
        Utils.parameterRequireNonNull(type, TYPE_PARAMETER);
        Utils.parameterRequireNonNull(parameterAnnotations, PARAMETER_ANNOTATIONS_PARAMETER);
        Utils.parameterRequireNonNull(methodAnnotations, METHOD_ANNOTATIONS_PARAMETER);
        Utils.parameterRequireNonNull(retrofit, RETROFIT_PARAMETER);
        return new RequestBodyConverter() {

            /**
             * Converting DTO model to their HTTP {@link RequestBody} representation
             *
             * @param body - DTO model
             * @return HTTP {@link RequestBody}
             */
            @Override
            @Nullable
            public RequestBody convert(@Nonnull Object body) {
                Utils.parameterRequireNonNull(body, BODY_PARAMETER);
                final ObjectMapper objectMapper = getRequestObjectMapper();
                final JavaType javaType = objectMapper.constructType(type);
                final ObjectWriter objectWriter = objectMapper.writerFor(javaType);
                final MediaType mediaType = ConvertUtils.getMediaType(methodAnnotations);
                try {
                    if (NULL_BODY_VALUE.equals(body)) {
                        return null;
                    } else if (NULL_JSON_VALUE.equals(body)) {
                        return RequestBody.create(mediaType, objectWriter.writeValueAsBytes(null));
                    } else {
                        return RequestBody.create(mediaType, objectWriter.writeValueAsBytes(body));
                    }
                } catch (Exception e) {
                    throw new ConvertCallException("Body not convertible to JSON. Body " + body.getClass(), e);
                }
            }

        };
    }

    /**
     * @see ExtensionConverter#responseBodyConverter(Type, Annotation[], Retrofit)
     */
    @Override
    @EverythingIsNonNull
    public ResponseBodyConverter<DTO> responseBodyConverter(final Type type,
                                                            final Annotation[] methodAnnotations,
                                                            final Retrofit retrofit) {
        Utils.parameterRequireNonNull(type, TYPE_PARAMETER);
        Utils.parameterRequireNonNull(methodAnnotations, METHOD_ANNOTATIONS_PARAMETER);
        Utils.parameterRequireNonNull(retrofit, RETROFIT_PARAMETER);
        return new ResponseBodyConverter<DTO>() {

            /**
             * Converting HTTP {@link ResponseBody} to their {@link DTO} model representation
             *
             * @param responseBody - HTTP {@link ResponseBody}
             * @return {@link DTO} model representation
             */
            @Override
            @Nullable
            public DTO convert(@Nullable ResponseBody responseBody) throws IOException {
                final String body = copyBody(responseBody);
                if (body == null || body.length() == 0) {
                    return null;
                }
                try {
                    final ObjectMapper objectMapper = getResponseObjectMapper();
                    final JavaType javaType = objectMapper.constructType(type);
                    return objectMapper.readerFor(javaType).readValue(body);
                } catch (Exception e) {
                    throw new ConvertCallException("\nResponse body not convertible to type " +
                            type + "\n" + e.getMessage(), e);
                }
            }
        };
    }

    /**
     * @return request body serializer
     */
    public ObjectMapper getRequestObjectMapper() {
        return requestObjectMapper;
    }

    /**
     * @return response body deserializer
     */
    public ObjectMapper getResponseObjectMapper() {
        return responseObjectMapper;
    }

}
