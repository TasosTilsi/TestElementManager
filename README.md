# Testing Package for ToolsQA website

**[Visit The Website that we will test](https://demoqa.com/books)**

## Prerequisites

To start run this tests or develop more scripts, you must have the following
* Fork, Clone, or Download this project
* Java 11 setted in your system
* Maven setted in your system
* Open a terminal/command prompt/git bash/etc where the pom.xml file is located and run
```bash
mvn clean install
```
* CD into the target directory that will be generated
* Give some execution rights to the launch-test.sh file
```bash
chmod u+x ./launch-test.sh
```
* Run the tests
```bash
#run all of them
./launch-test.sh

#run one whole suite
./launch-test.sh TS_UI_LOGIN

#run only one specific test case
./launch-test.sh TS_UI_LOGIN TC_UI_LOGIN_001
```

If you want to run the test with the latest drivers download them from the below links provided and move the files in the propriate directories inside the `/main/resources/drivers` directory.
* [Download the mozilla geckodriver](https://github.com/mozilla/geckodriver/)
* [Download the google chrome driver](https://sites.google.com/a/chromium.org/chromedriver/)

If you want to run the test in a portable browser instance download them from the below links provided and move the files in the propriate directories inside the `/main/resources/binaries` directory.
* [Download the google chrome portable browser](https://portableapps.com/apps/internet/google_chrome_portable)
* [Download the mozilla firefox portable browser](https://portableapps.com/apps/internet/firefox_portable)

## Usage

### Basic Usage

You have to be careful about the versions of webdriver you download and the portable or your installed browser to match. Otherwise you will some errors and the tests will not run. 

### Development
In order to develop more scripts to your convience just import the project to one of your favourite IDE and keep coding.
