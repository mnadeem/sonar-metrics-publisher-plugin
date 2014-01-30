/*
 * SonarQube Publisher Plugin
 * Copyright (C) 2014 publisher
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.publisher.support;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.BatchExtension;
import org.sonar.api.ServerExtension;

import us.monoid.json.JSONObject;
import us.monoid.web.Content;
import us.monoid.web.Resty;
import static us.monoid.web.Resty.*;

public class PublisherAdapter implements BatchExtension, ServerExtension {
	
	private static final Logger LOG = LoggerFactory.getLogger(PublisherAdapter.class);

	private final Resty resty;

	public PublisherAdapter() {
		this.resty = new Resty();
	}

	public void publish(final AppData appData, final MetricsData data) throws IOException {
		LOG.debug("publishing data");
		//this.resty.authenticate(appData.getUrl(), appData.getUser(), appData.getPassword().toCharArray());
		this.resty.json(appData.getUrl(), put(new Content("text/plain;charset=UTF-8", new JSONObject(data).toString().getBytes())));
	}
}
