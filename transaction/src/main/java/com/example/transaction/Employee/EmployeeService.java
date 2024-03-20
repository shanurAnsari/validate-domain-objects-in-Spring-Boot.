package com.example.transaction.Employee;

import com.example.transaction.Employee.Exception.EmployeeAlreadyExistsException;
import com.example.transaction.Employee.Exception.EmployeeNotFoundException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired EmployeeRepository employeeRepository;

    @Autowired AddressService addressService;

    @Autowired
    private Validator validator;

    @Transactional
    public Employee addEmployee(Employee employee) throws Exception{

        Employee emp = employeeRepository.findById(employee.getId()).orElse(null);
        if(emp != null){
            throw new EmployeeAlreadyExistsException("Employee with id " + emp.getId() +" already exists.");
        }

        /*Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Employee> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage()).append(", ");
            }

            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }*/

        Employee newEmployee =  this.employeeRepository.save(employee);

        Address address = new Address();
        address.setId(123);
        address.setAddress("Gokul Road");
        address.setEmployee(newEmployee);
        this.addressService.addAddress(address);
        return newEmployee;
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found in the Records"));
    }
}
