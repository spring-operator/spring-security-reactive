/*
 * Copyright 2017 the original author or authors.
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
package org.springframework.security.web.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.security.test.web.reactive.server.ServerWebExchangeBuilders;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class ContentTypeOptionsHttpHeadersWriterTests {

	ContentTypeOptionsHttpHeadersWriter writer = new ContentTypeOptionsHttpHeadersWriter();

	ServerWebExchange exchange = ServerWebExchangeBuilders.toExchange(MockServerHttpRequest.get("/"));

	HttpHeaders headers = exchange.getResponse().getHeaders();

	@Test
	public void writeHeadersThenSuccess() {
		writer.writeHttpHeaders(exchange);

		assertThat(headers.getFirst(ContentTypeOptionsHttpHeadersWriter.X_CONTENT_OPTIONS)).isEqualTo(ContentTypeOptionsHttpHeadersWriter.NOSNIFF);
	}

}
