plugins {
    java
    jacoco
    id("org.springframework.boot") version "2.1.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

jacoco.toolVersion = "0.8.2"

group = "ru.spbstu.icc.kspt"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    compile("com.intellij:annotations:12.0")
    compile("org.springframework.boot:spring-boot-starter-web")

    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

val jacocoReport = tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(jacocoReport)
}
