/*
 *
 *  * Copyright 2017 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package org.springframework.security.web.server.util.matcher;

import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class AndServerWebExchangeMatcher implements ServerWebExchangeMatcher {
	private final List<ServerWebExchangeMatcher> matchers;

	public AndServerWebExchangeMatcher(List<ServerWebExchangeMatcher> matchers) {
		Assert.notEmpty(matchers, "matchers cannot be empty");
		this.matchers = matchers;
	}

	public AndServerWebExchangeMatcher(ServerWebExchangeMatcher... matchers) {
		this(Arrays.asList(matchers));
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher#matches(org.springframework.web.server.ServerWebExchange)
	 */
	@Override
	public MatchResult matches(ServerWebExchange exchange) {
		Map<String, Object> variables = new HashMap<>();
		return matchers.stream()
			.map(m -> m.matches(exchange))
			.peek( m -> variables.putAll(m.getVariables()))
			.allMatch(m -> m.isMatch()) ? MatchResult.match(variables) : MatchResult.notMatch();
	}

	@Override
	public String toString() {
		return "AndServerWebExchangeMatcher{" +
				"matchers=" + matchers +
				'}';
	}
}
