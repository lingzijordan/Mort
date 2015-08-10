#customer-data

##Get Start

1. Download code:  
```
git clone https://zhangyi2015@bitbucket.org/BigEyeData/customer-data.git
```  
2. Install Mysql:  
```
brew install mysql
```  
3. Start Mysql:  
```
mysql.server start
```  

##Structures

* common: Share codes for all modules including db feature and common model
* db-domain-service: expose customer-data http service for RDB
* db-data-engine: Handle customer data for RDB
* data-writer: Write the data frame in to bigeye storage as analysis data
* project: Sbt build scripts

