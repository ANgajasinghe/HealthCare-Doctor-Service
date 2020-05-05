# PAF-Doctor_Service
HelthCare is a hospital management system where the registered users can make appointments with the registered doctors who visit the registered hospitals. The users can even make the payments for the appointments online. 
<p></p>
<h4> -- project architecture -- </h4>
<ul>
	<li>#UTILIRY :-</li>
	<br>
	<small><ol>
			<li>Server(doctors) class diagram</li>
			<li>database backup(s)</li>
			<li>configuration Zip file :- contains database connection builder xml file.</li>
			</ol>
	</small>
	<br>
	<li>Client :- </li>
	<br>
	<small><ol>
			<li>lib folder :- includes all client-side libraries (<i>jQuery-3.5.0,bootstrap,fontawesome</i>) </li>
			<li>app.css :- main css.</li>
			<li>app.js :- include all DC according to MVC architecture.</li>
			<li>index.html :- from and grid view</li>
			</ol>
	</small>
	<br>
	<li>doctors :- </li>
	<br>
	<small><ol>
			<li> include all server side implementation  </li>
			<li> MYSQL connection builder using connection.xml </li>
			</ol>
	</small>
</ul>	

<p></p>
<h4> -- Project configuration -- </h4>
<ol type="1">
	<li> Create a database. </li>
	<li> Export latest .sql backup to created database.(important)</li>
	<li> <b>Extract IT18153682.zip into the C: drive(important).</b> </li>
	<li> <b>Open and edit Connection.xml file according to your database (important).</b> </li>
	<li> Run doctors service using apache server  </li>
	<li> If you are not running on http://localhost/ Please change APIs URL from _URL() (<i>top of the app.js file</i>) function.</li>
</ol>  

