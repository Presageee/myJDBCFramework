# myJDBCFramework
This is a easy orm Framework.It is Built by gradle.(only support mysql)  

## Environment   
need jdk1.5+ gradle 2.2+  


## QuickStart

1.put the database.properties in the /src  
2.PropertiesLoad.config(properties name)  
3.init and get ConnectionFactoryBuilder by ConnectionFactoryBoss  
4.get ConnectionFactory  
5.get Connections  
6.begin transaction  
...   


**orm exmple**:  
//update      
connections.update(entity);   



**if you want to use sql,you should createQuery or createUpdate by connections.**        
ps:if you want to get list<entity> by query, you should build a entity and entitymap(implements Mapping).    
**exmple**   
//query by params  
List<entity> list = (List<entity>)connections.createQuery(sql, (Object[])params).query(mapping);   


**Entity support Annotation**    
@TableName   
@PrimaryKey    
@Column    
@NotColumn    
@AutoColumn    

## *.Properties
**base**:  
driver, url, username, password  
__optional__:  
isUsePool( (false || null) ? __direct connection(default)__ : __use conntionPool__ )  
poolSize(connectionPool.Size)  
loginTime(connectionPool.loginTime) 
