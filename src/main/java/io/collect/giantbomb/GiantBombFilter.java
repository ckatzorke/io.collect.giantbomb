/*
 * Copyright (C) Christian Katzorke <ckatzorke@gmail.com>
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
package io.collect.giantbomb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * Filter class to represent an (optional) filtering mechanism when requesting a
 * GiantBomb resource. The filter can be set in the URI using
 * <code>filter=fieldname_1:fieldvalue_1|...|fiedvalue_n,fieldname_2=fieldvalue_1|..|fieldvalue_n</code>
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class GiantBombFilter {

	private Map<String, List<String>> fieldFilter = new HashMap<>();

	public GiantBombFilter() {
		//
	}

	public GiantBombFilter(Map<String, List<String>> filterMap) {
		this.fieldFilter = filterMap;
	}

	public void addFilter(String field, String... values) {
		fieldFilter.put(field, Arrays.asList(values));
	}

	public String toString() {
		if (fieldFilter != null && !fieldFilter.isEmpty()) {
			StringBuffer buff = new StringBuffer("");
			fieldFilter.forEach((field, values) -> {
				buff.append(field)
						.append(":")
						.append(StringUtils.collectionToDelimitedString(values,
								"|")).append(",");
			});
			return buff.toString();
		}
		return "";
	}
}
