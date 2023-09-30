# Use a Postgres image as base
FROM postgres:latest
#FROM postgres:latest

MAINTAINER ashok @xyloinc.com

#Set environment variables

ENV POSTGRES_DB=trade
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=root

#Install Java 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk
RUN apt-get install -y maven 

RUN apt-get install -y cron	
       
#Clone and build 
RUN apt-get install -y git 

RUN git clone https://SanjayPrasath-Xylo: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@github.com:/XYLO-DEV/xylo-trade-manager.git && \
    cd xylo-trade-manager && mvn -B package --file pom.xml  

ENV SPRING_DATASOURCE_URL: jdbc:postgresql://pgdb:5432/trade
ENV SPRING_DATASOURCE_USERNAME: postgres
ENV SPRING_DATASOURCE_PASSWORD: root
 

ADD backup.sh /etc/cron.d/backup.sh 



RUN chmod 777 /etc/cron.d/backup.sh 

ADD backup_cron backup_cron

RUN chmod 777 /backup_cron


RUN crontab /backup_cron 


#ADD restore.sh restore.sh

#RUN chmod +x restore.sh

EXPOSE 9004

CMD service cron start  && java -jar xylo-trade-manager/target/trademanager-0.0.1-SNAPSHOT.jar



 


