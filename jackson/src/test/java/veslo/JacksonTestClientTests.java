/*
 * Copyright 2021-2022 Shaburov Oleg
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

import internal.test.utils.BaseUnitTest;
import okhttp3.Interceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import veslo.client.adapter.UniversalCallAdapterFactory;
import veslo.client.inteceptor.CompositeInterceptor;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@SuppressWarnings({"unused", "ConstantConditions"})
@DisplayName("JacksonTestClient.class unit tests")
public class JacksonTestClientTests extends BaseUnitTest {

    private static final String URL = "http://localhost";
    private static final Interceptor INTERCEPTOR = new CompositeInterceptor();
    private static final UniversalCallAdapterFactory CA_FACTORY = new UniversalCallAdapterFactory();
    private static final Class<Client> CLI_CLASS = Client.class;

    @Test
    @DisplayName("Utility class")
    public void test1643953968253() {
        assertUtilityClassException(JacksonTestClient.class);
    }

    @Nested
    @DisplayName("#build() method tests")
    public class BuildMethodTests {

        @Test
        @DisplayName("All parameters required")
        public void test1643953970780() {
            assertNPE(() -> JacksonTestClient.build(null, INTERCEPTOR, CA_FACTORY, CLI_CLASS), "baseUrl");
            assertNPE(() -> JacksonTestClient.build(URL, null, CA_FACTORY, CLI_CLASS), "interceptor");
            assertNPE(() -> JacksonTestClient.build(URL, INTERCEPTOR, null, CLI_CLASS), "callAdapterFactory");
            assertNPE(() -> JacksonTestClient.build(URL, INTERCEPTOR, CA_FACTORY, null), "clientClass");
        }

        @Test
        @DisplayName("Successful test client creation")
        public void test1643953974029() {
            final Client client = JacksonTestClient.build(URL, INTERCEPTOR, CA_FACTORY, CLI_CLASS);
            assertThat(client, not(nullValue()));
        }

    }

}