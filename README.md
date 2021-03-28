# whalerouter
Android(Kotlin,Androidx) 支持动态Route的路由库
# 基础功能
## Gradle 添加依赖配置
```
buildscript {
    repositories {
        google()
        ...
        maven {
            url "https://oss.sonatype.org/content/repositories/snapshots/"
        }
   }
}
```   

```
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [ROUTER_MODULE_NAME: "app", DEPENDENCY_ROUTER_NAMES: "base"]
            }
        }
    }
}

dependencies {
    api 'com.github.qingyunchinese:router-annotation:${router_version}'
    api ('com.github.qingyunchinese:router-api:${router_version}'){
        exclude group: 'androidx.appcompat'
    }
    kapt 'com.github.qingyunchinese:router-processor:${router_version}'
    ...
}
```  
## 代码使用
### 生命拦截器
```kotlin
 object AppRouter: RouterInterceptor, RouterAuthenticator, ContextInterceptor{

        fun init(application: Application) {
            WhaleRouter.init(application, BuildConfig.DeepLinkScheme)
            WhaleRouter.addRouteInterceptor(this)
            WhaleRouter.routerAuthenticator(this)
            WhaleRouter.bindContextInterceptor(this)
        }
        override fun intercept(chain: RouterInterceptor.Chain): RouterResponse {
            val routerRequest = chain.request()
            ...
            return chain.proceed(routerRequest)
        }
        override fun authenticate(response: RouterResponse): RouterRequest? {
            return response.request
        }
        override fun intercept(
                context: Context?,
                routerRequest: RouterRequest,
                routerResponse: RouterResponse
        ): Context? {
            return currentActivity
        }
     }
```
### 初始化
```kotlin
 class XApplication:Application{
    override fun onCreate() {
        super.onCreate()
        ...
        AppRouter.init(this)
        ...
    }
 }
 
```
### 页面添加Router配置
```kotlin
 @Router(path = [Router.LOGIN_ACTIVITY],description = "登录页",requiredAuthor = true)
     class LoginActivity:Activity{} 或者
     class LoginFragment:Fragment{}
```
### 打开页面
```
WhaleRouter.build(AppRouterConfig.LOGIN_ACTIVITY)
                    .withString("title", "登录页").navigate()
```
