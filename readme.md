Practices used in the project

General
1. Implementation of logs
2. Hide credentials of application.properties

DTO Implementation
1. Use Lombok to Reduce Boilerplate Code
2. Apply Bean Validation Annotations
3. Implement Serializable
4. Follow Naming Conventions
5. Include Audit Fields createdAt, updatedAt, deletedAt
6. Use of Builder (DESSING PATTERN)

Controller Implementation

1. Use Consistent API Versioning (/v1.0/countries)
2. Follow RESTful Naming Conventions (/countries)
3. Use Proper HTTP Status Codes
4. Implement Pagination & Filtering
6. Enable CORS Properly
7. Validate Inputs with @Valid

Exception handler
1. Global exception handler
2. Custom exceptions

Service Implementation
1. Separation of bussiness logic
2. Transaction Management
3. Error Handling
4. Interface driven development

Converter Implementation
1. Uses converter pattern (CountryConverter) for clean separation
2. Encript to hide autoincremet ids
3. Use enviroment variables

Entity Implementation
1. Lombok Optimization
2. Soft Delete Pattern
3. Use pattern Builder (DESSING PATTERN)
4. JPA

Repository Implementation
1. Repository Interface Design
2. Query derivation from method names findByIdAndDeletedAtIsNull
3. Soft Delete Support
4. JPA and SPECIFICACION to obtain data of databse