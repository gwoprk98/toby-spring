plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework:spring-context:5.3.22")
    implementation ("org.springframework:spring-jdbc:5.3.22")

    implementation ("mysql:mysql-connector-java:8.0.30")

    implementation ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")

    implementation ("org.slf4j:slf4j-api:1.7.30")
    implementation ("org.slf4j:slf4j-simple:1.7.30")

    testImplementation ("org.springframework:spring-test:5.3.22")
    testImplementation ("org.assertj:assertj-core:3.22.0")
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation ("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
