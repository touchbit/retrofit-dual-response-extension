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

package org.touchbit.retrofit.veslo.example.model.pet;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.touchbit.retrofit.veslo.asserter.HeadersAsserter;
import org.touchbit.retrofit.veslo.asserter.ResponseAsserter;
import org.touchbit.retrofit.veslo.asserter.SoftlyAsserter;
import org.touchbit.retrofit.veslo.example.model.AssertableModel;
import org.touchbit.retrofit.veslo.example.model.Status;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pet
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true, fluent = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonAutoDetect(creatorVisibility = ANY, fieldVisibility = ANY)
public class Pet extends AssertableModel<Pet> {

    private @NotNull @Min(0) Long id = null;
    private @NotNull @Size(max = 10) List<@Size(min = 1, max = 255) String> photoUrls;
    private @Valid Category category = null;
    private @Size(min = 1, max = 255) String name = null;
    private @NotNull @Size(max = 10) List<@Valid Tag> tags;
    private PetStatus status;

    public static void assertPetResponse(ResponseAsserter<Pet, Status, HeadersAsserter> asserter, Pet expected) {
        asserter.assertHttpStatusCodeIs(200)
                .assertSucBody((softly, act) -> act.assertPet(asserter, expected)); // first use case
//              .assertSucBody(pet -> pet.assertPet(expected)); // second use case
    }

    public void assertPet(SoftlyAsserter asserter, Pet expected) {
        asserter.ignoreNPE(true);
        asserter.softly(() -> assertThat(this.id()).as("Pet.id").isEqualTo(expected.id()));
        asserter.softly(() -> assertThat(this.name()).as("Pet.name").isEqualTo(expected.name()));
        asserter.softly(() -> assertThat(this.photoUrls()).as("Pet.photoUrls").isEqualTo(expected.photoUrls()));
        asserter.softly(() -> assertThat(this.tags()).as("Pet.tags").containsAll(expected.tags()));
        asserter.softly(() -> assertThat(this.category()).as("Pet.category").isNotNull());
        asserter.softly(() -> this.category().assertCategory(asserter, expected.category()));
    }

    public void assertPet(Pet expected) {
        try (SoftlyAsserter asserter = SoftlyAsserter.get()) {
            asserter.ignoreNPE(true);
            asserter.softly(() -> assertThat(this.id()).as("Pet.id").isEqualTo(expected.id()));
            asserter.softly(() -> assertThat(this.name()).as("Pet.name").isEqualTo(expected.name()));
            asserter.softly(() -> assertThat(this.photoUrls()).as("Pet.photoUrls").isEqualTo(expected.photoUrls()));
            asserter.softly(() -> assertThat(this.tags()).as("Pet.tags").containsAll(expected.tags()));
            asserter.softly(() -> assertThat(this.category()).as("Pet.category").isNotNull());
            asserter.softly(() -> this.category().assertCategory(asserter, expected.category()));
        }
    }

}
