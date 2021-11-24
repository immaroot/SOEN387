# SOEN387

To run the application:
 - install all the ```maven``` dependencies
 - compile ```ca.concordia.poll.core``` to a ```.jar```
 - need to add the following to the ```WEB-INF/lib``` directory for deployement:
    - ```clover-4.4.1.jar```
    - ```core.jar```
    - ```jakarta.servlet.jsp.jstl-2.0.0.jar```
    - ```jakarta.servlet.jsp.jstl-api-2.0.0.jar```
    - ```postgresql-42.3.1.jar```
 - need an instance of a postgresSQL server. You can have simply by going in the root directory with ```docker-compose -f stack.yml up``` this will give you an instance with ```POSTGRES_PASSWORD: secret```,```POSTGRES_USER: postgres``` running on port ```5432```

Team:

| Name          | Sudent ID|
|---------------|----------|
| Killian Kelly | 40014508 |
| Jainil Jaha   |          |
