/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2023 DBeaver Corp and others
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
package org.jkiss.dbeaver.model.data.aggregate;

import java.util.ArrayList;
import java.util.List;

/**
 * Mode
 */
public class FunctionMode implements IAggregateFunction {

    private final List<Object> cache = new ArrayList<>();

    @Override
    public boolean accumulate(Object value, boolean aggregateAsStrings) {
        Number num = FunctionNumeric.getNumeric(value);
        if (num != null) {
            value = num;
        }
        if (value != null) {
            cache.add(value);
            return true;
        }
        return false;
    }

    @Override
    public Object getResult(int valueCount) {
        Object maxValue = null;
        int maxCount = 0;

        for (int i = 0; i < cache.size(); ++i) {
            int count = 0;
            for (int j = 0; j < cache.size(); ++j) {
                if (cache.get(j).equals(cache.get(i))) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = cache.get(i);
            }
        }
//        if (maxCount <= 1) {
//            return null;
//        }
        return maxValue;
    }
}
