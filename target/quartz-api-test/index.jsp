<html>
<body>
	<h2>Datasource Test Application</h2>
	<form action="test" method="post">
		JNDI DataSource: 
		<input type="text" name="jndiURL" value="jdbc/CTS_BDD_MF"><br>		
        Timeout:
        <input type="text" name="cronExpression" value="0/15 * * * * ? *"><br>
        <br>
		<input type="submit" name="runButton" value="Run job">
        <input type="submit" name="deleteButton" value="Delete job">
    </form>
</body>
</html>
