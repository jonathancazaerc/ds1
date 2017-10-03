# ds1 - Repository JonathansEdits

## TCP or UDP
Toggle the comments on line 28 and 29 of DS1.java

## TCP
Inserted bufferedreader to send all kind of files. Feel free to try with berlin.jpg:
```bash
java -jar ds1.jar serve udp #for the server
java -jar ds1.jar pull berlin.jpg #for the client
```

### Principle?

- See animation: http://dsearls.org/courses/C113Java/JavaClassFiles/BufferedReader/Images/Animation1.gif
- In while loop of the handler.java: 
```java 
while ((count = in.read(buffer)) > 0) { 
    //  in.read(buffer)=0 (no bytes are read)
    //  in.read(buffer)=-1 (end of file)
    //  https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html
```

## UDP
- UDPServer.java: http://www.java2s.com/Code/Java/Network-Protocol/ReceiveUDPpockets.htm
- UDPClient.java: http://www.heimetli.ch/udp/UDPClient.html
- Scripts are lightly edited from the links above

## ToDo
- Server reply on UDP-request (use of handler.java?)
- Multithreading UDP
- Check with exercise
