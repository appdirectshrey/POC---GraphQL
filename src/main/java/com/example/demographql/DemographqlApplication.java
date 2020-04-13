package com.example.demographql;

import com.coxautodev.graphql.tools.SchemaParser;
import com.example.demographql.exception.GraphQLErrorAdapter;
import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.repository.AuthorRepository;
import com.example.demographql.repository.BookRepository;
import com.example.demographql.resolver.BookResolver;
import com.example.demographql.resolver.Mutation;
import com.example.demographql.resolver.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.ExecutionStrategy;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLErrorHandler;
import graphql.servlet.GraphQLServlet;
import graphql.servlet.SimpleGraphQLServlet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemographqlApplication {

	private final AuthorRepository authorRepository;

	private final BookRepository bookRepository;

	@Autowired
	public DemographqlApplication(AuthorRepository authorRepository, BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(DemographqlApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {

		GraphQLSchema schema = SchemaParser.newParser()
				.resolvers(authorResolver(authorRepository), mutation(authorRepository, bookRepository),
						query(authorRepository, bookRepository))
				.file("graphql/author.graphqls")
				.file("graphql/book.graphqls")
				.build().makeExecutableSchema();
		ExecutionStrategy executionStrategy = new AsyncExecutionStrategy();
		GraphQLServlet servlet = new SimpleGraphQLServlet(schema, executionStrategy);
		ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/graphql");

		return bean;
	}

	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}

	@Bean
	public BookResolver authorResolver(AuthorRepository authorRepository) {
		return new BookResolver(authorRepository);
	}

	@Bean
	public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Query(authorRepository, bookRepository);
	}

	@Bean
	public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Mutation(authorRepository, bookRepository);
	}

}
