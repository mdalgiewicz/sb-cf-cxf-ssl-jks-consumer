# sb-cf-cxf-ws-consumer
Spring Boot Cxf Contract First Soap Consumer


Generowanie keystore dla klienta usługi
keytool -genkeypair -alias consumerkey -keyalg RSA -dname "CN=localhost,OU=Example Org Unit,O=Dalgim,L=Warsaw,S=Warsaw,C=PL" -keypass P@ssw0rd -keystore consumer-keystore.jks -storepass P@SSWORD

Wyeksportowanie certyfikatu klienta
keytool -exportcert -alias consumerkey -file consumer-public.cer -keystore consumer-keystore.jks -storepass P@SSWORD

Utworzenie truststore dla klienta usługi, dodanie certyfikatu serwera
keytool -import -file producer-public.cer -alias producer -keystore consumer-truststore.jks



_Understanding the Common Name 
 Before you can enroll for a SSL Server Certificate, you must generate a CSR from your webserver software. During the creation of the CSR, the following fields must be entered: Organization (O), Organizational Unit (OU), Country (C), State (S), Locality (L), and Common Name (CN). The Common Name field is often misunderstood and is filled out incorrectly.
 
 The Common Name is typically composed of Host + Domain Name and will look like "www.yoursite.com" or "yoursite.com". SSL Server Certificates are specific to the Common Name that they have been issued to at the Host level. The Common Name must be the same as the Web address you will be accessing when connecting to a secure site. For example, a SSL Server Certificate for the domain "domain.com" will receive a warning if accessing a site named "www.domain.com" or "secure.domain.com", as "www.domain.com" and "secure.domain.com" are different from "domain.com". You would need to create a CSR for the correct Common Name. When the Certificate will be used on an Intranet (or internal network), the Common Name may be one word, and it can also be the name of the server._
 
 