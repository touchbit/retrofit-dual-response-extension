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

package org.touchbit.retrofit.veslo.example.client;

import io.qameta.allure.Description;
import org.touchbit.retrofit.veslo.example.client.transport.ExampleCustomResponse;
import org.touchbit.retrofit.veslo.example.client.transport.querymap.LoginUserQueryMap;
import org.touchbit.retrofit.veslo.example.model.Status;
import org.touchbit.retrofit.veslo.example.model.user.User;
import retrofit2.http.*;

import java.util.List;

@SuppressWarnings("unused")
public interface UserApi {

    /**
     * @param body Created {@link User} object (required)
     */
    @POST("/v2/user")
    @Description("Creates user")
    ExampleCustomResponse<User, Status> createUser(@Body Object body);

    /**
     * @param body {@link List<User>} of user object (required)
     */
    @POST("/v2/user/createWithList")
    @Description("Creates list of users")
    ExampleCustomResponse<List<User>, Status> createUsers(@Body Object body);

    /**
     * @param username - {@link String} name that needs to be deleted (required)
     */
    @DELETE("/v2/user/{username}")
    @Description("Delete user")
    ExampleCustomResponse<Status, Status> deleteUser(@Path("username") Object username);

    /**
     * @param username {@link String} name that needs to be fetched. Use user1 for testing.  (required)
     */
    @GET("/v2/user/{username}")
    @Description("Get user by user name")
    ExampleCustomResponse<User, Status> getUserByName(@Path("username") Object username);

    /**
     * @param username {@link String} user name for login (required)
     * @param password {@link String} password for login in clear text (required)
     */
    @GET("/v2/user/login")
    @Description("Logs user into the system")
    ExampleCustomResponse<Status, Status> loginUser(@Query("username") Object username, @Query("password") Object password);

    /**
     * @param queryMap user name & password for login (all required)
     */
    @GET("/v2/user/login")
    @Description("Logs user into the system")
    ExampleCustomResponse<Status, Status> loginUser(@QueryMap() LoginUserQueryMap queryMap);

    @GET("/v2/user/logout")
    @Description("Logs out current logged in user session")
    ExampleCustomResponse<Status, Status> logoutUser();

    /**
     * @param body     Updated {@link User} object (required)
     * @param username {@link String} name that need to be updated (required)
     */
    @PUT("/v2/user/{username}")
    @Description("Update user by username")
    ExampleCustomResponse<User, Status> updateUser(@Body Object body, @Path("username") Object username);

}
