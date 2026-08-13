---
title: Joanna Norris ePortfolio
---

## Joanna Norris

Welcome to my Computer Science Capstone ePortfolio.

#### Professional Self-Assessment

Coming soon...

#### Artifacts

- **Software Design & Engineering**

Artifact 1: Software Design and Engineering CS 360 Mobile Inventory Application.

The artifact I selected for the software design and engineering category is my Android Inventory Application, originally developed in CS-360: Mobile Architecture and Programming. The application was designed to help users manage inventory by allowing them to add, view, update, and delete inventory items stored in a local SQLite database. The original project also included a user authentication system, although portions of the login functionality were incomplete. For my capstone project, I improved the application's overall software design by completing the login logic, adding password encryption, adding a functional logout feature, and fully implementing the Create, Read, Update, and Delete (CRUD) operations so users could successfully manage inventory records through the application's interface. 

I selected this artifact because it demonstrates several core software engineering principles, including user interface design, object-oriented programming, event-driven programming, and database implementation, all within an Android application. The enhancements showcase my ability to evaluate existing software, identify missing functionality, and implement improvements. Completing the authentication logic and CRUD functionality transformed the application from a partially completed assignment to a functional inventory management application. 

This enhancement successfully met the course outcomes I identified in Module One by demonstrating my ability to analyze existing software, apply software engineering best practices, and improve the quality of an application through thoughtful design modifications. Throughout the enhancement process of all artifacts, I gained a greater appreciation for creating applications that are modular, maintainable, and scalable rather than simply functional. One of the greatest challenges was reconnecting with a project that I had not worked with for quite some time and understanding once again how everything worked together before making any changes. Working through these challenges strengthened my flexibility as a programmer, debugging skills, problem-solving, and software design skills.

- **Algorithms & Data Structures**

Artifact 2: CS330 Computational Graphics and Visualization Nightstand Scene

This artifact is a 3D graphics application originally created for my CS330 Computational Graphics and Visualization course. This project features C++, OpenGL, and GLSL shaders to render a realistic 3D nightstand scene composed of multiple textured and lit objects. For my capstone, I selected this artifact because it provided an opportunity to demonstrate not only graphics programming skills but also the application of data structures and algorithms to improve the organization, maintainability, and scalability of a complex software project. 

I chose this artifact for my ePortfolio because it showcases my ability to organize large amounts of rendering logic into a more structured and maintainable design. The original implementation contained all rendering logic in one large RenderScene() function which spanned hundreds of lines. As the scene grew in complexity, this approach became difficult to maintain and extend. To improve the artifact, I refactored the rendering process by separating each major object in the scene into its own rendering function, including the floor, dresser, books, alarm clock, and lamp. I also introduced a std::vector to manage scene components allowing the rendering process to iterate through the collection rather than relying on hard-coded function calls. This enhancement demonstrates the effective use of the C++ Standard Library, data structures, and traversal algorithms to improve code organization while maintaining the original functionality of the application. 

This enhancement met the course outcomes I identified in Module One. Specifically, it demonstrates my ability to design and evaluate appropriate data structures to improve software efficiency, maintainability, and scalability. By replacing a monolithic rendering method with a modular, collection-based approach, I improved the overall architecture without sacrificing readability or performance. At this time, I do not anticipate making any changes to my planned course outcome coverage, as this enhancement effectively represents my skills in algorithms and data structures. 

Enhancing this artifact reinforced the importance of choosing data structures that improve software organization without introducing unnecessary complexity. One of the most valuable improvements was replacing hard-coded rendering calls with a vector of objects that stores and iterates through the scene's render functions. This change made the rendering pipeline more scalable by allowing additional scene components to be added to the collection without modifying the overall rendering algorithm. I also reorganized the project by separating each major scene object into its own source file, making the code significantly easier to read, maintain, and debug. One challenge I faced was ensuring all pathways were connected correctly, and the refactored code continued to render the scene exactly as before. Completing this enhancement strengthened my understanding of how appropriate data structures, iteration, and modular design can improve software maintainability while keeping the implementation efficient and easy to expand upon. 

- **Databases**

Artifact 3: Software Design and Engineering CS 360 Mobile Inventory Application.

   The artifact selected for the database enhancement is the same one that was enhanced in software design and engineering: my Android inventory management application. The application was created in Java and XML using Android Studio. It provides users with the ability to add, edit, view, and delete inventory items. During my capstone enhancement, I expanded the application's database functionality and improved the way inventory information is organized and accessed.
   
  The completed enhancement expanded the original database to include separate tables for inventory items, categories, suppliers, and transactions. Inventory items can now be associated with a category and supplier, while supplier records also contain contact information. Additionally, I added functionality to create categories and suppliers, filter inventory by category, and view additional information, such as an item's description, on a separate page rather than displaying all information in the full inventory view.
  
  I selected this application because it demonstrates my ability to take an existing application and improve its functionality, rather than simply creating a new application from the beginning. The database enhancement primarily focuses on my ability to work with SQLite, CRUD operations, foreign-key relationships, SQL queries, and the integration of database information with an Android user interface.
  
  The original database focused mainly on storing inventory items. I improved it by separating categories and suppliers into their own tables and connecting them to inventory items through foreign keys. SQL joins allow the application to retrieve related category and supplier information along with each inventory item, displaying all relevant information in one location. To improve user experience, I added category and supplier dropdowns, the ability to add new categories and suppliers, category filtering, and an "All Categories" filter option. The inventory screen now supports viewing and editing items separately, while maintaining all other existing CRUD operations.
  
  I met the primary course outcomes that I planned to address through this enhancement, although I did not complete every enhancement I had originally planned. As I worked through this project, I adjusted my priorities based on the functionality that would provide the greatest improvement to the application. The completed database enhancement shows stronger evidence of my software development skills than the original plan because it demonstrates database design, CRUD operations, relational data, SQL queries, input validation, and integration between multiple components of an Android application. My outcome-coverage plan is therefore focused on the completed database functionality and the development skills demonstrated by the final artifact rather than the enhancement features that I ultimately did not implement.
  
  The enhancement process taught me how interconnected the different parts of an application are. A change to the database often required corresponding changes to the database helper, adapter, activity, and user interface. One of the biggest challenges was troubleshooting SQLite and cursor errors. I encountered several crashes caused by column names and SQL queries that did not return the columns expected by the adapter. These errors helped me better understand how SQL queries, cursors, and Android's SimpleCursorAdapter work together. I also learned the importance of always double-checking variable names if unsure. Overall, this enhancement gave me practical experience modifying an existing software project while maintaining its original functionality. The completed database enhancement demonstrates my growth in working with relational databases and integrating database functionality into an Android application.
