# BootStudentManagementSystem

A Spring Boot-based REST API for managing student and department data in an educational institution.

## Features

- **Student Management**
  - Create, read, update, and delete student records
  - Filter students by department, role, and marks
  - Retrieve students by name, marks, or unique ID
  - Exception handling for missing students

- **Department Management**
  - Create, read, update, and delete department records
  - Retrieve departments by name, location, or ID
  - Exception handling for missing departments

- **Caching**
  - Integrated caching for improved performance on student queries

## Technologies Used

- Java
- Spring Boot (RESTful API)
- Hibernate/JPA (for entity management)
- Spring Cache
- JSON for API communication

## API Endpoints

### Students

- `GET /api/student` — Get all students
- `POST /api/student` — Add a new student
- `PUT /api/student/{sid}` — Update student by ID
- `POST /api/student/{sid}` — Delete student by ID
- `GET /api/studentFilter` — Get students by department and marks filter
- `GET /api/onestudent?sid={sid}` — Get a single student by ID
- `GET /api/studDept?name={name}&marks={marks}` — Get student by name and marks
- `GET /api/studentName?name={name}` — Get students by name

### Departments

- `GET /api/dept` — Get all departments
- `POST /api/dept` — Add a new department
- `GET /api/onedept?deptid={deptid}` — Get a single department by ID

### Cache

- `GET /api/cache?cacheName={name}` — Inspect cache content

## Entity Models

### Students

- `sid` (int): Student ID
- `name` (String): Name of the student
- `marks` (String): Student marks
- `dept` (Department): Associated department
- `role` (String): Role of the student

### Department

- `deptid` (int): Department ID
- `deptname` (String): Department name
- `location` (String): Location of the department
- `studentlist` (List<Students>): List of students in the department

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Mayur-007S/BootStudentManagementSystem.git
   ```
2. Build and run using your preferred Java IDE or using Maven/Gradle.

3. The API server will run on `localhost:8080` by default.

## License

_No license file present. Please add a license for open-source compliance._

## Author

- [Mayur-007S](https://github.com/Mayur-007S)

