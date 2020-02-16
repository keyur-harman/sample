Step by Step procedure to run Spring Security:

1. In application.properties add your postgres db details.

2. In postgres add tables for roles,permission and role_permission_map as per https://parallel6.atlassian.net/wiki/spaces/PROV/pages/790757527/Data+Model+View .
 eg:
 
----->create table user_roles( username varchar(60),role varchar(60), Foreign Key (username) REFERENCES users (username), FOREIGN KEY (role) REFERENCES role (name));
--->create table role(id varchar Primary Key, name varchar not null, UNIQUE  (name) )

3. Add another two tables for authentication as users and user_roles. Users will have username and associated password. and user_roles has the username and the role for that user.

4. In this project , sql file rolesandpermission.sql has been added. You can restore this file in your local pgadmin4 locale.

