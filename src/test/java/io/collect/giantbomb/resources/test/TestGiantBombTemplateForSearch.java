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
public class TestGiantBombTemplateForSearch {

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	GiantBombTemplate gbTemplate;

	@Autowired
	RestTemplate restTemplate;

	MockRestServiceServer mockServer;

	private String searchSor;
	private String searchSor_filtered;

	@Before
	public void setup() throws IOException {
		mockServer = MockRestServiceServer.createServer(restTemplate);
		// read json from file
		if (searchSor == null) {
			searchSor = FileUtils.readFileToString(
					resourceLoader.getResource("classpath:search-SOR.json")
							.getFile(), "UTF-8");
		}
		if (searchSor_filtered == null) {
			searchSor_filtered = FileUtils.readFileToString(resourceLoader
					.getResource("classpath:search-SOR-filtered.json")
					.getFile(), "UTF-8");
		}

	}

	@After
	public void teardown() {
	}

	@Test
	public void testSearchWithoutFilter() throws Exception {
		// create server mock
		mockServer
				.expect(requestTo(new StringStartsWith(
						GiantBombTemplate.BASE_URI + "/search/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(searchSor, MediaType.APPLICATION_JSON));

		String query = "\"Streets of Rage\"";
		GiantBombMultiResourceResponse<GiantBombGame> searchresult = gbTemplate
				.searchForGame(query);
		mockServer.verify();

		// System.out.println(game);
		Assert.assertEquals(7, searchresult.number_of_total_results);
		Assert.assertEquals(7, searchresult.number_of_page_results);
		Assert.assertEquals("Streets of Rage", searchresult.results.get(0).name);
		Assert.assertNull(searchresult.results.get(0).aliases);
		Assert.assertNotNull(searchresult.results.get(0).deck);

		Assert.assertEquals(8, searchresult.results.get(0).platforms.length);
	}

	@Test
	public void testSearchWithFilter() throws Exception {

		// create server mock
		mockServer
				.expect(requestTo(new StringStartsWith(
						GiantBombTemplate.BASE_URI + "/search/")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(
						withSuccess(searchSor_filtered,
								MediaType.APPLICATION_JSON));

		String query = "\"Streets of Rage\"";
		GiantBombMultiResourceResponse<GiantBombGame> searchresult = gbTemplate
				.searchForGame(query, new GiantBombRequestOptions("name"));
		mockServer.verify();

		Assert.assertEquals(7, searchresult.number_of_total_results);
		Assert.assertEquals(7, searchresult.number_of_page_results);
		Assert.assertEquals("Streets of Rage", searchresult.results.get(0).name);
		Assert.assertNull(searchresult.results.get(0).aliases);
		Assert.assertNull(searchresult.results.get(0).deck);

		Assert.assertNull(searchresult.results.get(0).platforms);
	}

}
