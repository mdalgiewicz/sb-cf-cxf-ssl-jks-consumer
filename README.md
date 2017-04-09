**Spring Boot Contract First CXF Soap Consumer**
-Spring Boot
-Cxf, Contract First Soap Consumer
-Two wat SSL based on Java KeyStore
-Lombok

## Keystore i Truststore w jednym .jks
### Utworzenie klucza i certyfikatu serwera
keytool -genkeypair -alias serverkey -keyalg RSA -keypass password -keystore server.jks -storepass password
### Utworzenie klucza i certyfikatu klienta
keytool -genkeypair -alias clientkey -keyalg RSA -keypass password -keystore client.jks -storepass password
### Export certyfikatu serwera do pliku
keytool -exportcert -alias serverkey -file server-public.cer -keystore server.jks -storepass password
### Export certyfikatu klienta do pliku
keytool -exportcert -alias clientkey -file client-public.cer -keystore client.jks -storepass password
## Import certyfikatu klienta do keystore serwera
keytool -importcert -keystore server.jks -alias clientcert -file client-public.cer -storepass password -noprompt
## Import certyfikatu serwera do keysrore klienta
keytool -importcert -keystore client.jks -alias servercert -file server-public.cer -storepass password -noprompt

## Keystore i Trust store oddzielnie
### Utworzenie klucza i certyfikatu serwera
keytool -genkeypair -alias serverkey -keyalg RSA -keypass password -keystore server-keystore.jks -storepass password
### Utworzenie klucza i certyfikatu klienta
keytool -genkeypair -alias clientkey -keyalg RSA -keypass password -keystore client-keystore.jks -storepass password
### Export certyfikatu serwera do pliku
keytool -exportcert -alias serverkey -file server-public.cer -keystore server-keystore.jks -storepass password
### Export certyfikatu klienta do pliku
keytool -exportcert -alias clientkey -file client-public.cer -keystore client-keystore.jks -storepass password
### Import certyfikatu serwera do oddzielnego keystore (truststore dla klienta)
keytool -importcert -keystore client-truststore.jks -alias servercert -file server-public.cer -storepass password -noprompt
### Import certyfikatu klienta do oddzielnego keystore (truststore dla serwera)
keytool -importcert -keystore server-truststore.jks -alias clientcert -file client-public.cer -storepass password -noprompt


## Użycie certyfikatu w formacie PKCS12 - Najpier należy utworzyc certyfikat bazowy z którego wygenerujemy testowy format pkcs12 
## Utworzenie keystore w formacie p12 z istniejacego keystore
keytool -importkeystore -srckeystore client-keystore.jks -srcstoretype JKS -deststoretype PKCS12 -destkeystore client-keystore.p12
## Wyeksportowanie nowego certyfikatu w celu dodania go do trustore po stronie serwera
keytool -exportcert -keystore client-keystore.p12 -storetype PKCS12 -storepass password -alias clientkey -file client-cert.crt