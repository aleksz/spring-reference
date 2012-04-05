Tomcat setup for Estonian ID card
=================================

Here are some tips on how to quickly and lazily make ID card work with Tomcat, for development and testing purposes. The tips here are not mature enough for production systems, not even close.

SSL connectivity
----------------

We need a server certificate for SSL connectivity. Create a self-signed server certificate to Tomcat (to file `TOMCAT_HOME/conf/keystore.jks`).

```bash
cd TOMCAT_HOME
keytool -genkeypair -alias tomcat -keyalg RSA -keystore conf/keystore.jks
Enter keystore password:   // set password to "changeit"
Re-enter new password:     // set password to "changeit"
What is your first and last name?
    [Unknown]:  Your Name
What is the name of your organizational unit?
    [Unknown]:  
What is the name of your organization?
    [Unknown]:  Your Org
What is the name of your City or Locality?
    [Unknown]:  Tallinn
What is the name of your State or Province?
    [Unknown]:  Harjumaa
What is the two-letter country code for this unit?
    [Unknown]:  EE
Is CN=Your Name, OU=Unknown, O=Your Org, L=Tallinn, ST=Harjumaa, C=EE correct?
    [no]:  yes

Enter key password for <tomcat>
        (RETURN if same as keystore password):   // set password to "changeit"
Re-enter new password:                           // set password to "changeit"
```

Client certificate validation
-----------------------------

Web server takes care of validation of the client (i.e ID-card) certificate. Validation of ID-card certificates is done using SK root certificates. Import SK root certificates (<http://sk.ee/en/repository/certs/>) to Tomcat (to file `TOMCAT_HOME/conf/truststore.jks`).

There are more root certificates in SK site, feel free to add them all to the trust store file in the same manner.

(This code is from <http://code.google.com/p/esteid/wiki/AuthConfApache?wl=en> site.)

```bash
cd /etc/pki/esteid/ca
wget -O "JUUR-SK.crt" http://www.sk.ee/files/JUUR-SK.PEM.cer
wget -O "ESTEID-SK.crt" http://www.sk.ee/upload/files/ESTEID-SK.PEM.cer
wget -O "ESTEID-SK 2007.crt" http://www.sk.ee/files/ESTEID-SK%202007.PEM.cer
wget -O "ESTEID-SK 2011.crt" http://www.sk.ee/upload/files/ESTEID-SK%202011.pem.cer
for f in *.crt; do ln -sf "$f" `openssl x509 -hash -noout -in "$f"`.0; done

cd TOMCAT_HOME
keytool -import -alias root -keystore conf/truststore.jks -trustcacerts -file /etc/pki/esteid/ca/JUUR-SK.crt 
keytool -import -alias ESTEID-SK -keystore conf/truststore.jks -trustcacerts -file /etc/pki/esteid/ca/ESTEID-SK.crt 
keytool -import -alias ESTEID-SK-2007 -keystore conf/truststore.jks -trustcacerts -file /etc/pki/esteid/ca/ESTEID-SK\ 2007.crt 
keytool -import -alias ESTEID-SK-2011 -keystore conf/truststore.jks -trustcacerts -file /etc/pki/esteid/ca/ESTEID-SK\ 2011.crt 
```

Tomcat configuration
--------------------

Configure Tomcat to use server certificate and client certificate validation (in file `TOMCAT_HOME/conf/server.xml`)

```xml
<Connector port="8443" protocol="HTTP/1.1" SSLEnabled="true"
           maxThreads="150" scheme="https" secure="true"
           clientAuth="want" sslProtocol="TLS"
           connectionLinger="-1"
           keystoreFile="/opt/tomcat/current/conf/keystore.jks"
           keyAlias="tomcat" keyPass="changeit" keystorePass="changeit"
           truststoreFile="/opt/tomcat/current/conf/truststore.jks"
           truststorePass="changeit" />
```

Try it out
----------

Create a small proof-of-concept webapp, within folder `TOMCAT_HOME/webapps`.

(This code is from <http://virgo47.wordpress.com/2010/08/23/tomcat-web-application-with-ssl-client-certificates/> site.)

```bash
cd TOMCAT_HOME/webapps
mkdir -p certreader/WEB-INF

cat > certreader/WEB-INF/web.xml <<EOF
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
version="2.5">
  <display-name>Cert test</display-name>
</web-app>
EOF
  
cat > certreader/index.jsp <<EOF
<%@page import="java.security.cert.X509Certificate"%>
<%
Object o = request.getAttribute("javax.servlet.request.X509Certificate");
if (o != null) {
  X509Certificate certs[] = (X509Certificate[]) o;
  X509Certificate cert = certs[0];
%>
<%= cert.getSubjectDN().getName() %>
<%
} else {
%>
  Object was null.
<%
}
%>
```

Now, assuming there is ID-card plugin (either [this](https://installer.id.ee/) or [this](http://code.google.com/p/esteid/)) installed to the browser and your ID-card is in the card reader, open the browser and navigate to <https://localhost:8443/certreader>. Your PIN1 should be asked and certificate details displayed on the web page.