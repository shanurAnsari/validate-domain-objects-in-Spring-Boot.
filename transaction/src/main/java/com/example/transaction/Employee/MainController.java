package com.example.transaction.Employee;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class MainController {

    @Autowired private EmployeeService employeeService;
    @Autowired private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> add(@Valid @RequestBody EmployeeDTO employeeDto) throws Exception {
        Employee newEmp = this.employeeService.addEmployee(modelMapper.map(employeeDto, Employee.class));
        return new ResponseEntity<EmployeeDTO>(modelMapper.map(newEmp, EmployeeDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/Id/{Id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable int Id){
        Employee emp = employeeService.getEmployeeById(Id);

        EmployeeDTO empDto = modelMapper.map(emp, EmployeeDTO.class);
        return ResponseEntity.ok(empDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
