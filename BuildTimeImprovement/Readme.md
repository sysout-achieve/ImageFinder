# 빌드 시간 개선

<hr>

기본설정으로 빌드했을 때<br>
![https://github.com/sysout-achieve/ImageFinder/blob/master/BuildTimeImprovement/before%EB%B9%8C%EB%93%9C%EC%86%8D%EB%8F%84%EA%B0%9C%EC%84%A0.png](https://github.com/sysout-achieve/ImageFinder/blob/master/BuildTimeImprovement/before%EB%B9%8C%EB%93%9C%EC%86%8D%EB%8F%84%EA%B0%9C%EC%84%A0.png)
<br>
<br>
<br>
## 적용 팁
<br>

gradle.properties에 적용

```
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=1024m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.configureondemand=true
org.gradle.caching=true
android.enableBuildScriptClasspathCheck=false
```

gradle(app)에 적용
```
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
```

```
// 3. 레거시 multidex 사용 자제
// 4. 개발 빌드에서 패키징 리소스 최소화
productFlavors{
        dev {
            minSdkVersion 21                //...3
            resConfigs('en','xhdpi')        //...4

        }
        //...
}
```
<br>

개선사항 적용했을 때<br>
![https://github.com/sysout-achieve/ImageFinder/blob/master/BuildTimeImprovement/after%EB%B9%8C%EB%93%9C%EC%86%8D%EB%8F%84%EA%B0%9C%EC%84%A0.png](https://github.com/sysout-achieve/ImageFinder/blob/master/BuildTimeImprovement/after%EB%B9%8C%EB%93%9C%EC%86%8D%EB%8F%84%EA%B0%9C%EC%84%A0.png)

<br>
작은 프로젝트에서도 16초에서 1초로 빌드 시간을 크게 단축시키는 것을 확인할 수 있었다.<br>
프로젝트의 단위가 커질수록 빌드시간이 생산성을 방해하는 요소로 작용할 수 있다. 개발단위에서 필요하지 않은 빌드파일을 생성하지 않고, 버전별 대응에 관한 것을 선택적으로 적용하면 빌드시간을 줄이고 효율적인 작업을 할 수 있다.

<hr>

참고자료 : [라인 기술블로그](https://engineering.linecorp.com/ko/blog/how-to-make-android-app-build-faster/)
