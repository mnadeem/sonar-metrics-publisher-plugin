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
package org.sonar.plugins.publisher.config;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.BatchExtension;
import org.sonar.api.ServerExtension;
import org.sonar.api.config.Settings;

public class PublisherSettings implements BatchExtension, ServerExtension {

	public static final String LISTENER_URL = "sonar.publisher.listener-url";
	public static final String USER_NAME = "sonar.publisher.user-name";
	public static final String PASSWORD = "sonar.publisher.password";
	
	/** Sonar Property for Enabling / Disabling THIS Plugin. */
	public static final String SKIP_PUBLISHING = "sonar.publisher.skip";

	private Settings sonarSettings;

	public PublisherSettings(final Settings settings) {
		this.sonarSettings = settings;
	}

	public String getListenerURL() {
		return this.sonarSettings.getString(LISTENER_URL);
	}

	public void setListenerURL(String LISTENER_URL) {
		this.sonarSettings.setProperty(LISTENER_URL, LISTENER_URL);
	}

	public String getUserName() {
		return this.sonarSettings.getString(USER_NAME);
	}


	public void setUserName(String userName) {
		this.sonarSettings.setProperty(USER_NAME, userName);
	}

	public String getPassword() {
		return this.sonarSettings.getString(PASSWORD);
	}

	public void setPassword(String password) {
		this.sonarSettings.setProperty(PASSWORD, password);
	}
	
	public boolean isEnabled() {
		return this.sonarSettings.getBoolean(SKIP_PUBLISHING);
	}

	public void enablePublishing(boolean enable) {
		this.sonarSettings.setProperty(SKIP_PUBLISHING, enable);
	}

	public boolean missingMandatoryParameters() {
	    return StringUtils.isNotBlank(getListenerURL());
	}
}
