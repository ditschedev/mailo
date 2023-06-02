package dev.ditsche.mailo.util;

/*
 * Copyright 2002-2003,2009 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.InputStream;


/**
 * A tiny helper that optimizes the loading of resources.
 *
 * @author Tobias Dittmann
 * @version 0.1.0
 */
public class ClassLoaderUtil {

    /**
     * This is a convenience method to load a resource as a stream.
     *
     *
     * @param resourceName The name of the resource to load
     * @param callingClass The Class object of the calling object
     */
    public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);

        if (inputStream == null) {
            inputStream = ClassLoaderUtil.class.getClassLoader().getResourceAsStream(resourceName);
        }

        if (inputStream == null) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                inputStream = cl.getResourceAsStream(resourceName);
            }
        }

        if ((inputStream == null) && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) {
            return getResourceAsStream('/' + resourceName, callingClass);
        }

        return inputStream;
    }

    public static boolean canLoadResource(String resourceName, Class<?> callingClass) {
        try (InputStream inputStream = getResourceAsStream(resourceName, callingClass);) {
            return inputStream != null;
        } catch (IOException e) {
            return false;
        }
    }

}
