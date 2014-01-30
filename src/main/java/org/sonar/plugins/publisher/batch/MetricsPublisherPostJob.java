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
package org.sonar.plugins.publisher.batch;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.CheckProject;
import org.sonar.api.batch.Phase;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.Project;
import org.sonar.plugins.java.Java;
import org.sonar.plugins.publisher.config.PublisherSettings;
import org.sonar.plugins.publisher.support.MetricsData;
import org.sonar.plugins.publisher.support.PublisherAdapter;

@Phase(name = Phase.Name.POST)
public class MetricsPublisherPostJob implements PostJob, CheckProject {

	private static final Logger LOG = LoggerFactory.getLogger(MetricsPublisherPostJob.class);

	private PublisherSettings settings;
	private PublisherAdapter publisherAdapter;

	public MetricsPublisherPostJob(final PublisherSettings newSettings, final PublisherAdapter newPublisherAdapter) {
		this.settings = newSettings;
		this.publisherAdapter = newPublisherAdapter;
	}

	public boolean shouldExecuteOnProject(Project project) {
		return StringUtils.equals(project.getLanguageKey(), Java.KEY)
				&& !project.isRoot()
				&& !this.settings.missingMandatoryParameters();
	}

	public void executeOn(Project project, SensorContext context) {
		LOG.debug("Execucing on project {}", project.getName());
		try {
			this.publisherAdapter.publish(buildData(project, context));
		} catch (IOException e) {
			LOG.error("Error", e);
		}		
	}

	private MetricsData buildData(Project project, SensorContext context) {
		MetricsData metricsData = new MetricsData(project);

		return metricsData;
	}
}
