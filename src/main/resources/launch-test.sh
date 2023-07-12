if [ "$1" != "" -a "$2" != "" ];
then
	echo "Execute Test suite: $1 and Test case: $2"
	java -jar TestElementManager-1.0.0.jar -test-case "$1" "$2"
	exit
fi

if [ "$1" == "" ];
then
	echo "Execute of ALL Test Suites"
	java -jar TestElementManager-1.0.0.jar -test-suite ALL
    exit
fi

if [ "$1" != "" -a "${1:0:2}" = TS ];then
    echo "Execute Test suite: $1"
    java -jar TestElementManager-1.0.0.jar -test-suite "$1"
    exit
fi
