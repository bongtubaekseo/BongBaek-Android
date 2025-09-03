# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Application rules
-keepclassmembers @kotlinx.serialization.Serializable class com.bongtu.baekseo.** {
    # lookup for plugin generated serializable classes
    *** Companion;
    # lookup for serializable objects
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# lookup for plugin generated serializable classes
-if @kotlinx.serialization.Serializable class com.bongtu.baekseo.**
-keepclassmembers class <1>$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}

# Serialization supports named companions but for such classes it is necessary to add an additional rule.
# This rule keeps serializer and serializable class from obfuscation. Therefore, it is recommended not to use wildcards in it, but to write rules for each such class.
# -keep class com.bongtu.baekseo.SerializableClassWithNamedCompanion$$serializer {
#     *** INSTANCE;
# }

-keep class com.bongtu.baekseo.** {
    @kotlinx.serialization.SerialName <fields>;
}

## Coroutines
-keep class kotlinx.coroutines.android.AndroidDispatcherFactory
-keep class kotlinx.coroutines.android.AndroidExceptionPreHandler

## Timber
-keep class timber.log.Timber$Tree { *; }
-keep class timber.log.Timber$DebugTree { *; }

# Kakao SDK
-keep class com.kakao.** { *; }
-keep interface com.kakao.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
-dontwarn com.kakao.sdk.**
