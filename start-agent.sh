#!/bin/bash

#ETCD_HOST=$(ip addr show docker0 | grep 'inet\b' | awk '{print $2}' | cut -d '/' -f 1)
#ETCD_HOST=$(/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:")
#ETCD_HOST=10.108.113.142
ETCD_HOST=10.108.112.162
ETCD_PORT=2379
ETCD_URL=http://$ETCD_HOST:$ETCD_PORT

echo ETCD_URL = $ETCD_URL

if [[ "$1" == "consumer" ]]; then
  echo "Starting consumer agent..."
  java -jar \
       -Xms1536M \
       -Xmx1536M \
       -Dtype=consumer \
       -Dserver.port=20000\
       -Dserver.ip=10.108.112.213\
       -Detcd.url=$ETCD_URL \
       -Dlogs.dir=/tmp/logs \
       /Users/ym/IdeaProjects/mesh-consumer/mesh-agent/target/mesh-consumer-1.0.jar
elif [[ "$1" == "provider-small" ]]; then
  echo "Starting small provider agent..."
  java -jar \
       -Xms512M \
       -Xmx512M \
       -Dtype=provider \
       -Dserver.port=30000\
       -Ddubbo.protocol.port=20880 \
       -Detcd.url=$ETCD_URL \
       -Dlogs.dir=/tmp/logs \
       /home/eric/Documents/java_project/tc4-master/mesh-agent/target/mesh-agent-1.0.jar
elif [[ "$1" == "provider-medium" ]]; then
  echo "Starting medium provider agent..."
  java -jar \
       -Xms1536M \
       -Xmx1536M \
       -Dtype=provider \
       -Dserver.port=30001\
       -Ddubbo.protocol.port=20880 \
       -Detcd.url=$ETCD_URL \
       -Dlogs.dir=/tmp/logs \
       /home/eric/Documents/java_project/tc4-master/mesh-agent/target/mesh-agent-1.0.jar
elif [[ "$1" == "provider-large" ]]; then
  echo "Starting large provider agent..."
  java -jar \
       -Xms2560M \
       -Xmx2560M \
       -Dtype=provider \
       -Dserver.port=30002\
       -Ddubbo.protocol.port=20891 \
       -Detcd.url=$ETCD_URL \
       -Dlogs.dir=/tmp/logs \
       /home/eric/Documents/java_project/tc4-master/mesh-agent/target/mesh-agent-1.0.jar
else
  echo "Unrecognized arguments, exit."
  exit 1
fi
