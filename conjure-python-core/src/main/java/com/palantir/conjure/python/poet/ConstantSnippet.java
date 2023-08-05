/*
 * (c) Copyright 2023 Palantir Technologies Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.conjure.python.poet;

import com.palantir.conjure.spec.Documentation;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
public interface ConstantSnippet extends PythonSnippet {
    @Override
    @Value.Default
    default String idForSorting() {
        return className();
    }

    String className();

    String constantName();

    String constantValue();

    String myPyType();

    Optional<Documentation> docs();

    @Override
    default void emit(PythonPoetWriter poetWriter) {
        docs().ifPresent(docs -> poetWriter.writeIndentedLine("# " + docs.get().trim()));
        poetWriter.writeIndentedLine(String.format("%s: %s = %s", constantName(), myPyType(), constantValue()));
        poetWriter.writeLine();
    }

    class Builder extends ImmutableConstantSnippet.Builder {}

    static ConstantSnippet.Builder builder() {
        return new ConstantSnippet.Builder();
    }
}
