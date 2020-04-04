#!/bin/sh
echo $zk_command

# Remove already present node
$zk_command rmr /data
$zk_command create /data ''

# Create topic storage path, and provide broker info
$zk_command create /data/raw_data ''
$zk_command create /data/raw_data/kafka_brokers kafka_server
$zk_command create /tw/raw_data/topic raw_data_source_name