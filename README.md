mvn clean package -DskipTests

java --add-exports java.base/sun.nio.ch=ALL-UNNAMED -jar target/spark-etl-pipeline-1.0-SNAPSHOT.jar src/main/resources/data/sample-employees.csv