###############################################################################
# To create new rule test
# NOTE:
#	In Windows OS, forward slashes are also acceptable. ie "c:/mydir/somerule.drl"
###############################################################################
mvn com.vsp.enterprise.test:ruletest:create 
-DinputFile=./myrule.drl 
# Optional (defaults are below)
-DoverwriteExistJavaTest=true 
-DjavaTestDir=./src/test/java
-DresourceDir=./target
-DtemplateFile=./src/main/resources/ruletesttemplate.txt


###############################################################################
# To clean off the files
###############################################################################
mvn com.vsp.enterprise.test:ruletest:clean 
-DinputFile=./myrule.drl 
# Optional (defaults are below)
-DresourceDir=./src/main/java/resources 
