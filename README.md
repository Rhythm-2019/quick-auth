##  Quick-auth

Quick-auth can be used to authorize and protect the web interface, and users need to carry the correct signature to access resources

###  Usage

Installing dependency

```bash
$ git clone git@github.com:Rhythm-2019/quick-auth.git
$ cd qyick-auth
$ mvn install 
```

Adding the following dependencies to the project `pom.xml` file
```xml
<dependency>
    <groupId>org.mdnote</groupId>
    <artifactId>quick-auth</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Making a test

```java

// Get url、appId、timestamp、token in request
String url = "...";
String appId = "...";
String token = "...";
String ts = Long.parseLong("...");

// token is genrated with SHA256(url + appId + appScret + timestamp)
AuthResponse authResponse = new HttpApiAuthenticator(new PropertiesFileCredentialStorage(filepath), new DefaultHttpSignature())
        .auth(new HttpAuthRequest(url, token, ts, "", appId));

assert authResponse.isSuccess();
```

`ApiAuthenticator` is used as the entry, the key source `CredentialStorage` and the signature algorithm `HashSignature` need to be passed during construction, and finally the parameter `AuthRequest` submitted by the user is passed in for verification.


For example, for the HTTP protocol, you can use URL, APPID, APPSECRET, and TIMESTAMP for signature and accountability. TIMESTAMP is used to prevent replay attacks, and the time window is 60s.

###  Using in your project

In order to simplify development, readers can use Spring AOP to implant, the steps are as follows:

1. Configure beans：[QuickAuthConfig.java](example/QuickAuthConfig.java)
1. Create annotations and aspects：[QuickAuth.java](example/QuickAuth.java)、[QuickAuthAspect.java](example/QuickAuthAspect.java)
1. Adding anotation in your web interface
    ```java
    @Controller
    public class DemoController {
        @QuickAuth
        @GetMapping("hello")
        public String hello() {
            return "hello world";
        }
    }
    ```
1. Testing with curl
    ```bash
   curl \
     -H "X-CA-Timestamp: 1657690693" \
     -H "App-Id: hello" \
     -H "Token: c8244a2611ac55f9be48698959195d0bff6e815eca745ae01685a3c837b22f5d" \ 
     http://localhost:8080/hello 
   ```
###  TODO List

1. Strictly ensure that the interface is not replayed according to the request-id



