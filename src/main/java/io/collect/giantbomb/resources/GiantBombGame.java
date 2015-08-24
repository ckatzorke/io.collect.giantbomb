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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Christian Katzorke ckatzorke@gmail.com
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiantBombGame extends GiantBombResource {
	
	public static final String ID_PREFIX = "3030-";


	/**
	 * List of aliases the game is known by. A \n (newline) seperates each
	 * alias.
	 */
	public String aliases;

	/**
	 * Genres that encompass the game.
	 */
	public GiantBombGenre[] genres;
	/**
	 * Main image of the game.
	 */
	public GiantBombImage image;
	/**
	 * List of images associated to the game.
	 */
	public GiantBombImage[] images;
	/**
	 * Platforms the game appeared in.
	 */
	public GiantBombPlatform[] platforms;
	/**
	 * Companies who published the game.
	 */
	public GiantBombPublisher[] publishers;
	//public Video[] videos; Videos associated to the game.

	// characters Characters related to the game.
	// concepts Concepts related to the game.
	// developers Companies who developed the game.
	// expected_release_day Expected day the game will be released. The month is
	// represented numerically. Combine with 'expected_release_day',
	// 'expected_release_month', 'expected_release_year' and
	// 'expected_release_quarter' for Giant Bomb's best guess release date of
	// the game. These fields will be empty if the 'original_release_date' field
	// is set.
	// expected_release_month Expected month the game will be released. The
	// month is represented numerically. Combine with 'expected_release_day',
	// 'expected_release_quarter' and 'expected_release_year' for Giant Bomb's
	// best guess release date of the game. These fields will be empty if the
	// 'original_release_date' field is set.
	// expected_release_quarter Expected quarter game will be released. The
	// quarter is represented numerically, where 1 = Q1, 2 = Q2, 3 = Q3, and 4 =
	// Q4. Combine with 'expected_release_day', 'expected_release_month' and
	// 'expected_release_year' for Giant Bomb's best guess release date of the
	// game. These fields will be empty if the 'original_release_date' field is
	// set.
	// expected_release_year Expected year game will be released. Combine with
	// 'expected_release_day', 'expected_release_month' and
	// 'expected_release_quarter' for Giant Bomb's best guess release date of
	// the game. These fields will be empty if the 'original_release_date' field
	// is set.
	// first_appearance_characters Characters that first appeared in the game.
	// first_appearance_concepts Concepts that first appeared in the game.
	// first_appearance_locations Locations that first appeared in the game.
	// first_appearance_objects Objects that first appeared in the game.
	// first_appearance_people People that were first credited in the game.
	// franchises Franchises related to the game.
	// killed_characters Characters killed in the game.
	// locations Locations related to the game.
	// number_of_user_reviews Number of user reviews of the game on Giant Bomb.
	// objects Objects related to the game.
	// original_game_rating Rating of the first release of the game.
	// original_release_date Date the game was first released.
	// people People who have worked with the game.
	// releases Releases of the game.
	// reviews Staff reviews of the game.
	// similar_games Other games similar to the game.
	// themes Themes that encompass the game.
}
