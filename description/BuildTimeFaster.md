

gradle.properties에 아래 코드 추가

'''
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=1024m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.configureondemand=true
org.gradle.caching=true
android.enableBuildScriptClasspathCheck=false
'''



gradle(app)에 아래 코드 추가
'''
//  1.개발 시 여러 개의 APK 생성 설정 비활성화
//  2.PNG 크런칭 비활성화
buildTypes {
    debug {
        splits.abi.enable = false           //...1
        splits.density.enable = false       //...1
        aaptOptions.cruncherEnabled = false //...2
    }
    //..
}
'''

'''
// 1. 레거시 multidex 사용 자제
// 2. 개발 빌드에서 패키징 리소스 최소화
productFlavors{
        dev {
            minSdkVersion 21                //...1
            resConfigs('en','xhdpi')        //...2

        }
        //...
}
'''
