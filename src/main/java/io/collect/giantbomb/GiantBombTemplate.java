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

import io.collect.giantbomb.config.GiantBombProperties;
import io.collect.giantbomb.resources.GiantBombGame;
import io.collect.giantbomb.resources.GiantBombMultiResourceResponse;
import io.collect.giantbomb.resources.GiantBombPlatform;
import io.collect.giantbomb.resources.GiantBombSingleResourceResponse;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@Component
public class GiantBombTemplate {

	public static final String BASE_URI = "http://www.giantbomb.com/api";

	@Autowired
	private GiantBombProperties props;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Generic GET method for resources /games
	 * 
	 * @param options
	 * @return
	 */
	public GiantBombMultiResourceResponse<GiantBombGame> getForGames(
			GiantBombRequestOptions options) {
		ResponseEntity<GiantBombMultiResourceResponse<GiantBombGame>> responseEntity = restTemplate
				.exchange(
						BASE_URI
								+ "/games/?api_key={apikey}&format=json&limit={limit}&offset={offset}&field_list={field_list}&sort={sort}&filter={filter}",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<GiantBombMultiResourceResponse<GiantBombGame>>() {
						}, props.getApikey(), options.limit, options.offset,
						createFieldListParameter(options.fieldList),
						options.sort == null ? "" : options.sort.toString(),
						options.filter == null ? "" : options.filter.toString());
		GiantBombMultiResourceResponse<GiantBombGame> resources = responseEntity
				.getBody();
		return resources;
	}

	/**
	 * Generic GET method for resources /platforms
	 * 
	 * @param options
	 * @return
	 */
	public GiantBombMultiResourceResponse<GiantBombPlatform> getForPlatforms(
			GiantBombRequestOptions options) {
		ResponseEntity<GiantBombMultiResourceResponse<GiantBombPlatform>> responseEntity = restTemplate
				.exchange(
						BASE_URI
								+ "/platforms/?api_key={apikey}&format=json&limit={limit}&offset={offset}&field_list={field_list}&sort={sort}&filter={filter}",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<GiantBombMultiResourceResponse<GiantBombPlatform>>() {
						}, props.getApikey(), options.limit, options.offset,
						createFieldListParameter(options.fieldList),
						options.sort == null ? "" : options.sort.toString(),
						options.filter == null ? "" : options.filter.toString());
		GiantBombMultiResourceResponse<GiantBombPlatform> resources = responseEntity
				.getBody();
		return resources;
	}

	/**
	 * Connects to giantbomb api to query a dedicated game (passed via id). The
	 * connected url looks like:<br>
	 * <code>http://www.giantbomb.com/api/game/3030-4372?api_key=[apikey]&format=json</code>
	 * 
	 * @param id
	 * @return
	 */
	public GiantBombSingleResourceResponse<GiantBombGame> getForGame(String id) {
		return getForGame(id, new GiantBombRequestOptions());
	}

	/**
	 * Connects to giantbomb api to query a dedicated game (passed via id), and
	 * additionally applies passed filter (only field_list filter is relevant).
	 * The connected url looks like:<br>
	 * <code>http://www.giantbomb.com/api/game/3030-4372?api_key=[apikey]&format=json&field_list=field1,field2</code>
	 * 
	 * @param id
	 * @param options
	 * @return
	 */
	public GiantBombSingleResourceResponse<GiantBombGame> getForGame(String id,
			GiantBombRequestOptions options) {
		String gameId = fixId(GiantBombGame.ID_PREFIX, id);
		ResponseEntity<GiantBombSingleResourceResponse<GiantBombGame>> responseEntity = restTemplate
				.exchange(
						BASE_URI
								+ "/game/{id}/?api_key={apikey}&format=json&limit={limit}&offset={offset}&field_list={field_list}",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<GiantBombSingleResourceResponse<GiantBombGame>>() {
						}, gameId, props.getApikey(), options.limit,
						options.offset,
						createFieldListParameter(options.fieldList));
		GiantBombSingleResourceResponse<GiantBombGame> game = responseEntity
				.getBody();
		return game;
	}

	/**
	 * Search for a game with given query. The query support common search
	 * syntax (quotes for exact, AND, OR)
	 * 
	 * For payload reasons, the default filter only contains id, name, deck,
	 * platforms and image.
	 * 
	 * @param query
	 * @return
	 */
	public GiantBombMultiResourceResponse<GiantBombGame> searchForGame(
			String query) {
		return searchForGame(query, new GiantBombRequestOptions("id", "name",
				"deck", "platforms", "image"));
	}

	/**
	 * Search for a game with given query. The query support common search
	 * syntax (quotes for exact, AND, OR). When specifying a custom filter, be
	 * careful with payload when specifying to many fields to be returned.
	 * 
	 * @param query
	 * @param options
	 * @return
	 */
	public GiantBombMultiResourceResponse<GiantBombGame> searchForGame(
			String query, GiantBombRequestOptions options) {
		ResponseEntity<GiantBombMultiResourceResponse<GiantBombGame>> responseEntity = restTemplate
				.exchange(
						BASE_URI
								+ "/search/?api_key={apikey}&format=json&query={query}&limit={limit}&offset={offset}&field_list={field_list}",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<GiantBombMultiResourceResponse<GiantBombGame>>() {
						}, props.getApikey(), query, options.limit,
						options.offset,
						createFieldListParameter(options.fieldList));
		GiantBombMultiResourceResponse<GiantBombGame> searchresult = responseEntity
				.getBody();
		return searchresult;
	}

	/**
	 * @param idPrefix
	 * @param id
	 * @return
	 */
	private String fixId(String idPrefix, String id) {
		if (!id.startsWith(idPrefix)) {
			return idPrefix + id;
		}
		return id;
	}

	/**
	 * @param fieldList
	 * @return
	 */
	private Object createFieldListParameter(List<String> fieldList) {
		StringBuilder builder = new StringBuilder("");
		for (Iterator<String> iter = fieldList.iterator(); iter.hasNext();) {
			String field = iter.next();
			builder.append(field);
			if (iter.hasNext()) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
}
