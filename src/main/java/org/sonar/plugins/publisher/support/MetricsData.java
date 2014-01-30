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

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

public class MetricsData {

	private String projectName;
	private Date analysisDate;
	private String description;
	private String language;
	private long warnAlertCount;
	private long errorAlertCount;

	public MetricsData(Project project) {
		this.projectName = project.getName();
		this.analysisDate = project.getAnalysisDate();
		this.description = project.getDescription();
		this.language = project.getLanguageKey();
	}

	public void addMeasure(Measure measure) {
		if (isWarningAlert(measure)) {
			warnAlertCount++;
		} else if(isErrorAlert(measure)) {
			errorAlertCount++;
		}
	}

	private boolean isWarningAlert(Measure measure) {
		return !CoreMetrics.ALERT_STATUS.equals(measure.getMetric()) && Metric.Level.WARN.equals(measure.getAlertStatus());
	}

	private boolean isErrorAlert(Measure measure) {
		return !CoreMetrics.ALERT_STATUS.equals(measure.getMetric()) && Metric.Level.ERROR.equals(measure.getAlertStatus());
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getAnalysisDate() {
		return analysisDate;
	}

	public void setAnalysisDate(Date analysisDate) {
		this.analysisDate = analysisDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public long getWarnAlertCount() {
		return warnAlertCount;
	}

	public void setWarnAlertCount(long warnAlertCount) {
		this.warnAlertCount = warnAlertCount;
	}

	public long getErrorAlertCount() {
		return errorAlertCount;
	}

	public void setErrorAlertCount(long errorAlertCount) {
		this.errorAlertCount = errorAlertCount;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
