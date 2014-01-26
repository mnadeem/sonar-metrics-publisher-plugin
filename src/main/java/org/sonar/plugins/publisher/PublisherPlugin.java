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
 package org.sonar.plugins.publisher;

import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;
import org.sonar.plugins.publisher.batch.MetricsPublisherPostJob;
import org.sonar.plugins.publisher.config.PublisherSettings;
import org.sonar.plugins.publisher.ui.PublisherWidget;

import com.google.common.collect.ImmutableList;

/**
 * This class is the entry point for all extensions
 */
@Properties({
    @Property(
        key = PublisherSettings.LISTENER_URL,
        name = "Listener URL",
        description = "Example: http://demo.app.org/ it should accept post request"),
    @Property(
            key = PublisherSettings.USER_NAME,
            name = "host user name",
            description = "if the host is secured"),
    @Property(
            key = PublisherSettings.PASSWORD,
            name = "host password",
            description = "if the host is secured")})
public final class PublisherPlugin extends SonarPlugin {

  // This is where you're going to declare all your Sonar extensions
public List<Class<? extends Extension>> getExtensions() {
    return ImmutableList.of(
        // Definitions
        PublisherMetrics.class,
        // Batch 
        MetricsPublisherPostJob.class,
        // UI
        PublisherWidget.class);
  }
}