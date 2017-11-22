// ==============================================================
// DynamicQuery in groovy script
// ==============================================================
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil
import com.liferay.message.boards.kernel.service.MBThreadLocalServiceUtil
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil
import com.liferay.message.boards.kernel.model.MBThread
import com.liferay.message.boards.kernel.model.MBMessage
import com.liferay.portal.kernel.util.PortalUtil
import com.liferay.portal.kernel.util.GetterUtil
import com.liferay.portal.kernel.util.StringUtil
import com.liferay.portal.kernel.util.HtmlUtil

now = java.util.GregorianCalendar.getInstance()
weeksago = java.util.GregorianCalendar.getInstance()
weeksago.add(3, -52)

companyId = PortalUtil.getDefaultCompanyId()

mbThreadQuery = DynamicQueryFactoryUtil.forClass(MBThread.class)

mbThreadQuery.add(RestrictionsFactoryUtil.ne("categoryId", GetterUtil.getLong("-1")))
mbThreadQuery.add(RestrictionsFactoryUtil.eq("companyId", PortalUtil.getDefaultCompanyId()))
mbThreadQuery.add(RestrictionsFactoryUtil.eq("status", GetterUtil.getInteger("0")))
mbThreadQuery.add(RestrictionsFactoryUtil.between("lastPostDate", weeksago.getTime(), now.getTime()))
mbThreadQuery.setProjection(ProjectionFactoryUtil.property("threadId"))

threads = MBMessageLocalServiceUtil.dynamicQuery(mbThreadQuery)

mbMessageQuery = DynamicQueryFactoryUtil.forClass(MBMessage.class)

mbMessageQuery.add(RestrictionsFactoryUtil.in("threadId", threads))
mbMessageQuery.add(RestrictionsFactoryUtil.between("createDate", weeksago.getTime(), now.getTime()))

mbMessageQuery.setProjection(
	ProjectionFactoryUtil.projectionList().add(
		ProjectionFactoryUtil.groupProperty("rootMessageId")).add(
			ProjectionFactoryUtil.alias(
				ProjectionFactoryUtil.rowCount(),"msgCount")))

mbMessageQuery.addOrder(OrderFactoryUtil.desc("msgCount"))
mbMessageQuery.setLimit(0, 7)

messages = MBMessageLocalServiceUtil.dynamicQuery(mbMessageQuery)

messages.each { m ->
	rootMsgId = m[0]
	msgCount = m[1]
	subject = MBMessageLocalServiceUtil.getMessage(rootMsgId).getSubject()
	println StringUtil.shorten(HtmlUtil.escape(subject), 55) + " - " + msgCount
}
