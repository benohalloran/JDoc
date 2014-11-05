JDoc
=========

An interactive documentation system.

JDoc parses JAR files to extract inheritance and dependencies between different packages and classes. Instead of traditional Java Documentation tools, which simply spit back HTML pages with some links and some occasional styling, JDoc provides a way for new students to see real world examples of inheritance and be able to automatically trace the origin of functions and where the funtion is actually implemented.

JDoc displays both the methods and fields defined within a class source file and methods defined in it's heirarchy. Since methods come from a variety of classes in real-world systems, individual method's have their own 'heirarchy', tracing back where in the hierarchy it was declared, and where it was implemented.
