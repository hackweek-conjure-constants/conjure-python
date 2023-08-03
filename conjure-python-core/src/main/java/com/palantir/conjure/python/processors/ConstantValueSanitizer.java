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

package com.palantir.conjure.python.processors;

import com.palantir.conjure.spec.PrimitiveType;
import org.apache.commons.lang3.StringUtils;

public enum ConstantValueSanitizer {
    INSTANCE;

    public static String sanitize(PrimitiveType type, String value) {
        switch (type.get()) {
            case STRING:
            case RID:
            case BEARERTOKEN:
            case UUID:
            case DATETIME:
            case ANY:
                return String.format("\"%s\"", value);
            case BINARY:
                return String.format("0b%s", value);
            case BOOLEAN:
                return StringUtils.capitalize(value);
            case DOUBLE:
            case INTEGER:
            case SAFELONG:
                return value;
            case UNKNOWN:
                throw new IllegalArgumentException("unknown type: " + type);
        }
        return value;
    }
}
