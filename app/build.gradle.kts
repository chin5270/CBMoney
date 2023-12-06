plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.cbmoney"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.cbmoney"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding=true

    }



}

dependencies {


    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



    // cardview
    implementation("androidx.cardview:cardview:1.0.0")

    // Room components
    implementation ("androidx.room:room-runtime:2.4.0")
    annotationProcessor( "androidx.room:room-compiler:2.4.0")
    androidTestImplementation ("androidx.room:room-testing:2.4.0")

    // Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-common-java8:2.4.0")

    // pieChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")


    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

}

extra["appCompatVersion"] = "1.5.1"
extra["constraintLayoutVersion"] = "2.1.4"
extra["coreTestingVersion"] = "2.1.0"
extra["lifecycleVersion"] = "2.3.1"
extra["materialVersion"] = "1.3.0"
extra["roomVersion"] = "2.3.0"
// 測試相關
extra["junitVersion"] = "4.13.2"
extra["espressoVersion"] = "3.4.0"
extra["androidxJunitVersion"] = "1.1.2"
