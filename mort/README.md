#Mort
##Get Start

1. Download code:  
```
git clone git@bitbucket.org:BigEyeData/mort.git
```  
2. Install Mysql:  
```
brew install mysql
```  
3. Start Mysql:  
```
mysql.server start
```  
4. Use go.sh shell script when you development

##Structures

* commons: Share codes for all modules
* domain-service: expose mort http service
* data-set: Include codes to handle dataSet
* data-source: Include code to handle dataSource
* db-data-set: Include code to handle database dataSet
* db-data-source: Include code to handle database dataSource
* infrastructure: Include code to handle infrastructure(ie, spark, metadata db)
* metadata-db-script: Use flyWay to migrate database. The SQL should place in the src/main/resource/db/migration
* project: Sbt build scripts
* scripts: Shell script for development and test

