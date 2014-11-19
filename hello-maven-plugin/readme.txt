# To create new rule test
mvn com.vsp.enterprise.test:ruletest:create 
-DinputFile=./myrule.drl 
# Optional
-DoverwriteExistJavaTest=true 
-DjavaTestDir=./src/test/java
-DresourceDir=./src/main/resources 
-DtemplateFile=./src/main/resources/ruletesttemplate.txt


# To clean off the files
mvn com.vsp.enterprise.test:ruletest:clean 
-DinputFile=./myrule.drl 
#Optional
-DresourceDir=./src/main/java/resources 

// Creating Maven plugin
mvn archetype:generate -DgroupId=sample.plugin -DartifactId=hello-maven-plugin -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-plugin