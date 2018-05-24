# Library system
Final project for Java_External course

**(variant: 16)**

The system is represented as a catalog in which you can search by:
- The author (one of the group).
- Book title or title fragment.
- One of the key words of the book (the attribute of the book).

Administrator fiils in books catalog, can add, delete and change information about books.
Each book has an address(a place on the shelf) or a reader.
To take the book reader has to register by leaving an e-mail and phone number.
The book can be borrowed from Administrator in the library for a period of one month, only if the it is available in the library.
The administrator has a page where taken books and users who read them are reflected.

### Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Instructions for launching the application

1) Download and run tomcat via ./bin/startup.sh
2) Build project via 'mvn clean && mvn package'
3) Copy ROOT.war from target folder to  ./{$TOMCAT_HOME}/webpapps folder 

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Olha Revzon** 
