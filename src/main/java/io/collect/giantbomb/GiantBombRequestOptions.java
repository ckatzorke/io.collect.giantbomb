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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class or storing the request options when (optionally) filtering the resource
 * response. Following filter can be set:
 * <ul>
 * <li>fieldList - List of field names to include in the response. Use this if
 * you want to reduce the size of the response payload</li>
 * <li>limit - The number of results to display per page. This value defaults to
 * 100 and can not exceed this number.</li>
 * <li>offset - Return results starting with the object at the offset specified.
 * Defaults to ß</li>
 * <li>sort - the field to sort for either ascending or descending, only
 * applicable for resources (multi) like games, platforms, ... When no option
 * applied, the default order used by GiantBomb is used.</li>
 * <li>filter - The result can be filtered by the marked fields in the Fields
 * section below. Format: &filter=field:value1|value2,field:value</li>
 * </ul>
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public class GiantBombRequestOptions {

	public static final String FIELD_NAME = "name";
	public static final String FIELD_id = "id";
	public static final String FIELD_API_DETAIL_URL = "api_detail_url";
	public static final String FIELD_DATE_ADDED = "date_added";
	public static final String FIELD_DATE_LAST_UPDATED = "date_last_updated";
	public static final String FIELD_DECK = "deck";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_ABBREVIATION = "abbreviation";
	public static final String FIELD_COMPANY = "company";

	public GiantBombRequestOptions() {
		//
	}

	public GiantBombRequestOptions(String... fields) {
		this.fieldList = Arrays.asList(fields);
	}

	public GiantBombRequestOptions(int limit, int offset, GiantBombSort sort,
			GiantBombFilter filter, String... fields) {
		this.limit = limit;
		this.offset = offset;
		this.fieldList = Arrays.asList(fields);
		this.sort = sort;
		this.filter = filter;
	}

	public List<String> fieldList = new ArrayList<String>();
	public int limit = 100;
	public int offset = 0;
	public GiantBombSort sort = null;
	public GiantBombFilter filter;

}
