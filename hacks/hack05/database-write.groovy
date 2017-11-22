import java.io.PrintStream
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream
import com.liferay.portal.kernel.dao.jdbc.DataAccess

query = "update User_ set emailaddress = 'fulano@liferay.com' where userid = 41131"
max_rows = 100

con = DataAccess.getConnection()

stmt = con.createStatement()

rs = stmt.executeUpdate(query)

println rs
