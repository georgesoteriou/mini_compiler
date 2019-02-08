all: build

build:
	./gradlew assemble

clean:
	./gradlew clean

test:
	./gradlew test

.PHONY: all build clean test
