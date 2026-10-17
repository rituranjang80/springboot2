package com.example.employeeapi;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Employee> mapper = (rs, rowNum) ->
        new Employee(rs.getLong("id"), rs.getString("name"),
                     rs.getString("email"), rs.getString("department"));

    public EmployeeRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public List<Employee> findAll() {
        return jdbc.query("SELECT id, name, email, department FROM employees ORDER BY id", mapper);
    }

    public Optional<Employee> findById(long id) {
        return jdbc.query("SELECT id, name, email, department FROM employees WHERE id = ?", mapper, id)
                   .stream().findFirst();
    }

    public Employee create(EmployeeRequest request) {
        jdbc.update("INSERT INTO employees(name, email, department) VALUES (?, ?, ?)",
                    request.name(), request.email(), request.department());
        Long id = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
        return findById(id).orElseThrow();
    }

    public Optional<Employee> update(long id, EmployeeRequest request) {
        int changed = jdbc.update("UPDATE employees SET name=?, email=?, department=? WHERE id=?",
                request.name(), request.email(), request.department(), id);
        return changed == 0 ? Optional.empty() : findById(id);
    }

    public boolean delete(long id) {
        return jdbc.update("DELETE FROM employees WHERE id=?", id) > 0;
    }
}
