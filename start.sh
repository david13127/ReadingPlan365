CUR_PATH=$(cd "$(dirname "$0")"; pwd)
echo "Current path: ${CUR_PATH}"
mvn -Dmaven.test.skip=true package
java -jar ${CUR_PATH}/target/ReadingPlan365-0.0.1-SNAPSHOT.jar > ${CUR_PATH}/target/log.file 2>&1 &
echo $! > ${CUR_PATH}/target/ReadingPlan365.pid 