package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var schema = args[0];
		var tableName = args[1];
		System.out.println("Schema : "+ schema + " - Table : "+ tableName);
		jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
			var metaData = connection.getMetaData();
			try (var rs = metaData.getColumns(null, schema, tableName, null)) {
				while (rs.next()) {
					System.out.println(rs.getString("COLUMN_NAME").toUpperCase() + rs.getInt("DATA_TYPE"));
				}
			}
			return null;
		});
	}
}
