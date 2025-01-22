// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    id("com.android.library") version "8.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}