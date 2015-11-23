# myJDBCFramework
This is a easy JDBC Framework(based mysql).It is Built by gradle.

## environment
IntelliJ IDEA 14.1.4 + jdk1.8_11 + mysql-5.1.8 + gradle 2.2


## QuickStart

1.put the database.properties in the /src  
2.init ConnectionFactoryBuilder  
3.get ConnectionFactory  
4.get Connections  
5.begin transaction  
...

ps:if you want to get list<entity> by query, you should build a entity and entitymap(implements Mapping).
