# Architectural Decisions

- Assumption: The artist of the day feature will be frequently accessed and updated, requiring a responsive
  and efficient system.
    - Decision: Implementing caching for the artist of the day feature
        - Justification: Caching the artist of the day data will reduce database load and improve response times for
          users, ensuring a smooth and responsive experience even during peak access times.

- Decision: Generic User table with an artist flag
    - Justification: This allows for a single user table to be used for both artists and regular users,
      simplifying the database schema and reducing redundancy.

- Decision: Using cursor pagination for list APIs
    - Justification: Cursor pagination is more efficient for large datasets as it avoids the performance issues
      associated with offset-based pagination, ensuring faster response times and better scalability.

- Decision: Using Cursor based approach for artist of the day
    - Justification: Cursor-based pagination allows for efficient retrieval of the next set of artists without the need
      to calculate offsets, which can be slow and resource-intensive for large datasets. This approach ensures that
      users can seamlessly navigate through the list of artists without performance degradation.

- Decision: Using Hexagonal Architecture for the application
    - Justification: Hexagonal architecture promotes separation of concerns, making the system more maintainable,
      testable, and adaptable to changes. It allows for clear boundaries between the core business logic and external
      dependencies, facilitating easier integration with different frameworks and technologies.

- Decision: Using IT and Unit Tests:
    - Justification: Implementing both integration tests (IT) and unit tests ensures that individual components of the
      application work correctly in isolation (unit tests) and that the core business logic functions together
      as expected (integration tests). i.e. Artist of the day, core requirements, and other critical features will be
      thoroughly tested to validate their behavior and interactions.
      This comprehensive testing strategy helps identify issues early, improves code quality, and increases confidence
      in the system's reliability and stability.

- Decision: Using Relation Database for the application:
    - Justification: The artist, tracks, and artist alias entities have a clear relational structure, making a
      relational database a suitable choice. It allows for efficient querying, data integrity, and support for complex
      relationships between entities. Additionally, relational databases provide robust transaction management and
      indexing capabilities, which are essential for maintaining data consistency and performance in the application.

- Decision: Using Rest Assured, and Test Containers for integration testing:
    - Justification: Rest Assured is a powerful library for testing RESTful APIs, allowing for easy validation of
      endpoints and responses. Test Containers provide a lightweight and isolated environment for running integration
      tests, ensuring that the application behaves correctly in a real-world scenario. This combination enables
      comprehensive testing of the application's API endpoints and their interactions with external dependencies,
      enhancing overall test coverage and reliability.

- Decision: Using UUIDs as public keys:
    - Justification: UUIDs provide a unique identifier for each entity as well, and using UUIDs as public keys enhances
      security and scalability, as they can be generated independently. We should not use primary database keys as
      public keys, as they can expose sensitive information.

- Decision: Containerization of the application using Docker:
    - Justification: Containerization with Docker allows for consistent deployment across different environments,

- Decision: Using Open API Specification (OAS) for API documentation:
    - Justification: Provided for reviewers to understand the API endpoints, request/response formats, and
      available operations.