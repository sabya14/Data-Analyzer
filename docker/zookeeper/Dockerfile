FROM zookeeper:3.3.6
COPY seed.sh /seed.sh
ENV kafka_server "192.168.65.0:9092,192.168.65.0:9093"
ENV zk_command "zkCli.sh -server zookeeper:2181"
CMD ["/seed.sh"]
