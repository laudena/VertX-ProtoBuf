# Vert.x-ProtoBuf
Testing protocol buffers with VertX [JAVA]

__Vert.x__ is a tool-kit for building reactive applications on the JVM.
__Protocol Buffers__ are a language-neutral, platform-neutral extensible mechanism for serializing structured data.

This sample deploys two verticles that transfer a simple object between them. The data is encoded using protocol buffers, and it is sent using the vert.x event-bus.

___HelloRequest___ is used to define a simple object with a single property: name

___HelloWorldProto___ is auto-generated by protoc.exe 4.3.0, from the proto file _Simple.proto_

___SpeakerVerticle___ generates a HelloRequest object and convert it into message using protocol buffers generated _HelloWorldProto.HelloRequest.Build_

___ListenerVerticle___ receives the message through vert.x's event-bus and convert is instantiate an object based on the message received.


Target folder holds executables compiled for Java8 on Win7. For Windows, batch files are available: _build-verticles.bat_ and _run-verticles.bat_


UPDATE: 
In addition to sending the "news-feed" channel, a more complex message is sent at the "comm-feed" channel of the event-bus.
PersonProto defines ___CommunicationMessage___ class that it's object may hold either Person or Location message reports. 
At the recepient side, _ListenerVerticle_ verticle, the communication-message is parsed to determine the inner message within it. ProtoBuff construct _HasPersonReport()_ and _HasLocationReport()_ properties for this purpose...
