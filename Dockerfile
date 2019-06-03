#Application Building Container  Compling Time
FROM featx/sbt:1.2.8-play-docker as stage0
COPY . /usr/local
WORKDIR /usr/local
RUN sbt docker:publishLocal
RUN ["chmod", "-R", "u=rX,g=rX", "/usr/local"]
RUN ["chmod", "u+x,g+x", "/usr/local/target/docker/stage/opt/docker/bin/permutation"]
#Application Running Container   Runtime
FROM openjdk:8u212-jre-alpine
LABEL vendor=Featx
MAINTAINER Excepts <excepts@aliyun.com>

RUN apk add -U bash && \
        addgroup -g 1000 featx && \
        adduser -G featx -u 1000 -s /sbin/nologin -D -H featx && \
        chown -R featx:featx /usr/local/permutation
WORKDIR /usr/local/permutation
COPY --from=stage0 --chown=featx:featx /usr/local/target/docker/stage/opt/docker /usr/local/permutation
EXPOSE 9000 9443
USER featx
ENTRYPOINT ["/usr/local/permutation/bin/permutation"]
CMD []