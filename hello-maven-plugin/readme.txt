###############################################################################
# To create a java unit test for a rule
# NOTE:
#	In Windows OS, forward slashes are also acceptable. ie "c:/mydir/somerule.drl"
###############################################################################
mvn com.vsp.enterprise.test:ruletest:unit_test_create
-DinputFile=./myrule.drl 
# Optional (defaults are below)
-DoverwriteExistJavaTest=true 
-DjavaTestDir=./src/test/java
-DresourceDir=./target/rules
-DtemplateFile=./src/main/resources/UnitTestTemplate.txt

###############################################################################
# To recursively create a directory of faux rules from a given parent directory
# NOTE:
#	In Windows OS, forward slashes are also acceptable. ie "c:/mydir/somerule.drl"
###############################################################################
mvn com.vsp.enterprise.test:ruletest:rules_create
-DinputDir=c:/trash
# Optional (defaults are below)
-DresourceDir=./target/rules

###############################################################################
# To recursively create a directory of Java Unit Tests from a given parent directory
# NOTE:
#	In Windows OS, forward slashes are also acceptable. ie "c:/mydir/somerule.drl"
###############################################################################
mvn com.vsp.enterprise.test:ruletest:unit_test_create
-DinputDir=./some/ruledir
# Optional (defaults are below)
-DjavaTestDir=./src/test/java
