package com.example.employeeapi;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeRepository repository;
    public EmployeeController(EmployeeRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Employee> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable long id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@Valid @RequestBody EmployeeRequest request) {
        return repository.create(request);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable long id, @Valid @RequestBody EmployeeRequest request) {
        return repository.update(id, request).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable long id) {
        if (!repository.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return Map.of("message", "Employee deleted");
    }
}
