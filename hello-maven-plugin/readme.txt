// Building the project
mvn clean install

// Generate UnitTest skeleton code
mvn test -DinputFile=./uno/dos/somerule.drl

// Generate recursively faux rules files
mvn com.vsp.enterprise.test:ruletest:rules_create -DinputDir=c:\trash

// Run all unit tests in this project
mvn test

// Testing 1 class via Maven
mvn -Dtest=CLM00409Test test

// Testing all classes named *Test via Maven
mvn test -Dtest=*Test


###############################################################################
# Using the UnitTestCreate goal. This reads an existing rule file (ie CLM123.drl) then
#    1. Create a doppelganger rule file for testing, CLM123Faux.drl
#    2. Create a skeleton unit testing file, CLM123Test.java
#     
###############################################################################
mvn com.vsp.enterprise.test:ruletest:unit_test_create
-DinputFile=./myrule.drl 
# Optional (defaults are below)
-DoverwriteExistJavaTest=false 
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
