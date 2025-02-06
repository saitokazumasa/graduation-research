# Build tailwindCSS
FROM node:22.13.1 AS build-tailwindcss
WORKDIR /app
COPY ./src/ ./src/
COPY ./package.json ./package-lock.json ./tailwind.config.js ./
RUN npm ci
RUN npx tailwindcss -i src/main/resources/static/css/input.css -o src/main/resources/static/css/tailwind.css

# Build and test jar
FROM maven:3.9.9-eclipse-temurin-22 AS build-and-test-jar
WORKDIR /app
COPY --from=build-tailwindcss /app/src/ ./src/
COPY ./pom.xml ./
CMD ["mvn", "test"]
