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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GiantBombResource {

	/**
	 * Unique ID of the resource (without a prefix).
	 */
	public int id;
	/**
	 * Name of the resource.
	 */
	public String name;
	/**
	 * URL pointing to the resource detail api endpoint.
	 */
	public String api_detail_url;
	/**
	 * URL pointing to the descriptive site of the resource on Giant Bomb.
	 */
	public String site_detail_url;

	/**
	 * Date the game was added to Giant Bomb.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date date_added;
	/**
	 * Date the game was last updated on Giant Bomb.
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date date_last_updated;
	/**
	 * Brief summary of the game.
	 */
	public String deck;
	/**
	 * Description of the game.
	 */
	public String description;

}
