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
package io.collect.giantbomb.resources.test;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import io.collect.giantbomb.GiantBombRequestOptions;
import io.collect.giantbomb.GiantBombTemplate;
import io.collect.giantbomb.resources.GiantBombGame;
import io.collect.giantbomb.resources.GiantBombSingleResourceResponse;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringStartsWith;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class TestGiantBombTemplateForGameResource {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	GiantBombTemplate gbTemplate;

	@Autowired
	RestTemplate restTemplate;

	MockRestServiceServer mockServer;

	String id = "3030-4372";
	private String dott;
	private String dott_filtered;
	private String not_found;

	@Before
	public void setup() throws IOException {
		mockServer = MockRestServiceServer.createServer(restTemplate);
		// read json from file
		if (dott == null) {
			dott = FileUtils.readFileToString(
					resourceLoader.getResource("classpath:" + id + ".json")
							.getFile(), "UTF-8");
		}
		if (dott_filtered == null) {
			dott_filtered = FileUtils.readFileToString(
					resourceLoader.getResource("classpath:" + id + "-filtered.json")
							.getFile(), "UTF-8");
		}
		if (not_found == null) {
			not_found= FileUtils.readFileToString(
					resourceLoader.getResource("classpath:" + id + "_notfound.json")
							.getFile(), "UTF-8");
		}

	}

	@After
	public void teardown() {
		//
	}

	@Test
	public void testGetGameWithoutFilter() throws Exception {
		// create server mock
		mockServer
				.expect(requestTo(new StringStartsWith(
						GiantBombTemplate.BASE_URI + "/game/" + id + "/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(dott, MediaType.APPLICATION_JSON));

		GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate
				.getForGame("3030-4372");

		mockServer.verify();

		// System.out.println(game);
		Assert.assertEquals("Maniac Mansion: Day of the Tentacle",
				game.results.name);
		Assert.assertEquals("DOTT", game.results.aliases);
		Assert.assertNotNull(game.results.deck);

		Assert.assertEquals("Adventure", game.results.genres[0].name);
		Assert.assertEquals(5, game.results.platforms.length);
	}
	
//	@Test
	public void testGetGameThatDoesNotExist() throws Exception {
		// create server mock
		mockServer
				.expect(requestTo(new StringStartsWith(
						GiantBombTemplate.BASE_URI + "/game/" + id + "/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(not_found, MediaType.APPLICATION_JSON));

		GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate
				.getForGame("3030-4372");

		mockServer.verify();

		System.out.println(game);
		Assert.assertEquals("Object Not Found", game.error);
	}

	@Test
	public void testGetGameWithoutFilterWithoutPrefix() throws Exception {

		// create server mock
		mockServer
				.expect(requestTo(new StringStartsWith(
						GiantBombTemplate.BASE_URI + "/game/" + id + "/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(dott, MediaType.APPLICATION_JSON));

		GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate
				.getForGame("4372");

		mockServer.verify();

		// System.out.println(game);
		Assert.assertEquals("Maniac Mansion: Day of the Tentacle",
				game.results.name);
		Assert.assertEquals("DOTT", game.results.aliases);
		Assert.assertNotNull(game.results.deck);
	}

	@Test
	public void testGetGameWithFilter() throws Exception {
		// create server mock
		mockServer
				.expect(requestTo(new StringContains("&field_list=name,deck")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(dott_filtered, MediaType.APPLICATION_JSON));

		GiantBombSingleResourceResponse<GiantBombGame> game = gbTemplate
				.getForGame("3030-4372", new GiantBombRequestOptions("name", "deck"));

		mockServer.verify();

		// System.out.println(game);
		Assert.assertEquals("Maniac Mansion: Day of the Tentacle",
				game.results.name);
		Assert.assertNotNull(game.results.deck);
		Assert.assertNull(game.results.aliases);
	}

}
