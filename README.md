# JSFLibraryTracker

JSFLibraryTracker is a simple library database management software, to manage a library catalog, register library members and to track the status of the books. 

## Technologies

- Java 1.8
- Java Server Faces 2.2
- Hibernate 4.3
- MySQL Community Server version 5.7
- MySQL Connector/J 8.0
- MySQLWorkbench 6.3
- Cascading Style Sheets
- Apache Tomcat 8.0
- NetBeans 8.2

## Prerequisites

- Java Runtime Environment (JRE) 8
- MySQL Community Server version 5.7
- MySQL Connector/J 8.0
- Optional: MySQLWorkbench 6.3

### Create MySQL database
- **Download .sql files from https://github.com/gittaszabo/Swinglibrarytrackerdb.git**
	```LibraryTrackerDatabase/librarytracker.sql``` -> empty database 
	```LibraryTrackerDatabase/librarytrackersample.sql``` -> sample database

- **Create a database** 
	Note, that these .sql files will create databases with the names 'librarytracker' and 'librarytrackersample', respectively.
	(Existing databases with the same name will be deleted when running the .sql scripts.)

	Create a database **using the command line**:
	```mysql -u root -p <librarytracker.sql``` will create an empty database
	```mysql -u root -p <librarytrackersample.sql``` will create a database with sample data

	...**or in MySQL Workbench**
	select  ```File > Run SQL script > Run```

### Set database connection data
Go to '/src/java' in the project folder. Set your database connection data in the hibernate.cfg.xml file.

#### Deploy
Deploy and run the project from the IDE or with the Tomcat Web Application Manager using the '/dist/JSFLibraryTracker.war' file.

## Built With
Netbeans 8.2

## Author
Gitta Szabo - https://github.com/gittaszabo
