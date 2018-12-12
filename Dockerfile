FROM lamtev/java:latest

MAINTAINER Anton Lamtev <antonlamtev@gmail.com>

COPY . /opt/service

RUN cd /opt/service \
    && gradle bootJar \
    && mv build/libs/*.jar . \
    && find . -not -name '*.jar' -not -name '*.kts' -not -name '.' \
    -print0 | xargs -0  -I {} rm -rf {}
