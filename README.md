# myJDBCFramework
This is a easy orm Framework.It is Built by gradle.(only support mysql)  

## Environment   
need jdk1.5+ 

## Update

Added caching

## QuickStart

1.put the database.properties in the /src  
2.PropertiesLoad.config(properties name)  
3.init ConnectionFactoryBuilder by ConnectionFactoryBoss  
4.get ConnectionFactory by ConnectionFactoryBuilder    
5.get Connections  
6.begin transaction  
...   


**orm exmple**:  
//update      
connections.update(entity);   


**if you want to use sql,you should createQuery or createUpdate by connections.**        
 
**exmple**   
//query by params  
List<entity> list = (List<entity>)connections.createQuery().queryByClass(entity.class);   


**Entity support Annotation**    
@TableName   
@PrimaryKey    
@Column    
@NotColumn    
@AutoColumn   

**Method support annotation**  
@AutoTransactionalControl(it is dependency for cglib)(This is annotation of method,if some method of class have  annotation , the class should have a method that name is getConnections)

## *.Properties
**base**:  
driver, url, username, password  
__optional__:  
isUsePool( (false || null) ? __direct connection(default)__ : __use conntionPool__ )  
poolSize(connectionPool.Size)  
loginTime(connectionPool.loginTime) 
