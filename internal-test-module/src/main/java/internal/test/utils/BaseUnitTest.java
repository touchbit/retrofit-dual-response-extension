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

package internal.test.utils;

import internal.test.utils.asserter.ThrowableAsserter;
import internal.test.utils.asserter.ThrowableRunnable;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static internal.test.utils.TestUtils.array;
import static internal.test.utils.TestUtils.getGenericReturnTypeForMethod;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"unused", "rawtypes"})
public abstract class BaseUnitTest {

    protected static final String INFO = "endpointInfo";
    protected static final Annotation[] AA = array();
    protected static final Retrofit RTF = RetrofitTestUtils.retrofit();
    protected static final Class<Object> OBJ_C = Object.class;
    protected static final Type OBJ_T = OBJ_C;
    protected static final Class<String> STRING_C = String.class;
    protected static final Type STRING_T = STRING_C;
    protected static final Class<Character> CHARACTER_C = Character.class;
    protected static final Type CHARACTER_T = CHARACTER_C;
    protected static final Class<Boolean> BOOLEAN_C = Boolean.class;
    protected static final Type BOOLEAN_T = BOOLEAN_C;
    protected static final Class<Byte> BYTE_C = Byte.class;
    protected static final Type BYTE_T = BYTE_C;
    protected static final Class<Integer> INTEGER_C = Integer.class;
    protected static final Type INTEGER_T = INTEGER_C;
    protected static final Class<Double> DOUBLE_C = Double.class;
    protected static final Type DOUBLE_T = DOUBLE_C;
    protected static final Class<Float> FLOAT_C = Float.class;
    protected static final Type FLOAT_T = FLOAT_C;
    protected static final Class<Long> LONG_C = Long.class;
    protected static final Type LONG_T = LONG_C;
    protected static final Class<Short> SHORT_C = Short.class;
    protected static final Type SHORT_T = SHORT_C;
    protected static final Class<Character> PRIMITIVE_CHARACTER_C = Character.TYPE;
    protected static final Type PRIMITIVE_CHARACTER_T = PRIMITIVE_CHARACTER_C;
    protected static final Class<Boolean> PRIMITIVE_BOOLEAN_C = Boolean.TYPE;
    protected static final Type PRIMITIVE_BOOLEAN_T = PRIMITIVE_BOOLEAN_C;
    protected static final Class<Byte> PRIMITIVE_BYTE_C = Byte.TYPE;
    protected static final Type PRIMITIVE_BYTE_T = PRIMITIVE_BYTE_C;
    protected static final Class<Integer> PRIMITIVE_INTEGER_C = Integer.TYPE;
    protected static final Type PRIMITIVE_INTEGER_T = PRIMITIVE_INTEGER_C;
    protected static final Class<Double> PRIMITIVE_DOUBLE_C = Double.TYPE;
    protected static final Type PRIMITIVE_DOUBLE_T = PRIMITIVE_DOUBLE_C;
    protected static final Class<Float> PRIMITIVE_FLOAT_C = Float.TYPE;
    protected static final Type PRIMITIVE_FLOAT_T = PRIMITIVE_FLOAT_C;
    protected static final Class<Long> PRIMITIVE_LONG_C = Long.TYPE;
    protected static final Type PRIMITIVE_LONG_T = PRIMITIVE_LONG_C;
    protected static final Class<Short> PRIMITIVE_SHORT_C = Short.TYPE;
    protected static final Type PRIMITIVE_SHORT_T = PRIMITIVE_SHORT_C;
    protected static final Type STRING_ARRAY_T = String[].class;
    protected static final Class<String[]> STRING_ARRAY_C = String[].class;
    protected static final Type STRING_SET_T = getGenericReturnTypeForMethod(BaseGenericTypes.class, "stringSetType");
    protected static final Class<Set> SET_C = Set.class;
    protected static final Type STRING_LIST_T = getGenericReturnTypeForMethod(BaseGenericTypes.class, "stringListType");
    protected static final Class<List> LIST_C = List.class;
    protected static final Type STRING_MAP_T = getGenericReturnTypeForMethod(BaseGenericTypes.class, "stringMapType");
    protected static final Class<Map> MAP_C = Map.class;
    protected static final Logger UNIT_TEST_LOGGER = LoggerFactory.getLogger(BaseUnitTest.class);

    public static ThrowableAsserter assertThrow(ThrowableRunnable runnable) {
        return new ThrowableAsserter(runnable);
    }

    public ThrowableAsserter assertNPE(ThrowableRunnable runnable, String parameter) {
        return new ThrowableAsserter(runnable).assertNPE(parameter);
    }

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(reason, actual, matcher);
    }

    public static <DTO> void assertIs(DTO actual, DTO expected) {
        assertThat(actual, is(expected));
    }

    protected Annotation[] getAnyAnnotations() {
        return Retention.class.getAnnotations();
    }

    protected String fileToString(Path file) {
        if (file == null) {
            return null;
        }
        return fileToString(file.toFile());
    }

    protected String fileToString(File file) {
        if (file == null) {
            return null;
        }
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new CorruptedTestException(e);
        }
    }

    private interface BaseGenericTypes {
        Set<String> stringSetType();

        List<String> stringListType();

        Map<String, String> stringMapType();

        String[] stringArrayType();
    }

    public interface RetrofitCallClient {

        @GET("/api/call")
        Call<Object> call();

    }

}