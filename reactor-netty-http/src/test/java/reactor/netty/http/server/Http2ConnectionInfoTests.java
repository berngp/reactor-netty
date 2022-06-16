/*
 * Copyright (c) 2020-2022 VMware, Inc. or its affiliates, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reactor.netty.http.server;

import io.netty5.handler.ssl.SslContext;
import io.netty5.handler.ssl.SslContextBuilder;
import io.netty5.handler.ssl.util.InsecureTrustManagerFactory;
import org.junit.jupiter.api.Disabled;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

/**
 * Tests for HTTP/2.0 and {@link ConnectionInfo}
 *
 * @author Violeta Georgieva
 * @since 1.0.0
 */
@Disabled
class Http2ConnectionInfoTests extends ConnectionInfoTests {
	@Override
	protected HttpClient customizeClientOptions(HttpClient httpClient) {
		try {
			SslContext ctx = SslContextBuilder.forClient()
			                                  .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
			return httpClient.protocol(HttpProtocol.H2)
			                 .secure(ssl -> ssl.sslContext(ctx));
		}
		catch (SSLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected HttpServer customizeServerOptions(HttpServer httpServer) {
		try {
			SslContext ctx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
			return httpServer.protocol(HttpProtocol.H2)
			                 .secure(ssl -> ssl.sslContext(ctx));
		}
		catch (SSLException e) {
			throw new RuntimeException(e);
		}
	}
}
