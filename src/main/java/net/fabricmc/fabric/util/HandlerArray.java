/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
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

package net.fabricmc.fabric.util;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HandlerArray<T> implements HandlerRegistry<T> {
	private T[] array;

    @SuppressWarnings("unchecked")
	public HandlerArray(Class<T> theClass) {
		this.array = (T[]) Array.newInstance(theClass, 0);
	}

	@Override
	public void register(T handler) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == handler) {
				throw new RuntimeException("Handler " + handler + " already registered!");
			}
		}

		T[] newArray = Arrays.copyOf(array, array.length + 1);
		newArray[array.length] = handler;
		array = newArray;
	}

	public T[] getBackingArray() {
		return array;
	}
}
