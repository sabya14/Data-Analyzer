### AIM
Build a data analytics system that can consume records from various sources.
 * Support fast search on the data.
 * Support fast analytics over the data.
 * Auto generate analytics on the data as fast as possible.
 * The system should be scalable.


### STEPS
 * Using Kafka to read from various data sources and stream the data.
 * Then a solr consumer will consume it and create a new collection in solr.
 This collection will be used for fast queries of mainly columns which have primary key. Solr wont store the
 entire data but only column necessary for searching unique records among the data.
 * Besides, this the entire data would be written into a hadoop data store with auto detected schema.
 * Schema is needed to enable Kylin to process this data and make OLAP cubes ready.
 * This would result in supporting fast sub seconds aggregate operations.
 


### STEPS TO RUN THE KAKFA ENV
    Start a kafka with two brokers which would read periodically from a URL and write to a topic named test.
    * Setup docker.host.internal in /etc/host using your host ip.
    * cd /docker/kafka
    * docker-compose down && docker-compose build && docker-compose up --force-recreate


