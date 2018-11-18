/*
 * (c) Copyright 2018 Palantir Technologies Inc. All rights reserved.
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

package com.palantir.gradle.graal;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;

/** Contains options and settings for tuning GraalVM use. */
public class GraalExtension {

    private final Property<String> mainClass;
    private final Property<String> outputName;
    private final ListProperty<String> options;

    public GraalExtension(Project project) {
        mainClass = project.getObjects().property(String.class);
        outputName = project.getObjects().property(String.class);
        options = project.getObjects().listProperty(String.class);
    }

    public final void mainClass(String value) {
        mainClass.set(value);
    }

    /** Returns the main class to use as the entry point to the generated executable file. */
    public final Provider<String> getMainClass() {
        return mainClass;
    }

    public final void outputName(String value) {
        outputName.set(value);
    }

    /**
     * Returns the outputName to use for the generated executable file.
     *
     * <p>Check {@link org.gradle.api.provider.Provider#isPresent()} to determine if an override has been set.</p>
     */
    public final Provider<String> getOutputName() {
        return outputName;
    }


    public final ListProperty<String> getOptions() {
        return this.options;
    }

    /**
     * Add option from https://github.com/oracle/graal/blob/master/substratevm/OPTIONS.md.
     */
    public final void option(String option) {
        if (option.trim().startsWith("-H:Name=")) {
            throw new GradleException("Use 'outputName' instead of '" + option + "'");
        }
        this.options.add(option);
    }

}
