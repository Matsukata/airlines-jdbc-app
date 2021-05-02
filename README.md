### Implementation of a persistence layer using JDBC API for an Airlines Application.

***

#### Prerequisites:

- System should have installed PostgreSQL

#### Install notes:

1. Create database airlines
    ```
   CREATE USER airlines;
   ALTER USER airlines WITH PASSWORD 'airlines';
   CREATE DATABASE airlines;   
   GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA PUBLIC TO airlines;
   GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA PUBLIC TO airlines;
    ```

2. Execute *resource/schema.sql* script

3. Execute *resource/test_data.sql* script for mock data (optional)