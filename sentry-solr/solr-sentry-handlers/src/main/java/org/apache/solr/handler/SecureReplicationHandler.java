/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.solr.handler;

import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

/**
 * Secure (sentry-aware) version of ReplicationHandler
 */
public class SecureReplicationHandler extends ReplicationHandler {
  @Override
  public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp) throws Exception {
    String collection = null;
    if (req.getCore() == null) {
      // if the request doesn't have a core, let's use the core stored on the
      // request handler
      collection = core.getCoreDescriptor().getCloudDescriptor().getCollectionName();
    }
    SecureRequestHandlerUtil.checkSentryAdmin(req, SecureRequestHandlerUtil.QUERY_AND_UPDATE, true, collection);
    super.handleRequestBody(req, rsp);
  }
}
