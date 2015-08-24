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
import io.collect.giantbomb.GiantBombSort;
import io.collect.giantbomb.GiantBombTemplate;
import io.collect.giantbomb.resources.GiantBombGame;
import io.collect.giantbomb.resources.GiantBombMultiResourceResponse;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
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
public class TestGiantBombTemplateForResources {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	GiantBombTemplate gbTemplate;

	@Autowired
	RestTemplate restTemplate;

	MockRestServiceServer mockServer;

	private String games_unsorted;
	private String games_sorted;

	@Before
	public void setup() throws IOException {
		mockServer = MockRestServiceServer.createServer(restTemplate);
		// // read json from file
		if (games_unsorted == null) {
			games_unsorted = FileUtils.readFileToString(resourceLoader
					.getResource("classpath:games-unsorted.json").getFile(),
					"UTF-8");
		}
		if (games_sorted == null) {
			games_sorted = FileUtils.readFileToString(resourceLoader
					.getResource("classpath:games-sorted.json").getFile(),
					"UTF-8");
		}

	}

	@After
	public void teardown() {
	}

	@Test
	public void testGetGames() throws Exception {
		// create server mock
		mockServer
				.expect(requestTo(new StringStartsWith(
						GiantBombTemplate.BASE_URI + "/games/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(
						withSuccess(games_unsorted, MediaType.APPLICATION_JSON));

		GiantBombRequestOptions options = new GiantBombRequestOptions(10, 0,
				null, null, "id", "name");
		GiantBombMultiResourceResponse<GiantBombGame> games = gbTemplate
				.getForGames(options);
		mockServer.verify();

		// System.out.println(game);
		Assert.assertEquals(45275, games.number_of_total_results);
		Assert.assertEquals(10, games.number_of_page_results);
		Assert.assertEquals("Desert Strike: Return to the Gulf",
				games.results.get(0).name);
		Assert.assertNull(games.results.get(0).aliases);
	}

	@Test
	public void testGetGamesSorted() throws Exception {
		// create server mock
		mockServer
				.expect(requestTo(new StringStartsWith(
						GiantBombTemplate.BASE_URI + "/games/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(
						withSuccess(games_sorted, MediaType.APPLICATION_JSON));

		GiantBombRequestOptions options = new GiantBombRequestOptions(10, 0,
				new GiantBombSort("date_added", false), null, "id", "name");
		GiantBombMultiResourceResponse<GiantBombGame> games = gbTemplate
				.getForGames(options);
		mockServer.verify();

		// System.out.println(game);
		Assert.assertEquals(45275, games.number_of_total_results);
		Assert.assertEquals(10, games.number_of_page_results);
		Assert.assertEquals("Assassin's Creed Chronicles: China",
				games.results.get(0).name);
		Assert.assertNull(games.results.get(0).aliases);
	}

}
