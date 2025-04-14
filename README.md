# Project Changelog
## Generated from Git Commit History
- Added method security and Role hierarchy (kenduraghav)
- Added Admin Dashboard functionality (kenduraghav)
- Added My URL functionality. Delete Selected URLs functionality (kenduraghav)
- Added Basic pagination with default values (kenduraghav)
- RedirectToOriginal URL if user logged in for private urls (kenduraghav)
- Updated Short URL with isPrivate and Expiration days for logged in user. Inserted data for short_urls (kenduraghav)
- Added Custom Spring Security Configuration, Role based access control and Display of Logged in User info (kenduraghav)
- Added Spring Security Configuration with defaults (kenduraghav)
- Added Short Key URL Redirections and Exception Handling(404- if page not found) (kenduraghav)
- Refactored packages. Added Configuration Properties for app based and  checks the URL is valid or not. (kenduraghav)
- Added Create Short URL form and service to save the Shortened URL generated with validations (kenduraghav)
- Added Entity Mapper using java records. Package names refactored (kenduraghav)
- Fixed lazy loading using second approach with Entity Graphs (kenduraghav)
- Fixed Lazy loading exception using left join fetch in JPQL (@query annotation) (kenduraghav)
- Disabled Open Session in view to throw lazy loading initialization exception (kenduraghav)
- Added columns for CreatedBy and displaying the user details. (kenduraghav)
- Changed derived query to JPQL to have more control (kenduraghav)
- Added findByIsPrivateIsFalse method for listing only public urls (kenduraghav)
- Added Repository and added Public URLs List page (kenduraghav)
- Added Liquibase Migration for the schema and data (kenduraghav)
- Added support for Spring Data JPA and docker compose support for postgres (kenduraghav)
- Changed index page to show the dynamic content (kenduraghav)
- Added Home Page layout decoration (kenduraghav)