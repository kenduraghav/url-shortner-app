package com.example.demo.domain.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.entities.User;
import com.example.demo.domain.models.Role;


@Repository
public class UsersRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersRepository.class);
	
	private final JdbcClient jdbcClient;
	
	public UsersRepository(JdbcClient jdbcClient) {
		this.jdbcClient = jdbcClient;
	}
	
	public Optional<User> findByEmail(String email){
		String sql="SELECT id,name,email,password,role,created_at FROM users where email = :email";
		return jdbcClient
				.sql(sql)
				.param("email", email)
				.query(new UserRowMapper())
				.optional();
	}
	
	public boolean existsByEmail(String email) {
		String sql="select count(email) > 0 from users where email = :email";
		return jdbcClient
				.sql(sql)
				.param("email", email)
				.query(Boolean.class)
				.single();
	}

	public Optional<User> findById(Long id) {
		String sql="SELECT id,name,email,password,role,created_at FROM users where id = :id";
		return jdbcClient
				.sql(sql)
				.param("id", id)
				.query(new UserRowMapper())
				.optional();
	}
	
	
	public void save(User user) {
		String sql = """
				INSERT into users(email, password, name,role,created_at) 
				values (:email,:password, :name, :role, :createdAt)
				RETURNING id
				""";	
		var keyHolder = new GeneratedKeyHolder();
		jdbcClient.sql(sql)
			.param("email", user.getEmail())
			.param("password", user.getPassword())
			.param("name", user.getName())
			.param("role", user.getRole().name())
			.param("createdAt", LocalDateTime.now())
			.update(keyHolder);
		logger.info("User id : {}", keyHolder.getKeyAs(Long.class));
	}
	
	static class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			var user = new User();
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setRole(Role.valueOf(rs.getString("role")));
			user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			return user;
		}
		
	}

	

}
