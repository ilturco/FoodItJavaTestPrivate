/*
 * This file is a component of thundr, a software library from 3wks.
 * Read more: http://www.3wks.com.au/thundr
 * Copyright (C) 2013 3wks, <thundr@3wks.com.au>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.foodit.test.sample.controller;

import com.google.appengine.tools.development.testing.*;
import org.junit.rules.ExternalResource;

import java.util.TimeZone;

public class SetupAppengine extends ExternalResource {
	private LocalServiceTestHelper helper;

	@Override
	protected void before() throws Throwable {
		LocalDatastoreServiceTestConfig hrdDatastore = new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(0.50f);
		LocalTaskQueueTestConfig queueConfig = new LocalTaskQueueTestConfig();
		queueConfig.setQueueXmlPath("src/main/webapp/WEB-INF/queue.xml");
		LocalMemcacheServiceTestConfig memcacheConfig = new LocalMemcacheServiceTestConfig();
		LocalSearchServiceTestConfig searchConfig = new LocalSearchServiceTestConfig();
		helper = new LocalServiceTestHelper(queueConfig, hrdDatastore, memcacheConfig, searchConfig);
		helper.setTimeZone(TimeZone.getDefault());
		helper.setUp();
	}

	@Override
	protected void after() {
		helper.tearDown();
	}
}
