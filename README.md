Ministry standard JT808 808 protocol gateway
  

Project Introduction
Based on Netty, realize the message processing, encoding and decoding of JT808 808 protocol, 1078 protocol, Subiao, Guangdong standard;
No need to modify the code, support TCP, UDP protocol at the same time;
Use Spring WebFlux to provide web interface services that support high concurrency;
Does not depend on Spring, can remove Spring to run independently (encoding and decoding can support Android);
The most concise, refreshing, and easy-to-use framework for developing departmental standards.
Main features
The code is concise enough to facilitate secondary development;
Pay tribute to the design concepts of Spring and Hibernate, and students who are familiar with web development can get started very quickly;
Use annotations to describe protocols, say goodbye to tedious packaging and unpacking;
Support asynchronous batch processing, significantly improve MySQL storage performance;
Provide a message interpreter (analysis tool for parsing process), encoding and decoding are no longer blind;
Full coverage of test cases, stable release.
Protocol support (transport layer protocol supports TCP, UDP)
