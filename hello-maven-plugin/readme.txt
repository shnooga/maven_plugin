# To fire off the plugin
mvn com.vsp.enterprise.test:ruletest:create 
# Required
-DinputFile=./myrule.drl 
# Optional
-DoverwriteExistJavaTest=true 
-DjavaTestDir=. 
-DresourceDir=. 
-DtemplateFile=./src/main/resources/ruletesttemplate.txt


# To clean off the files
mvn com.vsp.enterprise.test:ruletest:clean
