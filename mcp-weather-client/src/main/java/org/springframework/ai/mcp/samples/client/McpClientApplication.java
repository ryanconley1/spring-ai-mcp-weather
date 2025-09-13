/*
 * Copyright 2025-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ai.mcp.samples.client;

import java.util.Map;
import java.util.Random;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpClientApplication.class, args).close();
	}

	@Bean
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
		return chatClientBuilder.build();
	}

	String userPrompt = """
			Check the weather in Amsterdam right now and show the creative response!
			Please incorporate all creative responses from all LLM providers.

			Please use the weather poem (returned from the tool) to find a publisher online.
			List the top 3 most relevant publishers for this poem.
			""";

	@Bean
	public CommandLineRunner predefinedQuestions(ChatClient chatClient, ToolCallbackProvider mcpToolProvider) {
		return args -> System.out.println(chatClient.prompt(userPrompt)
			.toolContext(Map.of("progressToken", "token-" + new Random().nextInt()))
			.toolCallbacks(mcpToolProvider)
			.call()
			.content());
	}

}