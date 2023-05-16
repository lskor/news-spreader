import dto.NewsDTO
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object GreenCity54
{

	private val url = "http://greencity54.ru/press/oazis54/"

	def getNews(number: Long): NewsDTO =
	{
		val doc = Jsoup.connect(s"$url/$number/").get()

		val outputSettings = new Document.OutputSettings
		outputSettings.prettyPrint(false)
		doc.outputSettings(outputSettings)
		doc.select("br").before("\\r\\n\\t")
		doc.select("p").append("\\r\\n\\t")

		val header = doc.select(".news-title").first().text()
		val content = doc.select(".news-text").first().text()
		val date = doc.select(".news-date > span").first().text()

		NewsDTO(header, content, date)
	}
}