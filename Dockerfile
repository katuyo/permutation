#Application Building Container  Compling Time
FROM featx/playframework:sbt-1.2.8 as stage0
COPY . /usr/local/permutation
WORKDIR /usr/local/permutation
RUN set -ex && \
 apk add -U unzip && \
 sbt dist && \
 cd target/universal/ && \
 unzip permutation-*.zip && \
 rm permutation-*.zip && \
 mv permutation-* permutation

#Application Running Container   Runtime
FROM openjdk:8u212-jre-alpine
LABEL vendor=Featx
MAINTAINER Excepts <excepts@aliyun.com>
COPY --from=stage0 /usr/local/permutation/target/universal/permutation /usr/local/permutation
RUN apk add -U bash && \
        addgroup -g 1000 featx && \
        adduser -G featx -u 1000 -s /sbin/nologin -D -H featx && \
        chown -R featx:featx /usr/local/permutation

WORKDIR /usr/local/permutation
EXPOSE 9000 9443
USER featx
ENTRYPOINT ["/usr/local/permutation/bin/permutation"]
CMD []
