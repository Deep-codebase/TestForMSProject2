package com.restapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restapi.entity.Employee;
import com.restapi.exception.UserNotFoundException;
import com.restapi.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmpRestController {

	private EmployeeService employeeService;

	public EmpRestController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}

	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {

		Employee theEmployee = employeeService.findById(employeeId);

		if (theEmployee == null) {
//			throw new NullPointerException();
//			throw new RuntimeException();
			throw new UserNotFoundException("Employee not found with ID:  " + employeeId);
		}

		return theEmployee;
	}

	/*
	 * @PostMapping("/employees") public Employee addEmployee(@RequestBody Employee
	 * theEmployee) {
	 * 
	 * // also just in case they pass an id in JSON ... set id to 0 // this is to
	 * force a save of new item ... instead of update
	 * 
	 * theEmployee.setId(0);
	 * 
	 * employeeService.save(theEmployee);
	 * 
	 * return theEmployee; }
	 */

	@PostMapping("/employees")
//	@PostMapping(value= "/employees",consumes = {MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity addEmployeeWithResponse(@RequestBody Employee theEmployee) {

		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update

		theEmployee.setId(0);

		employeeService.save(theEmployee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(theEmployee.getId()).toUri();
		System.out.println("location: " + location);
		
//		return ResponseEntity.status(HttpStatus.CREATED).body(theEmployee);
		return ResponseEntity.created(location).build();
	}
}
