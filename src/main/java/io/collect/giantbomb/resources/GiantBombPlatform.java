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
public class GiantBombPlatform extends GiantBombResource {

	public static final String ID_PREFIX = "3045-";

	/**
	 * Abbreviation of the platform.
	 */
	public String abbreviation;
	/**
	 * Company that created the platform.
	 */
	public GiantBombResource company;
	/**
	 * Main image of the platform.
	 */
	public GiantBombImage image;
	// install_base Approximate number of sold hardware units.
	// online_support Flag indicating whether the platform has online
	// capabilities.
	// original_price Initial price point of the platform.
	// release_date Date of the platform.

}
