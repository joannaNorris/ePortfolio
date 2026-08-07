---
title: Joanna Norris ePortfolio
---

## Joanna Norris

Welcome to my Computer Science Capstone ePortfolio.

#### Professional Self-Assessment

Coming soon...

#### Artifacts

- **Software Design & Engineering**

Artifact 1: Software Design and Engineering CS 360 Mobile Inventory Application The artifact I selected for the software design and engineering category is my Android Inventory Application, originally developed in CS-360: Mobile Architecture and Programming. The application was designed to help users manage inventory by allowing them to add, view, update, and delete inventory items stored in a local SQLite database. The original project also included a user authentication system, although portions of the login functionality were incomplete. For my capstone project, I improved the application's overall software design by completing the login logic, adding password encryption, adding a functional logout feature, and fully implementing the Create, Read, Update, and Delete (CRUD) operations so users could successfully manage inventory records through the application's interface. 

I selected this artifact because it demonstrates several core software engineering principles, including user interface design, object oriented programming, event-driven programming, and database implementation all within an Android application. The enhancements showcase my ability to evaluate existing software, identify missing functionality, and implement improvements. Completing the authentication logic and CRUD functionality transformed the application from a partially completed assignment to a functional inventory management application. 

This enhancement successfully met the course outcomes I identified in Module One by demonstrating my ability to analyze existing software, apply software engineering best practices, and improve the quality of an application through thoughtful design modifications. Throughout the enhancement process of all artifacts, I gained a greater appreciation for creating applications that are modular, maintainable, and scalable rather than simply functional. One of the greatest challenges was reconnecting with a project that I had not worked with for quite some time and understanding once again how everything worked together before making any changes. Working through these challenges strengthened my flexibility as a programmer, debugging skills, problem-solving, and software design skills.

- ** Algorithms & Data Structures **

This artifact is a 3D graphics application originally created for my CS330 Computational Graphics and Visualization course. This project features C++, OpenGL, and GLSL shaders to render a realistic 3D nightstand scene composed of multiple textured and lit objects. For my capstone, I selected this artifact because it provided an opportunity to demonstrate not only graphics programming skills but also the application of data structures and algorithms to improve the organization, maintainability, and scalability of a complex software project. 

I chose this artifact for my ePortfolio because it showcases my ability to organize large amounts of rendering logic into a more structured and maintainable design. The original implementation contained all rendering logic in one large RenderScene() function which spanned hundreds of lines. As the scene grew in complexity, this approach became difficult to maintain and extend. To improve the artifact, I refactored the rendering process by separating each major object in the scene into its own rendering function, including the floor, dresser, books, alarm clock, and lamp. I also introduced a std::vector to manage scene components allowing the rendering process to iterate through the collection rather than relying on hard-coded function calls. This enhancement demonstrates the effective use of the C++ Standard Library, data structures, and traversal algorithms to improve code organization while maintaining the original functionality of the application. 

This enhancement met the course outcomes I identified in Module One. Specifically, it demonstrates my ability to design and evaluate appropriate data structures to improve software efficiency, maintainability, and scalability. By replacing a monolithic rendering method with a modular, collection-based approach, I improved the overall architecture without sacrificing readability or performance. At this time, I do not anticipate making any changes to my planned course outcome coverage, as this enhancement effectively represents my skills in algorithms and data structures. 

Enhancing this artifact reinforced the importance of choosing data structures that improve software organization without introducing unnecessary complexity. One of the most valuable improvements was replacing hard-coded rendering calls with a vector of objects that stores and iterates through the scene's render functions. This change made the rendering pipeline more scalable by allowing additional scene components to be added to the collection without modifying the overall rendering algorithm. I also reorganized the project by separating each major scene object into its own source file, making the code significantly easier to read, maintain, and debug. One challenge I faced was ensuring all pathways were connected correctly, and the refactored code continued to render the scene exactly as before. Completing this enhancement strengthened my understanding of how appropriate data structures, iteration, and modular design can improve software maintainability while keeping the implementation efficient and easy to expand upon. 

- ** Databases **
