
# VivialConnect Client for Java

VivialConnect is a simple SMS/MMS API. It's designed specifically for developers seeking a simple, affordable and scalable messaging solution.

Get your API key here: https://www.vivialconnect.net/register <br />
Be sure to read the API documentation: https://docs.vivialconnect.net


### Requirements

* [JDK 7 or latest](http://www.oracle.com/technetwork/java/javase/downloads/index.html)  

### Maven Installation

Include the following dependency into your pom.xml:
```xml
<dependency>
  <groupId>vivialconnect</groupId>
  <artifactId>vivialconnect-java</artifactId>
  <version>0.1.6</version>
</dependency>
```
### Manual Installation

You can clone the VivialConnect Java client repository into your project:
```
git clone https://github.com/VivialConnect/vivialconnect-java
```

### Examples

__Initialize Client:__ Needed before attempting to use any resource.

```java
VivialConnectClient.init(123456, my-api-key, my-api-secret);
```

__(optional) Set Proxy:__ If you need to setup a proxy:

```java
Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9000));
VivialConnectClient.setProxy(proxy);
```

__Search for and buying a number:__ 

```java
List<AvailableNumber> availableNumbers = Number.findAvailableNumbersByAreaCode("302");
AvailableNumber availableNumber = availableNumbers.get(0);
AssociatedNumber associatedNumber = availableNumber.buy();
```
__Send a text message:__ 

```java
Message message = new Message();
message.setFromNumber("+19132597591");
message.setToNumber("+11234567890");
message.setBody("Hello, from Vivial Connect!");
message.send(); 
```
__Retrieve a list of all messages sent:__ 

```java
Message.getMessages();
```
__Get a specific message by ID:__ 

```java
Message message = Message.getMessageById(86962);
```

### Query Parameters

qParams are managed by this library using a `Map<String, String>`. Every resource that supports query parameters will have an overload method that takes a Map.

__Retrieve a list of the 5 most recent messages:__
```java
Map<String, String> queryParams = new HashMap<String, String>();
queryParams.put("order", "sent desc");
queryParams.put("limit", "5");
List<Message> messages = Message.getMessages(queryParams);
```

__Retrieve a 2 available numbers from US area code 302:__
```java
Map<String, String> queryParams = new HashMap<String, String>();
queryParams.put("limit", "2");
List<AvailableNumber> availableNumbers = Number.findAvailableNumbersByAreaCode("302", queryParams);
```
