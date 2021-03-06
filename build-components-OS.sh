mvn -f books/pom.xml clean package && \
mvn -f authors/pom.xml clean package && \
mvn -f web-sockets/pom.xml clean package && \
mvn -f frontend/pom.xml clean package && \
docker build -t supra89kren/bff-books-service:1.0 -f books/Dockerfile books && \
docker build -t supra89kren/bff-authors-service:1.0 -f authors/Dockerfile authors && \
docker build -t supra89kren/bff-web-sockets-service:1.0 -f web-sockets/Dockerfile web-sockets && \
docker build -t supra89kren/bff-frontend:1.0 -f frontend/Dockerfile frontend
