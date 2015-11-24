# myJDBCFramework
This is a easy JDBC Framework(based mysql).It is Built by gradle.

## Environment
IntelliJ IDEA 14.1.4 + jdk1.8_11 + mysql-5.1.8 + gradle 2.2


## QuickStart

1.put the database.properties in the /src  
2.PropertiesLoad.config(properties name)
3.init and get ConnectionFactoryBuilder by ConnectionFactoryBoss  
4.get ConnectionFactory  
5.get Connections  
6.begin transaction  
...

ps:if you want to get list<entity> by query, you should build a entity and entitymap(implements Mapping).

## *.Properties
**base**:  
driver, url, username, password  
__optional__:  
isUsePool( (false || null) ? __direct connection(default)__ : __use conntionPool__ )  
poolSize(connectionPool.Size)  
loginTime(connectionPool.loginTime) 
