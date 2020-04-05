### STEPS TO RUN THE KAKFA ENV
    Start a kafka with two brokers which would read periodically from a URL and write to a topic named test.
    * Setup docker.host.internal in /etc/host using your host ip.
    * cd /docker/kafka
    * docker-compose down && docker-compose build && docker-compose up --force-recreate


