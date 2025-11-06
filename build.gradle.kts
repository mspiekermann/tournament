plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.51.0.0")
}

application {
    mainClass = "Main"
}