package snippet;

public class Snippet {
	<?xml version="1.0" encoding="UTF-8"?>  
	<Server port="8005" shutdown="SHUTDOWN">  
	  <Listener SSLEngine="on" className="org.apache.catalina.core.AprLifecycleListener"/>  
	  <Listener className="org.apache.catalina.core.JasperListener"/>  
	  <Listener className="org.apache.catalina.core.JreMemoryLeakPreventionListener"/>  
	  <Listener className="org.apache.catalina.mbeans.GlobalResourcesLifecycleListener"/>  
	  <Listener className="org.apache.catalina.core.ThreadLocalLeakPreventionListener"/>  
	  <GlobalNamingResources>  
	    <Resource auth="Container" description="User database that can be updated and saved" factory="org.apache.catalina.users.MemoryUserDatabaseFactory" name="UserDatabase" pathname="conf/tomcat-users.xml" type="org.apache.catalina.UserDatabase"/>  
	  </GlobalNamingResources>  
	  <Service name="Catalina">  
	    <Connector connectionTimeout="20000" port="80" protocol="HTTP/1.1" redirectPort="8443"/>  
	    <Connector port="8009" protocol="AJP/1.3" redirectPort="8443"/>  
	  
	    <Engine defaultHost="localhost" name="Catalina">  
	      <Realm className="org.apache.catalina.realm.LockOutRealm">  
	        <Realm className="org.apache.catalina.realm.UserDatabaseRealm" resourceName="UserDatabase"/>  
	      </Realm>  
	  
	      <Host appBase="webapps" autoDeploy="true" name="localhost" unpackWARs="true">  
	             <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs" pattern="%h %l %u %t &quot;%r&quot; %s %b" prefix="localhost_access_log." suffix=".txt"/>  
	        <!-- 增加虚拟目录配置-->  
	             <Context docBase="d:/home/baseos/upload/" path="/upload" reloadable="true"/>  
	      </Host>  
	    </Engine>  
	  </Service>  
	</Server>  
}

