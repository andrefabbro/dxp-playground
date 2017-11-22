import java.io.PrintStream
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream
import com.liferay.portal.kernel.dao.jdbc.DataAccess

query = "select userid, emailaddress from User_"
max_rows = 100

con = DataAccess.getConnection()

stmt = con.createStatement()

stmt.setMaxRows(max_rows)
rs = stmt.executeQuery(query)

md = rs.getMetaData()
cc = md.getColumnCount()

header = "#"
(1..cc).each {
	value = md.getColumnLabel(it) 
    header = header + "," + value
}

println header

while (rs.next()) {
    line = rs.getRow()
    (1..cc).each {
		value = rs.getObject(it)
		line = line + "," + value
	}
	println line
}

DataAccess.cleanUp(con, stmt, rs)
