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
package io.collect.giantbomb.resources;



/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
public abstract class GiantBombResponse {
	/**
	 * An integer indicating the result of the request. Acceptable values are:
	 * 1:OK 100:Invalid API Key 
	 * 101:Object Not Found 
	 * 102:Error in URL Format
	 * 103:'jsonp' format requires a 'json_callback' argument 
	 * 104:Filter Error
	 * 105:Subscriber only video is for subscribers only
	 */
	public int status_code;
	/**
	 * A text string representing the status_code
	 */
	public String error;
	/**
	 * The number of total results matching the filter conditions specified
	 */
	public int number_of_total_results;
	/**
	 * The number of results on this page
	 */
	public int number_of_page_results;
	/**
	 * The value of the limit filter specified, or 100 if not specified
	 */
	public int limit = 100;
	/**
	 * The value of the offset filter specified, or 0 if not specified
	 */
	public int offset = 0;
	
	/**
	 * the api version
	 */
	public String version;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GiantBombResponse [status_code=" + status_code + ", error="
				+ error + ", number_of_total_results="
				+ number_of_total_results + ", number_of_page_results="
				+ number_of_page_results + ", limit=" + limit + ", offset="
				+ offset + ", version=" + version + "]";
	}
	
	
	
	
}
