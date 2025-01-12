FROM ubuntu:latest
LABEL authors="miglius"

ENTRYPOINT ["top", "-b"]