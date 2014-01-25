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

import org.sonar.plugins.publisher.PublisherMetrics;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.resources.Java;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.Scopes;

public class PublisherDecorator implements Decorator {

  public boolean shouldExecuteOnProject(Project project) {
    // Execute only on Java projects
    // Modify the condition if the Decorator should be executed in other projects
    return StringUtils.equals(project.getLanguageKey(), Java.KEY);
  }

  public void decorate(Resource resource, DecoratorContext context) {
    // This method is executed on the whole tree of resources.
    // Bottom-up navigation : Java methods -> Java classes -> files -> packages -> modules -> project
    if (Scopes.isBlockUnit(resource)) {
      // Sonar API includes many libraries like commons-lang and google-collections
      double value = RandomUtils.nextDouble();

      // Add a measure to the current Java method
      context.saveMeasure(PublisherMetrics.RANDOM, value);

    } else {
      // we sum random values on resources different than method
      context.saveMeasure(PublisherMetrics.RANDOM, MeasureUtils.sum(true, context.getChildrenMeasures(PublisherMetrics.RANDOM)));
    }
  }
}
