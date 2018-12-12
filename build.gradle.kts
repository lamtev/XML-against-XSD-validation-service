import com.bmuschko.gradle.docker.tasks.image.*
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.util.Collections
import java.nio.file.Files
import java.nio.file.Paths


plugins {
    java
    jacoco
    id("org.springframework.boot") version "2.1.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"

    id("com.bmuschko.docker-remote-api") version "4.1.0"
}

jacoco.toolVersion = "0.8.2"

group = "com.lamtev"
version = "1.0.RELEASE"
val serviceName = "xml-against-xsd-validation-service"

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

tasks.withType<BootJar> {
    baseName = serviceName
    version = project.version as String
    mainClassName = "com.lamtev.xml_against_xsd_validation_service.ServiceLauncher"
}

val buildDockerImage = tasks.create("buildDockerImage", type = DockerBuildImage::class) {
    inputDir.set(file("."))
    tag.set("lamtev/xml-against-xsd-validation-service:latest")
}

tasks.create("pushDockerImage", type = DockerPushImage::class) {
    tag.set("lamtev/xml-against-xsd-validation-service:latest")
}

tasks.create("runService", type = JavaExec::class) {
    main = "-jar"
    val jarName = "$serviceName-$version.jar"
    args = Collections.singletonList(
            when {
                Files.exists(Paths.get(jarName)) -> jarName
                Files.exists(Paths.get("build/libs/$jarName")) -> "build/libs/$jarName"
                else -> ""
            }
    )
}
