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


-keep interface android.support.v7.** { *; }
-keep class android.support.v7.** { *; }

-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-assumenosideeffects class android.util.Log {
    public static *** e(...);
    public static *** w(...);
    public static *** wtf(...);
    public static *** d(...);
    public static *** v(...);
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment




-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**
-dontwarn com.google.android.gms.internal.zzhu





# For Parse
-dontwarn com.facebook.**
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.**
-dontwarn okio.**

-keepattributes SourceFile, LineNumberTable
-keepattributes LocalVariableTable, LocalVariableTypeTable

-keepattributes *Annotation*, Signature, Exception

-keepclassmembers class com.codepath.models** { <fields>; }

-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }

#startapp rule



-keep class com.truenet.** {
      *;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface


-dontwarn org.jetbrains.annotations.**

-keep class cz.msebera.android.httpclient.HttpResponse { *; }
-keep class com.loopj.android.http.HttpGet { *; }
-keep class com.loopj.android.http.HttpDelete { *; }

-ignorewarnings
